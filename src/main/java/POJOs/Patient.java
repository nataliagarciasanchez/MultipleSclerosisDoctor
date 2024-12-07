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

public class Patient implements Serializable{
    
    private static final long serialVersionUID = 8419572012345678901L;
    
    private User user;
    
    private Integer id;
    private String name;
    private String surname;
    private String NIF;
    private Date dob;
    private Gender gender;
    private String phone;
    private Doctor doctor;
    private List<Report> reports;
   
    
    public Patient() {
        super();
    }
    
    /**
     * Constructor used to create the patient wo/ the doctor assigned
     * It will later be assigned by the server
     * @param name
     * @param surname
     * @param NIF
     * @param dob
     * @param gender
     * @param phone 
     */
    public Patient(String name, String surname, String NIF, Date dob, Gender gender, String phone) {
        
        this.name = name;
        this.surname=surname;
        this.NIF=NIF;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        
    }

    public Patient(String name, String surname, String NIF, Date dob, Gender gender, String phone, Doctor doctor, User user) {
        
        this.name = name;
        this.surname=surname;
        this.NIF=NIF;
        this.dob = dob;
        this.gender = gender;
        this.phone = phone;
        this.doctor=doctor;
        this.user=user;
        
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getDob() {
        return dob;
    }

    public Gender getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public List<Report> getReports() {
        return reports;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public String getSurname() {
        return surname;
    }
    
    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setDoctor(Doctor doctor) {
        this.doctor = doctor;
    }

    public void setReports(List<Report> reports) {
        this.reports = reports;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNIF() {
        return NIF;
    }

    public void setNIF(String NIF) {
        this.NIF = NIF;
    }
    
    

    @Override
    public String toString() {
        return "User: " + user + "\n"
                + "ID: " + id + "\n"
                + "Name: " + name + "\n"
                + "Surname: " + surname + "\n"
                + "NIF: " + NIF + "\n"
                + "Date of Birth: " + dob + "\n"
                + "Gender: " + gender + "\n"
                + "Phone: " + phone;
    }



    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.dob);
        hash = 53 * hash + Objects.hashCode(this.gender);
        hash = 53 * hash + Objects.hashCode(this.phone);
        hash = 53 * hash + Objects.hashCode(this.doctor);
        hash = 53 * hash + Objects.hashCode(this.reports);
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
        final Patient other = (Patient) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.dob, other.dob)) {
            return false;
        }
        if (this.gender != other.gender) {
            return false;
        }
        if (!Objects.equals(this.phone, other.phone)) {
            return false;
        }
        if (!Objects.equals(this.doctor, other.doctor)) {
            return false;
        }
        return Objects.equals(this.reports, other.reports);
    }
    
    

    
    
}
