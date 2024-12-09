/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import IOCommunication.DoctorServerCommunication;
import javax.swing.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author nataliagarciasanchez
 */
public class MainDoctorGUI {
    private static DoctorServerCommunication patientServerCom;
    private static DoctorServerCommunication.Send send;

    public static void main(String[] args) {
        // Pedir al usuario el IP del servidor y el puerto
        try {
            String serverAddress = JOptionPane.showInputDialog(null, "Enter the Server IP Address:", "Server Connection", JOptionPane.QUESTION_MESSAGE);
            String portInput = JOptionPane.showInputDialog(null, "Enter the Server Port:", "Server Connection", JOptionPane.QUESTION_MESSAGE);

            if (serverAddress == null || portInput == null || serverAddress.isEmpty() || portInput.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Server IP and Port are required. Exiting the application.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Salir si no se proporciona la información del servidor
            }

            int port = Integer.parseInt(portInput);

            // Inicializar la conexión al servidor
            patientServerCom = new DoctorServerCommunication(serverAddress, port);
            patientServerCom.start(); 
            send = patientServerCom.new Send();

            JOptionPane.showMessageDialog(null, "Connected to the server successfully!", "Connection Status", JOptionPane.INFORMATION_MESSAGE);

            // Iniciar la interfaz gráfica
            SwingUtilities.invokeLater(() -> {
                FramePrincipal mainFrame = new FramePrincipal(send);
                mainFrame.setVisible(true);
            });

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid port number. Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            Logger.getLogger(MainDoctorGUI.class.getName()).log(Level.SEVERE, "Unexpected error", e);
            JOptionPane.showMessageDialog(null, "An unexpected error occurred. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}