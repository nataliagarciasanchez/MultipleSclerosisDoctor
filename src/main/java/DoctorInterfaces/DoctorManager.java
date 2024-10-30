/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DoctorInterfaces;

import POJOs.Doctor;
import POJOs.Specialty;
import POJOs.User;
import java.util.List;

/**
 *
 * @author nataliagarciasanchez
 */
public interface DoctorManager {
    
    public void createDoctor(Doctor d);
    public Doctor viewMyInfo(Integer id);
    public List<Doctor> getListOfDoctors();
    public void removeDoctorById(Integer id);
    public void modifyDoctorInfo(Integer id, String name, Specialty specialty, User user);
    public Doctor searchDoctorById(Integer id);
    public List<Doctor> searchDoctorByName(String name);//es una lista porque puede haber doctores con el mismo nombre
    public void assignPatient2Doctor(Integer doctorId, Integer patientId);
    public void removePatientFromDoctor(Integer doctorId, Integer patientId);
   
}
