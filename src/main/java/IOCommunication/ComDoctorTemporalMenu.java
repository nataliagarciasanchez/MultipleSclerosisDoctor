/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package IOCommunication;

import Menu.Utilities.Utilities;
import POJOs.Doctor;
import POJOs.Gender;
import POJOs.Patient;
import POJOs.Role;
import POJOs.SignalType;
import POJOs.User;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author noeli
 */
public class ComDoctorTemporalMenu {
    
    public static DoctorServerCommunication com; 
    public static DoctorServerCommunication.Send send;
    public static String macAddress = "98:D3:41:FD:4E:E8";
    public static Role role;
    
    public static void main(String[] args) {
        com= new DoctorServerCommunication("localhost", 9000);
        //com.start();
        send= com.new Send();
        role=new Role();
        //register();
        //login();
        //updateInfo();
        //Function giving back feedback with diagnosis        
    }
    
     public static void register() {
         Doctor noelia =new Doctor("Dr.Noelia","Auba");
         User user=new User("drauba@gmail.com", "Password123", role);
         noelia.setUser(user);
         send.register(noelia);
     }
     public static void login(){
       Doctor doctor=send.login("drauba@gmail.com", "Password123");
       System.out.println(doctor);
    }
    
    public static void updateInfo() {
        Doctor doctor=send.login("drauba@gmail.com", "Password123");
        System.out.println(doctor);
        User user = doctor.getUser();
        user.setRole(role);
        System.out.println("user\n" + user);
        String newPass = "Password456";
        if (Utilities.isValidPassword(newPass)) {
            user.setPassword("Password456");
            System.out.println("This is to check if the setter is working correctly: " + user.getPassword());
            send.updateInformation(user);
        }

    }
    
}

