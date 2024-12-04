/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJOs;

import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author Andreoti
 */
public class Feedback {
    
     private static final long serialVersionUID = 1L;
     private Integer id;
     private String message;
     private Date date;
     private Doctor doctor;
     private Patient patient;
     
     public Feedback(){
     super();
     }

    public Feedback(Integer id, String message, Date date, Doctor doctor, Patient patient) {
        this.id = id;
        this.message = message;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
    }
    public Feedback(String message, Date date, Doctor doctor, Patient patient) {
        
        this.message = message;
        this.date = date;
        this.doctor = doctor;
        this.patient = patient;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }
   

    public Date getDate() {
        return date;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public Patient getPatient() {
        return patient;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Feedback{");
        sb.append("message=").append(message);
        sb.append(", date=").append(date);
        sb.append(", doctor=").append(doctor);
        sb.append(", patient=").append(patient);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 43 * hash + Objects.hashCode(this.message);
        hash = 43 * hash + Objects.hashCode(this.date);
        hash = 43 * hash + Objects.hashCode(this.doctor);
        hash = 43 * hash + Objects.hashCode(this.patient);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Feedback other = (Feedback) obj;
        if (!Objects.equals(this.message, other.message)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.doctor, other.doctor)) {
            return false;
        }
        return Objects.equals(this.patient, other.patient);
    }
     
     
     
     
    
}
