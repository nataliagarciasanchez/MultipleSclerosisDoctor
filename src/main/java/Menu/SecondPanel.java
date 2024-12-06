/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Menu;

import IOCommunication.DoctorServerCommunication;
import static IOCommunication.DoctorServerCommunicationTest.role;
import Menu.Utilities.Utilities;
import POJOs.Bitalino;
import POJOs.Doctor;
import POJOs.Report;
import POJOs.Role;
import POJOs.SignalType;
import POJOs.Symptom;
import POJOs.User;
import Security.PasswordEncryption;
import java.awt.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.IntStream;
import javax.swing.*;
import java.time.LocalDate;
/**
 *
 * @author nataliagarciasanchez
 */
public class SecondPanel extends JPanel {
    private JPanel whitePanel; // Dynamic white panel
    private JLabel titleLabel; // Main title
    private java.util.List<Symptom> symptomsList; // Symptom list
    private Doctor doctor;
    private final Image backgroundImage; // Background image
    private final DoctorServerCommunication.Send send;
    private LocalDate date = LocalDate.now();
    public static String macAddress = "98:D3:41:FD:4E:E8";
    private java.util.List<Bitalino> bitalinos; // Symptom list
    public static Role role;
    
    
    
    public SecondPanel(Doctor doctor, DoctorServerCommunication.Send send) {
        this.send = send;
        this.doctor = doctor;
        this.role=new Role();

        backgroundImage = new ImageIcon(getClass().getResource("/images/Fondo.jpg")).getImage();
        
        setLayout(new BorderLayout());
        
        JPanel mainContainer = new JPanel();
        mainContainer.setLayout(new BorderLayout());
        mainContainer.setOpaque(false);

        JPanel leftPanel = new JPanel();
        leftPanel.setOpaque(false); 
        leftPanel.setLayout(new BorderLayout());
        leftPanel.setBorder(BorderFactory.createEmptyBorder(0, 20, 20, 20)); 

        JPanel logoPanel = new JPanel();
        logoPanel.setOpaque(false);
        logoPanel.setLayout(new BoxLayout(logoPanel, BoxLayout.Y_AXIS));
        logoPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel logoLabel = new JLabel(new ImageIcon(getClass().getResource("/images/LogoSmall.jpeg")));
        logoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel appLabel = new JLabel("NeuroTrack");
        appLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        appLabel.setForeground(Color.WHITE);
        appLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        logoPanel.add(Box.createRigidArea(new Dimension(0, 20))); 
        logoPanel.add(logoLabel);
        logoPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        logoPanel.add(appLabel);

        leftPanel.add(logoPanel, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false); 
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        buttonPanel.add(Box.createRigidArea(new Dimension(0, 30))); 

        JButton viewInfoButton = createStyledButton("View My Information");
        JButton viewPatients = createStyledButton("View Patients");
        JButton settingsButton = createStyledButton("Settings");
        
        viewInfoButton.addActionListener(e -> displayDoctorInfo());
        viewPatients.addActionListener(e -> displayPatients());
        settingsButton.addActionListener(e -> auxiliar());
        
        buttonPanel.add(viewInfoButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(viewPatients);
        buttonPanel.add(Box.createVerticalGlue()); 
        buttonPanel.add(settingsButton);

        leftPanel.add(buttonPanel, BorderLayout.CENTER);

        mainContainer.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);

        titleLabel = new JLabel("Welcome to the MultipleSclerosis Patient app!");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setOpaque(false); 
        titlePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20)); // (top, left, bottom, right)
        titlePanel.add(titleLabel, BorderLayout.WEST);
        
        JButton logoutButton = createStyledButton("Log Out");
        logoutButton.setPreferredSize(new Dimension(120, 40)); 
        logoutButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to log out?", "Log Out", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                System.exit(0); 
            }
        });
        
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 0, 0));
        logoutPanel.setOpaque(false); 
        logoutPanel.add(logoutButton);

        titlePanel.add(logoutPanel, BorderLayout.CENTER);

        rightPanel.add(titlePanel, BorderLayout.NORTH);

        JPanel whitePanelWrapper = new JPanel();
        whitePanelWrapper.setLayout(new BorderLayout());
        whitePanelWrapper.setOpaque(false); 
        whitePanelWrapper.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // (top, left, bottom, right)

        whitePanel = new JPanel();
        whitePanel.setBackground(Color.WHITE);
        whitePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        whitePanel.setLayout(new BoxLayout(whitePanel, BoxLayout.Y_AXIS));

        whitePanelWrapper.add(whitePanel, BorderLayout.CENTER);

        rightPanel.add(whitePanelWrapper, BorderLayout.CENTER);

        mainContainer.add(rightPanel, BorderLayout.CENTER);

        add(mainContainer, BorderLayout.CENTER);
    }
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setOpaque(true); 
        button.setBackground(Color.WHITE); 
        button.setForeground(Color.BLACK); 
        button.setFocusPainted(false);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(250, 50));
        button.setPreferredSize(new Dimension(250, 50));
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK)); 
        return button;
    }
    
    private void displayDoctorInfo() {
        whitePanel.removeAll();

        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); 

        contentPanel.add(createInfoLine("Name: ", doctor.getName() != null ? doctor.getName() + " " + doctor.getSurname() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.add(createInfoLine("Specialty: ", doctor.getSpecialty() != null ? doctor.getSpecialty() : "N/A"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        contentPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        whitePanel.add(contentPanel);

        whitePanel.revalidate();
        whitePanel.repaint();
    }
    
    private JPanel createInfoLine(String labelText, String valueText) {
        JPanel linePanel = new JPanel();
        linePanel.setLayout(new BoxLayout(linePanel, BoxLayout.X_AXIS)); 
        linePanel.setBackground(Color.WHITE);
        linePanel.setAlignmentX(Component.LEFT_ALIGNMENT); 

        JLabel label = new JLabel(labelText);
        label.setFont(new Font("Segoe UI", Font.BOLD, 18)); 
        linePanel.add(label);

        JLabel value = new JLabel(valueText);
        value.setFont(new Font("Segoe UI", Font.PLAIN, 16)); 
        linePanel.add(value);

        return linePanel;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // Ensure the background image fills the entire panel dynamically
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
    
    private void displayPatients(){
        
    }
    
    private void auxiliar() {
        whitePanel.removeAll();
        whitePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JPanel borderedPanel = new JPanel();
        borderedPanel.setLayout(new GridBagLayout());
        borderedPanel.setBackground(Color.WHITE);
        borderedPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            "Select one of the options",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 16)
        )); 
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        
        JButton changeInfoButton = new JButton("Change my personal information");
        changeInfoButton.setBackground(Color.WHITE);
        changeInfoButton.setForeground(Color.BLACK);
        changeInfoButton.setFocusPainted(false);
        changeInfoButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        JButton changePasswordButton = new JButton("Change my password");
        changePasswordButton.setBackground(Color.WHITE);
        changePasswordButton.setForeground(Color.BLACK);
        changePasswordButton.setFocusPainted(false);
        changePasswordButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        changeInfoButton.addActionListener(e -> displayDoctorInfoUpdate());
        changePasswordButton.addActionListener(e -> displayDoctorPasswordUpdate());

        buttonPanel.add(changeInfoButton);
        buttonPanel.add(changePasswordButton);

        borderedPanel.add(buttonPanel);

        GridBagConstraints borderedGbc = new GridBagConstraints();
        borderedGbc.gridx = 0;
        borderedGbc.gridy = 0;
        borderedPanel.add(buttonPanel, borderedGbc);

        whitePanel.add(borderedPanel,  BorderLayout.CENTER);

        whitePanel.revalidate();
        whitePanel.repaint();
    }

    
    
    private void displayDoctorInfoUpdate() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BorderLayout());
        whitePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20)); 

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            "Update Personal Information",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 16)
        )); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Dimension fieldSize = new Dimension(200, 30);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(nameLabel, gbc);

        JTextField nameField = new JTextField(doctor.getName() != null ? doctor.getName() : "");
        nameField.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        contentPanel.add(nameField, gbc);

        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy++;
        contentPanel.add(surnameLabel, gbc);

        JTextField surnameField = new JTextField(doctor.getSurname() != null ? doctor.getSurname() : "");
        surnameField.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        contentPanel.add(surnameField, gbc);

        whitePanel.add(contentPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        JButton saveButton = new JButton("Save Changes");
        saveButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        saveButton.setBackground(Color.WHITE);
        saveButton.setForeground(Color.BLACK);
        saveButton.setFocusPainted(false);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);

        saveButton.addActionListener(e -> {
            try {
                if (nameField.getText().trim().isEmpty() || surnameField.getText().trim().isEmpty()) {
                    throw new IllegalArgumentException("All fields must be filled.");
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            doctor.setName(nameField.getText());
            doctor.setSurname(surnameField.getText());
            User user = doctor.getUser();
            user.setRole(role);
            send.updateInformation(user, doctor); 

            JOptionPane.showMessageDialog(this, "Patient information updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        cancelButton.addActionListener(e -> auxiliar());

        buttonPanel.add(cancelButton);
        buttonPanel.add(saveButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        // Refresh the whitePanel
        whitePanel.revalidate();
        whitePanel.repaint();
    }



    
    private void displayDoctorPasswordUpdate() {
        whitePanel.removeAll();
        whitePanel.setLayout(new BorderLayout());
        whitePanel.setBorder(BorderFactory.createEmptyBorder(15, 20, 0, 20)); 

        JPanel passwordPanel = new JPanel(new GridBagLayout());
        passwordPanel.setBackground(Color.WHITE);
        passwordPanel.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createLineBorder(Color.BLACK, 1),
            "Change Password",
            javax.swing.border.TitledBorder.CENTER,
            javax.swing.border.TitledBorder.TOP,
            new Font("Segoe UI", Font.PLAIN, 16)
        )); 
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_END;
        
        JLabel newPasswordLabel = new JLabel("New Password:");
        newPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordPanel.add(newPasswordLabel, gbc);

        gbc.gridy++;
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        confirmPasswordLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        passwordPanel.add(confirmPasswordLabel, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.LINE_START;

        JPasswordField newPasswordField = new JPasswordField(20);
        passwordPanel.add(newPasswordField, gbc);

        gbc.gridy++;
        JPasswordField confirmPasswordField = new JPasswordField(20);
        passwordPanel.add(confirmPasswordField, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);

        JButton savePasswordButton = new JButton("Save New Password");
        savePasswordButton.setFont(new Font("Segoe UI", Font.BOLD, 14));
        savePasswordButton.setBackground(Color.WHITE);
        savePasswordButton.setForeground(Color.BLACK);
        savePasswordButton.setFocusPainted(false);
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.setBackground(Color.WHITE);
        cancelButton.setForeground(Color.BLACK);
        cancelButton.setFocusPainted(false);
        cancelButton.setFont(new Font("Segoe UI", Font.BOLD, 14));

        savePasswordButton.addActionListener(e -> {
            
            String newPassword = new String(newPasswordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            try{
                if (newPassword.isEmpty() || confirmPassword.isEmpty()) {
                    JOptionPane.showMessageDialog(whitePanel, "Password fields cannot be empty.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!newPassword.equals(confirmPassword)) {
                    JOptionPane.showMessageDialog(whitePanel, "New password and confirm password do not match.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!Utilities.isValidPassword(newPassword)) {
                    JOptionPane.showMessageDialog(whitePanel, "Password must be at least 8 characters long.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                User user = doctor.getUser();
                user.setPassword(newPassword); 
                user.setRole(role);
                send.updateInformation(user, doctor); 

                JOptionPane.showMessageDialog(whitePanel, "Password successfully updated.", "Success", JOptionPane.INFORMATION_MESSAGE);
                auxiliar(); 
            }catch(Exception ex){
                JOptionPane.showMessageDialog(whitePanel, "An error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        cancelButton.addActionListener(e -> auxiliar());
                
        buttonPanel.add(cancelButton);
        buttonPanel.add(savePasswordButton);

        whitePanel.add(passwordPanel, BorderLayout.CENTER);
        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }

}
