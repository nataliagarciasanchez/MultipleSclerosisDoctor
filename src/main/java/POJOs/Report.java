/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJOs;

import java.io.Serializable;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author laura
 */

public class Report implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Date date;
    private Patient patient;
    private List<Bitalino> bitalinos;
    private List<Symptom> symptoms;
    
    public Report(){
    super();
    }

    public Report(Integer id, Date date, Patient patient, List<Bitalino> bitalinos, List<Symptom> symptoms) {
        this.id = id;
        this.date = date;
        this.patient = patient;
        this.bitalinos = bitalinos;
        this.symptoms = symptoms;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public Patient getPatient() {
        return patient;
    }

    public List<Bitalino> getBitalinos() {
        return bitalinos;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public void setBitalinos(List<Bitalino> bitalinos) {
        this.bitalinos = bitalinos;
    }

    public void setSymptom(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    @Override
    public String toString() {
        return "Report{" + "id=" + id + ", date=" + date + ", patient=" + patient + ", bitalinos=" + bitalinos + ", symptom=" + symptoms + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.id);
        hash = 59 * hash + Objects.hashCode(this.date);
        hash = 59 * hash + Objects.hashCode(this.patient);
        hash = 59 * hash + Objects.hashCode(this.bitalinos);
        hash = 59 * hash + Objects.hashCode(this.symptoms);
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
        final Report other = (Report) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.patient, other.patient)) {
            return false;
        }
        if (!Objects.equals(this.bitalinos, other.bitalinos)) {
            return false;
        }
        return Objects.equals(this.symptoms, other.symptoms);
    }
    
    
}
