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
        try {
            
            this.socket=new Socket(serverAddress,serverPort);
            out = new ObjectOutputStream(socket.getOutputStream());
            this.out.flush();
            in = new ObjectInputStream(socket.getInputStream());
            //el doctor debe poder recibir las señales del server mientras manda el feedback 
            // Thread receiveThread=new Thread(new Receive());
            //receiveThread.start();
        } catch (IOException ex) {
            Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
   /* public void start(){
        try {
            this.in=new ObjectInputStream(socket.getInputStream());
            this.out=new ObjectOutputStream(socket.getOutputStream());
            System.out.println("Doctor connected to server");
            
            new Thread(new Receive(in, out)).start();
        } catch (IOException ex) {
            Logger.getLogger(DoctorServerCommunication.class.getName()).log(Level.SEVERE, null, ex);
        }
    }*/
    
    class Send {
        
        /**
         * Calls the server so the doctor registers in the app and, therefore,
         * it is saved in the database
         *
         * @param doctor
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
                out.writeObject(password);
                System.out.println("Logging in.....");
                doctor= (Doctor) in.readObject();
  
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
       
        public void viewPatients(){
        try {
        // Send the request to the server
        System.out.println("Requesting patient list...");
        out.writeObject("viewPatients");
        out.flush();
        Object confirmation=in.readObject();
        List<Patient> patients = (List<Patient>) confirmation; // Cast the object to List<Patient>
        System.out.println("Patient list received:");
            
        // Iterate over all patients
        for (Patient patient : patients) {
        System.out.println(patient); // Assumes Patient class has a meaningful toString() implementation
        } 
        
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
    
    class Receive implements Runnable {

        private ObjectInputStream in;
        private boolean running=true;
       // private ObjectOutputStream out;

        public Receive(ObjectInputStream in) {
            this.in = in;
            
        }
        public void stop() {
        running = false;
        }

        @Override
        public void run() {
            try {
                while (running) {
                    // Read signals
                    //List <Frame> signals signals = (List<Frame>) in.readObject();
                    Patient patient=(Patient) in.readObject();
                    System.out.println("Processing signals and patient symptoms.....");
                    handlePatientFromServer(patient);
                    // handleSignalsFromServer(signals);
                    //Report report=calls the class that laura is doing
                    //out.writeObject(report);
                    
                }
            } catch (IOException | ClassNotFoundException ex) {
                Logger.getLogger(Receive.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        private void handlePatientFromServer(Object message) {
            // Process messages received from the server
            System.out.println("Received from server: " + message);
        }
        private void handleSignalsFromServer(Object message) {
            // Process messages received from the server
            System.out.println("Received from server: " + message);
        }
        
    }

    
}
