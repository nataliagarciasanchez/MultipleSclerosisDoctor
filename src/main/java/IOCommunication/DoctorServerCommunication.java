
package IOCommunication;
import Menu.Utilities.Utilities;
import POJOs.Doctor;
import POJOs.Feedback;
import POJOs.Patient;
import POJOs.Report;
import POJOs.User;
import Security.PasswordEncryption;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author maipa
 */
public class DoctorServerCommunication {
    
    private final String serverAddress;
    private final int serverPort;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Doctor doctor;
    
    public DoctorServerCommunication(String serverAddress, int serverPort){
        this.serverAddress=serverAddress;
        this.serverPort=serverPort;
    }
    
    public boolean start(){
        boolean connection = false;
        try {
            this.socket = new Socket(serverAddress, serverPort);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            this.in = new ObjectInputStream(socket.getInputStream());
            
            
            String message = "DoctorServerCommunication";
            out.writeObject(message);
            out.flush();
            
            Boolean serverResponse = (Boolean) in.readObject();
            
            if (!serverResponse) {
                connection = false;
                releaseResources(in, out, socket);
                //System.exit(0);
                
            }else{
            connection = true;
            }
            
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            JOptionPane.showMessageDialog(null, 
            "Connection to the server was lost. Please try again later.",
            "Connection Error", JOptionPane.ERROR_MESSAGE);
        System.exit(0);
        }
        return connection;
    }
    
    public class Send {
        
        /**
         * Calls the server so the doctor registers in the app and, therefore,
         * it is saved in the database
         *
         * @param doctor
         */
        public void register(Doctor doctor) {
            try {
                
                Utilities.validateRegisterDoctor(doctor);
                
                out.writeObject("register"); // Acción de registro
                out.flush();
                
                String hashedPassword = PasswordEncryption.hashPassword(doctor.getUser().getPassword()); 
                doctor.getUser().setPassword(hashedPassword); // para comprobar si el hash se hace bien
                
                
                out.writeObject(doctor.getUser());//envía los datos de registro al server
                out.writeObject(doctor);
                out.flush();
                
                System.out.println("Registering.....");
                String confirmation=(String) in.readObject();
                if (confirmation.contains("Username already in use") ){
                   System.out.println("Error: " + confirmation); 
                }else{
                    System.out.println(confirmation);//muestra la confirmación del server de que se ha registrado
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        /**
         * Logs in the app with the username and password and accesses the info
         * of that doctor
         *
         * @param username
         * @param password
         * @return message
         */
        public Doctor login(String username, String password) {
            
            try {
                User user = new User(username, password);
                Utilities.validateLogin(user);            
                
                out.writeObject("login"); // Acción de inicio de sesión
                out.writeObject(username);
                
                password = PasswordEncryption.hashPassword(password); // encriptamos
                
                out.writeObject(password);
                
                System.out.println("Logging in.....");
                Object response=in.readObject();
                if(response instanceof Doctor doctor1){//si es de tipo patient es que las credenciales son correctas
                   doctor = doctor1;
                   System.out.println("Successfull log in!");
                }else if (response instanceof String errorMessage){ // Mensaje de error
                    // Mensaje de error
                   System.out.println("Error: " + errorMessage); 
                }
                
  
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
            return doctor;
        }
        
        /**
         * Logs out of the app by closing all connections from that doctor to the server
         */
        public void logout(){
            try {
                out.writeObject("logout");
                System.out.println(in.readObject());
                //ahora mismo lo hace el server cuando recive esta opcion
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
              releaseResources(in, out, socket);  
            }
        }

        /**
         * Changes the current password of the patient
         *
         * @param user
         * @param doctor
         * @param confirmPassword
         */
        public void updateInformation(User user, Doctor doctor, String confirmPassword) {

            try {
                //Utilities.validateUpdateDoctor(doctor);
                
                out.writeObject("updateInformation");
                System.out.println("Sending update request to the server...");
                
                // Gestionar el hash de la contraseña en el servidor
                String newHashedPassword = PasswordEncryption.hashPassword(user.getPassword());
                String existingHashedPassword = doctor.getUser().getPassword();

                // update password if it has changed
                if (!existingHashedPassword.equals(newHashedPassword) && !user.getPassword().equals(existingHashedPassword)) {
                    System.out.println("Updating password...");
                    user.setPassword(newHashedPassword);
                    out.writeObject(user);
                }else{
                    out.writeObject(doctor.getUser());
                }
                
                
                out.writeObject(doctor);
                
                String response = (String) in.readObject();
                System.out.println("Server response: " + response);
                
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        
        /**
         * Shows list of patients the doctor has assigned
         * @param doctor
         * @return list of patients
         */
        public List <Patient> viewPatients(Doctor doctor) {
            List <Patient> patients = null;
            try {
                // Send the request to the server
                System.out.println("Requesting patient list...");
                out.writeObject("viewPatients");
                out.flush();
                System.out.println("viewPatientsRequest sent");
                
                out.writeObject(doctor);
                out.flush();
                
                patients = (List<Patient>) in.readObject();
                System.out.println("Patient list received:");

                // Iterate over all patients
                for (Patient patient : patients) {
                    System.out.println(patient); // Assumes Patient class has a meaningful toString() implementation
                }

            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }

        return patients;
        }
        
        public File getSignalsFile(Report report) {
            File signalsFile = null;

            try {
                System.out.println("Requesting signals file...");
                out.writeObject("getSignalsFile");
                out.flush();
                out.writeObject(report);
                out.flush();

                Object response = in.readObject();

                if (response instanceof byte[]) {
                    byte[] fileData = (byte[]) response; 
                    String fileName = (String) in.readObject(); 

                    // Limpiar y preparar nombre de archivo
                    String sanitizedFileName = fileName.replaceAll("[^a-zA-Z0-9\\.\\-]", "");
                    String fileExtension = fileName.contains(".") ? fileName.substring(fileName.lastIndexOf(".")) : ".tmp";

                    signalsFile = File.createTempFile(sanitizedFileName, fileExtension);
                    try (FileOutputStream fos = new FileOutputStream(signalsFile)) {
                        fos.write(fileData); 
                    }

                    System.out.println("File received: " + signalsFile.getAbsolutePath());
                } else if (response instanceof String) {
                    String errorMessage = (String) response;
                    System.err.println("Error received from server: " + errorMessage);
                } else {
                    System.out.println("Unexpected response from server.");
                }
            } catch (IOException | ClassNotFoundException ex) {
                ex.printStackTrace();
            }

            return signalsFile; 
        }
        
        public void sendFeedback2Server(Feedback feedback){
            try {
                System.out.println(feedback.toString());
                out.writeObject("sendFeedback");
                out.writeObject(feedback);
                out.flush();
                System.out.println(in.readObject());//response fromServer
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        
        
    }
    
    private static void releaseResources(ObjectInputStream in, ObjectOutputStream out, Socket socket) {
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    
}
