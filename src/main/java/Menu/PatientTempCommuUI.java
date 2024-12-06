/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import IOCommunication.DoctorServerCommunication;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

/**
 *
 * @author nataliagarciasanchez
 */
public class PatientTempCommuUI {
    
    public static void main(String[] args) {
        try {
            // Configuración predeterminada del servidor (localhost y puerto 9000)
            String serverAddress = "127.0.0.1"; // Localhost
            int port = 9000; // Puerto predeterminado

            // Inicializar la conexión al servidor
            DoctorServerCommunication doctorServerCom = new DoctorServerCommunication(serverAddress, port);
            doctorServerCom.start(); // was missing, required to start communication
            DoctorServerCommunication.Send send = doctorServerCom.new Send();

            JOptionPane.showMessageDialog(null, "Connected to the server successfully!", "Connection Status", JOptionPane.INFORMATION_MESSAGE);

            // Iniciar la interfaz gráfica
            SwingUtilities.invokeLater(() -> {
                FramePrincipal mainFrame = new FramePrincipal(send);
                mainFrame.setVisible(true);
            });

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error connecting to the server. Please ensure the server is running.", "Connection Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
}
