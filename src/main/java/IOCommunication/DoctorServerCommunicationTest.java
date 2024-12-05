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
import POJOs.Role;
import POJOs.User;
import Report.ProcessReport;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;


/**
 *
 * @author noeli
 */
public class DoctorServerCommunicationTest {
    
    public static DoctorServerCommunication com; 
    public static DoctorServerCommunication.Send send;
    public static String macAddress = "98:D3:41:FD:4E:E8";
    public static Role role;
    
    public static void main(String[] args) {
        com= new DoctorServerCommunication("localhost", 9001);
        com.start();
        send= com.new Send();
        role=new Role();
        register();
        //login();
        //updateInfo();
        //viewPersonalInfo();
        //viewPatients();
        //checkReports
    }
    
    public static void register() {
        Doctor noelia = new Doctor("Dr.Noelia", "Auba");
        User user = new User("drProbando@gmail.com", "Password123", role);
        noelia.setUser(user);
        send.register(noelia);
    }

    public static void login() {
        Doctor doctor = send.login("drauba@gmail.com", "Password123");
        System.out.println(doctor);
    }

    public static void updateInfo() {
        Doctor doctor1 = send.login("drProbando@gmail.com", "Password123");
        System.out.println(doctor1);
        User user = doctor1.getUser();
        user.setRole(role);
        System.out.println("user\n" + user);
        String newPass = "Password456";
        if (Utilities.isValidPassword(newPass)) {
            user.setPassword(newPass);
            System.out.println("This is to check if the setter is working correctly: " + user.getPassword());
            send.updateInformation(user);
        }
        Doctor doctor2 = send.login("drProbando@gmail.com", newPass);

    }

    public static void viewPersonalInfo() {
        Doctor doctor = send.login("doctor.garcia@multipleSclerosis.com", "Password456");
        System.out.println(doctor);
    }

    public static void viewPatients() {
        Doctor doctor = send.login("doctor.garcia@multipleSclerosis.com", "Password456");
        List<Patient> patients = send.viewPatients(doctor);
        Iterator<Patient> it = patients.listIterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }

    }

    //SAME AS VIEWPATIENTS BUT SHOWS ALL REPORTS FROM PATIENTS 
    public static void checkReports() {
        Doctor doctor = send.login("doctor.garcia@multipleSclerosis.com", "Password456");
        List<Patient> patients = send.viewPatients(doctor);
        Iterator<Patient> it1 = patients.listIterator();
        List<Report> reports = null;

        Report report = null;
        while (it1.hasNext()) {
            reports = it1.next().getReports();
        }
        Iterator<Report> it2 = reports.listIterator();
        System.out.println("This is the list of all the reports the patients from this doctor: ");
        while (it2.hasNext()) {
            System.out.println(it2.next());
        }
        System.out.println("This is the list of the reports the doctor has received that day: ");
        while (it2.hasNext()) {
            report = it2.next();
            LocalDate date = it2.next().getDate().toLocalDate();
            if (isReportFromToday(date)) {
                System.out.println(report);
            }
        }
        //en el report que seleccione deberá aparece un espacio para escribir el feedback y mandarlo
        //devuelve el report seleccionado y se lo manda a sendFeedback
        sendFeedback2Server(report);
    }
    
    
    public static boolean isReportFromToday(LocalDate report_date){
        LocalDate todays_date=LocalDate.now();
        return report_date.equals(todays_date);
    }
    
    public static void sendFeedback2Server(Report report){
        
        Doctor doctor = send.login("doctor.garcia@multipleSclerosis.com", "Password456");
        Patient patient=report.getPatient();
        Bitalino bitalino_EMG=report.getBitalinos().get(0);
        Bitalino bitalino_ECG=report.getBitalinos().get(1);
        //before creating the feedback-> the signals from the patient are processed so the 
        //doctor knows if the parameters are abnormal and takes it into account in the feedback
        ProcessReport.analyzeSignalsReport(report, patient, bitalino_EMG);
        ProcessReport.analyzeSignalsReport(report, patient, bitalino_ECG);
        
        String message="This is the feedback message of the test";
        Feedback feedback=new Feedback(message,report.getDate(),doctor,patient);
        send.sendFeedback2Server(feedback);
    }
    
}

