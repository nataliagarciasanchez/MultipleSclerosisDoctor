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
import POJOs.Feedback;
import POJOs.Patient;
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
import java.util.stream.Collectors;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
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
        JButton viewReports = createStyledButton("View Reports");
        JButton settingsButton = createStyledButton("Settings");
        
        viewInfoButton.addActionListener(e -> displayDoctorInfo());
        viewPatients.addActionListener(e -> displayPatients());
        viewReports.addActionListener(e -> displayReports());
        settingsButton.addActionListener(e -> auxiliar());
        
        buttonPanel.add(viewInfoButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(viewPatients);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        buttonPanel.add(viewReports);
        buttonPanel.add(Box.createVerticalGlue()); 
        buttonPanel.add(settingsButton);

        leftPanel.add(buttonPanel, BorderLayout.CENTER);

        mainContainer.add(leftPanel, BorderLayout.WEST);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.setOpaque(false);

        titleLabel = new JLabel("Welcome to the MultipleSclerosis Doctor app!");
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
    
    private void displayPatients() {
        whitePanel.removeAll(); // Limpiar el contenido del panel blanco
        whitePanel.setLayout(new BorderLayout());

        // Título de búsqueda
        JLabel searchLabel = new JLabel("Search a patient:");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        // Campo de texto para buscar pacientes
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Panel para el buscador
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        whitePanel.add(searchPanel, BorderLayout.NORTH);

        // Panel principal para los pacientes con un JScrollPane
        JPanel patientsPanel = new JPanel();
        patientsPanel.setLayout(new BoxLayout(patientsPanel, BoxLayout.Y_AXIS));
        patientsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(patientsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Incremento de desplazamiento
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        whitePanel.add(scrollPane, BorderLayout.CENTER);

        // Obtener la lista de pacientes desde el servidor
        java.util.List<Patient> patients = send.viewPatients(doctor);
        if (patients != null) {
            updatePatientsList(patients, patientsPanel); // Mostrar la lista completa al principio
        } else {
            System.out.println("No patients received from server.");
        }

        // Actualizar la lista de pacientes en función de la búsqueda
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterPatients();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterPatients();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterPatients();
            }

            private void filterPatients() {
                String searchText = searchField.getText().toLowerCase();
                if (patients != null) {
                    java.util.List<Patient> filteredPatients = patients.stream()
                            .filter(p -> (p.getName() + " " + p.getSurname()).toLowerCase().contains(searchText))
                            .collect(Collectors.toList());
                    updatePatientsList(filteredPatients, patientsPanel);
                }
            }
        });

        whitePanel.revalidate();
        whitePanel.repaint();
    }
    
    private void updatePatientsList(java.util.List<Patient> patients, JPanel patientsPanel) {
        patientsPanel.removeAll(); // Limpiar el contenido anterior

        Dimension fixedSize = new Dimension(700, 50); // Tamaño fijo para cada panel de paciente

        for (Patient patient : patients) {
            JPanel patientPanel = new JPanel(new BorderLayout());
            patientPanel.setBackground(Color.LIGHT_GRAY);
            patientPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            patientPanel.setPreferredSize(fixedSize); // Fijar el tamaño del panel
            patientPanel.setMaximumSize(fixedSize); // Asegurar que no crezca más allá de este tamaño
            patientPanel.setMinimumSize(fixedSize); // Asegurar que no se reduzca más allá de este tamaño

            JLabel patientLabel = new JLabel(patient.getName() + " " + patient.getSurname());
            patientLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JButton viewButton = new JButton("View");
            viewButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            viewButton.setBackground(Color.WHITE);
            viewButton.setForeground(Color.BLACK);

            viewButton.addActionListener(e -> displayPatientDetails(patient)); // Lógica para mostrar los detalles del paciente

            patientPanel.add(patientLabel, BorderLayout.CENTER);
            patientPanel.add(viewButton, BorderLayout.EAST);

            patientsPanel.add(patientPanel);
            patientsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre pacientes
        }
        
        patientsPanel.revalidate();
        patientsPanel.repaint();
    }
    
    private void displayPatientDetails(Patient patient) {
        whitePanel.removeAll(); // Limpiar el contenido del panel blanco
        whitePanel.setLayout(new BorderLayout());

        // Panel superior para mostrar la información del paciente
        JPanel patientInfoPanel = new JPanel();
        patientInfoPanel.setBackground(Color.WHITE);
        patientInfoPanel.setLayout(new BoxLayout(patientInfoPanel, BoxLayout.Y_AXIS));
        patientInfoPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Añadir información del paciente
        patientInfoPanel.add(createInfoLine("Name: ", patient.getName() != null ? patient.getName() + " " + patient.getSurname() : "N/A"));
        patientInfoPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        patientInfoPanel.add(createInfoLine("DNI: ", patient.getNIF() != null ? patient.getNIF() : "N/A"));
        patientInfoPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        patientInfoPanel.add(createInfoLine("Birth Date: ", patient.getDob() != null ? patient.getDob().toString() : "N/A"));
        patientInfoPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        patientInfoPanel.add(createInfoLine("Gender: ", patient.getGender() != null ? patient.getGender().toString() : "N/A"));
        patientInfoPanel.add(Box.createRigidArea(new Dimension(0, 6)));
        patientInfoPanel.add(createInfoLine("Phone: ", patient.getPhone() != null ? patient.getPhone() : "N/A"));

        whitePanel.add(patientInfoPanel, BorderLayout.NORTH);

        // Panel contenedor para el buscador y la lista de informes
        JPanel reportsContainer = new JPanel();
        reportsContainer.setLayout(new BorderLayout());
        reportsContainer.setBackground(Color.WHITE);

        // Crear un buscador para los informes
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        JLabel searchLabel = new JLabel("Search Reports by Date (YYYY-MM-DD):");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        reportsContainer.add(searchPanel, BorderLayout.NORTH);

        // Panel para la lista de informes (Reports)
        JPanel reportsPanel = new JPanel();
        reportsPanel.setLayout(new BoxLayout(reportsPanel, BoxLayout.Y_AXIS));
        reportsPanel.setBackground(Color.WHITE);

        // JScrollPane para hacer desplazable la lista de informes
        JScrollPane scrollPane = new JScrollPane(reportsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));

        reportsContainer.add(scrollPane, BorderLayout.CENTER);

        whitePanel.add(reportsContainer, BorderLayout.CENTER);

        // Obtener los informes del paciente y mostrarlos
        java.util.List<Report> reports = patient.getReports(); 
        if (reports != null) {
            updateReportsList(reports, reportsPanel); // Mostrar todos los informes al principio
        } else {
            System.out.println("No reports available for this patient.");
        }

        // Filtrar informes en función de la búsqueda
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterReports();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterReports();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterReports();
            }

            private void filterReports() {
                String searchText = searchField.getText().toLowerCase();
                if (reports != null) {
                    java.util.List<Report> filteredReports = reports.stream()
                            .filter(report -> report.getDate().toString().contains(searchText)) // Filtrar por fecha
                            .collect(Collectors.toList());
                    updateReportsList(filteredReports, reportsPanel);
                }
            }
        });
        
        // Botón Back enmarcado
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        
        backButton.addActionListener(e -> displayPatients());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(backButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }

    private void updateReportsList(java.util.List<Report> reports, JPanel reportsPanel) {
        reportsPanel.removeAll(); // Limpiar el contenido anterior

        Dimension fixedSize = new Dimension(1000, 50); // Tamaño fijo para cada panel de reporte

        for (Report report : reports) {
            JPanel reportPanel = new JPanel(new BorderLayout());
            reportPanel.setBackground(Color.LIGHT_GRAY);
            reportPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            reportPanel.setPreferredSize(fixedSize); // Aplicar tamaño fijo
            reportPanel.setMaximumSize(fixedSize);
            reportPanel.setMinimumSize(fixedSize);

            JLabel reportLabel = new JLabel("Date: " + report.getDate().toString());
            reportLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JButton feedbackButton = new JButton("View Detail Report");
            feedbackButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            feedbackButton.setBackground(Color.WHITE);
            feedbackButton.setForeground(Color.BLACK);

            feedbackButton.addActionListener(e -> viewDetailReport(report));

            reportPanel.add(reportLabel, BorderLayout.CENTER);
            reportPanel.add(feedbackButton, BorderLayout.EAST);

            reportsPanel.add(reportPanel);
            reportsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre informes
        }

        reportsPanel.revalidate();
        reportsPanel.repaint();
    }
    
    private void viewDetailReport(Report report){
        whitePanel.removeAll(); // Limpiar el contenido del panel blanco
        whitePanel.setLayout(new BorderLayout());
        
        JLabel titleLabel = new JLabel("Report Details");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        whitePanel.add(titleLabel, BorderLayout.NORTH);
        
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        contentPanel.add(createInfoLine("1. Symptoms: ", report.getSymptoms() != null && !report.getSymptoms().isEmpty()
            ? report.getSymptoms().stream().map(Symptom::getName).collect(Collectors.joining(", "))
            : "No symptoms reported"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createInfoLine("2. ECG: ", Utilities.checkECG(report) ? "Values are OK" : "Values are NOT OK"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        contentPanel.add(createInfoLine("3. EMG: ", Utilities.checkEMG(report) ? "Values are OK" : "Values are NOT OK"));
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        JLabel feedbackLabel = new JLabel("4. Send Feedback to Patient:");
        feedbackLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        contentPanel.add(feedbackLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));

        JTextField feedbackField = new JTextField();
        feedbackField.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        feedbackField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30)); // Tamaño fijo para el campo
        contentPanel.add(feedbackField);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JButton sendFeedbackButton = new JButton("Send Feedback");
        sendFeedbackButton.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        sendFeedbackButton.setBackground(Color.WHITE);
        sendFeedbackButton.setForeground(Color.BLACK);
        
        sendFeedbackButton.addActionListener(e -> {
                String feedbackMessage = feedbackField.getText().trim();
                if (!feedbackMessage.isEmpty()) {
                    // Crear una instancia de Feedback
                    Feedback feedback = new Feedback(
                        feedbackMessage, // Mensaje del feedback
                        new java.sql.Date(System.currentTimeMillis()), // Fecha actual
                        doctor, // Objeto Doctor (asumiendo que está disponible en el contexto)
                        report.getPatient()  // Objeto Patient correspondiente
                     );

                    // Llamar al método para enviar el feedback al servidor
                    send.sendFeedback2Server(feedback);

                    // Mostrar mensaje de confirmación
                    JOptionPane.showMessageDialog(whitePanel, "Feedback sent successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    // Limpiar el campo de texto
                    feedbackField.setText("");
                } else {
                    // Mostrar advertencia si no se ingresa mensaje
                    JOptionPane.showMessageDialog(whitePanel, "Please enter feedback before sending.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
        });

        
        contentPanel.add(sendFeedbackButton);
        
        whitePanel.add(contentPanel, BorderLayout.CENTER);
        
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(Color.BLACK);
        backButton.addActionListener(e -> displayPatientDetails(report.getPatient()));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(backButton);

        whitePanel.add(buttonPanel, BorderLayout.SOUTH);

        whitePanel.revalidate();
        whitePanel.repaint();
    }
    
    
    private void displayReports() {
        whitePanel.removeAll(); // Limpiar el contenido del panel blanco
        whitePanel.setLayout(new BorderLayout());

        // Título de búsqueda
        JLabel searchLabel = new JLabel("Search reports by date (YYYY-MM-DD):");
        searchLabel.setFont(new Font("Segoe UI", Font.PLAIN, 18));

        // Campo de texto para buscar reports
        JTextField searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 16));

        // Panel para el buscador
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        searchPanel.add(searchLabel, BorderLayout.WEST);
        searchPanel.add(searchField, BorderLayout.CENTER);

        whitePanel.add(searchPanel, BorderLayout.NORTH);

        // Panel principal para los reports con un JScrollPane
        JPanel reportsPanel = new JPanel();
        reportsPanel.setLayout(new BoxLayout(reportsPanel, BoxLayout.Y_AXIS));
        reportsPanel.setBackground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(reportsPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Incremento de desplazamiento
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        whitePanel.add(scrollPane, BorderLayout.CENTER);

        // Obtener la lista de reports desde el servidor
        java.util.List<Patient> patients = send.viewPatients(doctor); 
        java.util.List<Report> reports = new ArrayList<>();
        for (Patient patient : patients) {
            if (patient.getReports() != null) { // Verificar que la lista de reports no sea null
                reports.addAll(patient.getReports()); // Acumular reports solo si no es null
            }
        }

        if (!reports.isEmpty()) {
            updateReportsList2(reports, reportsPanel); 
        }else {
            reportsPanel.setLayout(new BoxLayout(reportsPanel, BoxLayout.Y_AXIS));

            JLabel noReportsLabel = new JLabel("No reports received from server.");
            noReportsLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
            noReportsLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            noReportsLabel.setForeground(Color.GRAY);

            reportsPanel.add(Box.createVerticalGlue()); // Añadir espacio arriba
            reportsPanel.add(noReportsLabel); // Añadir el mensaje
            reportsPanel.add(Box.createVerticalGlue()); // Añadir espacio abajo

            reportsPanel.revalidate();
            reportsPanel.repaint();
        }

        // Actualizar la lista de reports en función de la búsqueda
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterReports();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterReports();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterReports();
            }

            private void filterReports() {
                String searchText = searchField.getText().toLowerCase();
                if (reports != null) {
                    java.util.List<Report> filteredReports = reports.stream()
                            .filter(report -> report.getDate().toString().contains(searchText)) // Filtrar por fecha
                            .collect(Collectors.toList());
                    updateReportsList(filteredReports, reportsPanel);
                }
            }
        });

        whitePanel.revalidate();
        whitePanel.repaint();
    }

    private void updateReportsList2(java.util.List<Report> reports, JPanel reportsPanel) {
        reportsPanel.removeAll(); // Limpiar el contenido anterior

        Dimension fixedSize = new Dimension(1000, 50); // Tamaño fijo para cada panel de report

        for (Report report : reports) {
            JPanel reportPanel = new JPanel(new BorderLayout());
            reportPanel.setBackground(Color.LIGHT_GRAY);
            reportPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            reportPanel.setPreferredSize(fixedSize); // Fijar el tamaño del panel
            reportPanel.setMaximumSize(fixedSize); // Asegurar que no crezca más allá de este tamaño
            reportPanel.setMinimumSize(fixedSize); // Asegurar que no se reduzca más allá de este tamaño

            JLabel reportLabel = new JLabel("Date: " + report.getDate().toString() + " | Patient: " + report.getPatient().getName());
            reportLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));

            JButton viewButton = new JButton("View Details");
            viewButton.setFont(new Font("Segoe UI", Font.PLAIN, 14));
            viewButton.setBackground(Color.WHITE);
            viewButton.setForeground(Color.BLACK);

            viewButton.addActionListener(e -> viewDetailReport(report)); // Lógica para mostrar los detalles del report

            reportPanel.add(reportLabel, BorderLayout.CENTER);
            reportPanel.add(viewButton, BorderLayout.EAST);

            reportsPanel.add(reportPanel);
            reportsPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Espacio entre reports
        }

        reportsPanel.revalidate();
        reportsPanel.repaint();
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
