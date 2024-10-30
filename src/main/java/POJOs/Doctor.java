/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJOs;

import POJOs.Feedback;
import POJOs.Patient;
import POJOs.User;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author nataliagarciasanchez
 */
@Entity
@Table (name= "doctors")
public class Doctor implements Serializable{
    
    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue (generator = "doctors")
    @TableGenerator(name = "doctors", table = "sqlite_sequence",  pkColumnName = "name", valueColumnName = "seq", pkColumnValue = "doctors")
    private Integer id;
    private String name;
    private String specialty;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Patient> patients;
    
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Feedback> feedback; 
    
    public Doctor(){
    super();
    }

    public Doctor(String name, Integer id, String specialty, User user) {
        this.name = name;
        this.id = id;
        this.specialty = specialty;
        this.user = user;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getName() {
        return name;
    }

    public Integer getId() {
        return id;
    }

    public String getSpecialty() {
        return specialty;
    }

    public User getUser() {
        return user;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Doctor{" + "name=" + name + ", id=" + id + ", specialty=" + specialty + ", user=" + user + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.id);
        hash = 53 * hash + Objects.hashCode(this.specialty);
        hash = 53 * hash + Objects.hashCode(this.user);
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
        final Doctor other = (Doctor) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.specialty, other.specialty)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return Objects.equals(this.user, other.user);
    }
    

}
