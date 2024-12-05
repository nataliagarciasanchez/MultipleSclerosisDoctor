/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOCommunication;
import Menu.Utilities.Utilities;
import POJOs.Bitalino;
import POJOs.Doctor;
import POJOs.Feedback;
import POJOs.Patient;
import POJOs.Report;
import POJOs.User;
import Report.ProcessReport;
import Security.PasswordEncryption;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author maipa
 */
public class DoctorServerCommunication {
    
    private String serverAddress;
    private int serverPort;
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Doctor doctor;
    
    public DoctorServerCommunication(String serverAddress, int serverPort){
        this.serverAddress=serverAddress;
        this.serverPort=serverPort;
    }
    
    public void start(){
        
        try {
            this.socket = new Socket(serverAddress, serverPort);
            this.out = new ObjectOutputStream(socket.getOutputStream());
            out.flush();
            this.in = new ObjectInputStream(socket.getInputStream());
            System.out.println("DoctorServerCommunication - Doctor connected to server");
            //new Thread(new Receive(in)).start();
        } catch (IOException ex) {
            Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
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
                out.writeObject("register"); // Acción de registro
                out.flush();
                
                //TODO quitar souts
                System.out.println("Plain: " + doctor.getUser().getPassword()); // para comprobar si el hash se hace bien
                String hashedPassword = PasswordEncryption.hashPassword(doctor.getUser().getPassword()); 
                doctor.getUser().setPassword(hashedPassword); // para comprobar si el hash se hace bien
                System.out.println("Hashed: " + doctor.getUser().getPassword());
                
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
                out.writeObject("login"); // Acción de inicio de sesión
                out.writeObject(username);
                
                System.out.println("Plain: " + password);
                password = PasswordEncryption.hashPassword(password); // encriptamos
                System.out.println("Hashed: "+ password);
                out.writeObject(password);
                
                System.out.println("Logging in.....");
                Object response=in.readObject();
                if(response instanceof Doctor){//si es de tipo patient es que las credenciales son correctas
                   doctor = (Doctor) response;
                   System.out.println("Successfull log in!");
                }else if (response instanceof String){
                   String errorMessage = (String) response; // Mensaje de error
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
         */
        public void updateInformation(User user) { //TODO could receive also a doctor object to change that info

            try {
                out.writeObject("updateInformation");
                
                System.out.println("Plain: " + doctor.getUser().getPassword()); // para comprobar si el hash se hace bien
                String hashedPassword = PasswordEncryption.hashPassword(doctor.getUser().getPassword()); 
                doctor.getUser().setPassword(hashedPassword); 
                System.out.println("Hashed: " + doctor.getUser().getPassword());// para comprobar si el hash se hace bien
                
                out.writeObject(user);
                System.out.println(in.readObject());
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                releaseResources(in,out,socket);//TODO quitar el cerrar, solo para la prueba
            }

        }
        
        /**
         * Shows list of patients the doctor has assigned
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
        
        public void sendFeedback2Server(Feedback feedback){
            try {
                out.writeObject("sendFeedback");
                out.writeObject(feedback);
                out.flush();
                System.out.println(in.readObject());//response fromServer
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
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
    
    

    
}
