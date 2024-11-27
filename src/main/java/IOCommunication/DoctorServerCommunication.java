/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOCommunication;
import POJOs.Doctor;
import POJOs.Patient;
import POJOs.User;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
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
        try {
            this.serverAddress=serverAddress;
            this.serverPort=serverPort;
            this.socket=new Socket(serverAddress,serverPort);
            System.out.println("Doctor connected to server");
            
        } catch (IOException ex) {
            Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public void start(){
        try {
            this.in=new ObjectInputStream(socket.getInputStream());
            this.out=new ObjectOutputStream(socket.getOutputStream());
            
            
            new Thread(new Receive(in, out)).start();
        } catch (IOException ex) {
            Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    class Send {
        
        /**
         * Calls the server so the patient registers in the app and, therefore,
         * it is saved in the database
         *
         * @param patient
         */
        public void register(Doctor doctor) {
            try {
                out.writeObject("register"); // Acción de registro
                out.writeObject(doctor.getUser());//envía los datos de registro al server
                out.writeObject(doctor);
                
                out.flush();
                System.out.println("Registering.....");
                String confirmation=(String) in.readObject();
                System.out.println(confirmation);//muestra la confirmación del server de que se ha registrado
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        /**
         * Logs in the app with the username and password and accesses the info
         * of that patient
         *
         * @param username
         * @param password
         * @return message
         */
        public Doctor login(String username, String password) {
            
            try {
                out.writeObject("login"); // Acción de inicio de sesión
                out.writeObject(username);
                out.writeObject(password);
                System.out.println("Logging in.....");
                doctor= (Doctor) in.readObject();
  
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }
            return doctor;
        }
        
        /**
         * Logs out of the app by closing all connections from that patient to the server
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
        public void updateInformation(User user) {

            try {
                out.writeObject("updateInformation");
                out.writeObject(user);
                System.out.println(in.readObject());
                
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
            }finally{
                releaseResources(in,out,socket);//TODO quitar el cerrar, solo para la prueba
            }

        }
        
        //public void viewPatient....
        
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
    
    class Receive implements Runnable {

        private ObjectInputStream in;
        private ObjectOutputStream out;

        Receive(ObjectInputStream in, ObjectOutputStream out) {
            this.in = in;
            this.out=out;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    // Read signals
                    //List <Frame> signals signals = (List<Frame>) in.readObject();
                    Patient patient=(Patient) in.readObject();
                    System.out.println("Processing signals and patient symptoms.....");
                    
                    //Report report=calls the class that laura is doing
                    //out.writeObject(report);
                    
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    
}
