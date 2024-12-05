/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Andreoti
 */
public class PasswordEncryption {
    
    
    //method for hashing password
    public static String hashPassword(String plainPassword){
        try {
            // Crear instancia de MessageDigest para MD5
            MessageDigest md = MessageDigest.getInstance("MD5");
            
            // Convertir la contraseña en bytes
            byte[] passwordBytes = plainPassword.getBytes();
            
            // Generar el hash
            byte[] hashBytes = md.digest(passwordBytes);
            
            // Convertir el hash a una representación hexadecimal
            StringBuilder hashString = new StringBuilder();
            for (byte b : hashBytes) {
                // Convertir cada byte en un valor hexadecimal
                hashString.append(String.format("%02x", b));
            }
            
            // Devolver el hash como cadena
            return hashString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error when initializing MD5", e);
        }
    }
}
    
    

