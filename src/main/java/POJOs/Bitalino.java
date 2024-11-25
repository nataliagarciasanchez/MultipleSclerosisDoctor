/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package POJOs;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

/**
 *
 * @author noeli
 */

public class Bitalino implements Serializable {

    private static final long serialVersionUID = 123456000L;

    private Integer id;
    private Date date;
    private SignalType signal_type;
    private final Float duration = 60.0F;//seconds-> 1 min
    private Report report;

    public Bitalino() {
        super();
    }

    public Bitalino(Date date, SignalType signal_type) {
        this.date = date;
        this.signal_type = signal_type;
    }

    public Bitalino(Integer id, Date date, SignalType signal_type) {
        this.id = id;
        this.date = date;
        this.signal_type = signal_type;
    }

    public Bitalino(Integer id, Date date, SignalType signal_type, Report report) {
        this.id = id;
        this.date = date;
        this.signal_type = signal_type;
        this.report = report;
    }

    public Bitalino(Date date, SignalType signal_type, Report report) {
        this.date = date;
        this.signal_type = signal_type;
        this.report = report;
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

    public SignalType getSignal_type() {
        return signal_type;
    }

    public Float getDuration() {
        return duration;
    }

    public Report getReport() {
        return report;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setSignal_type(SignalType signal_type) {
        this.signal_type = signal_type;
    }

    public void setReport(Report report) {
        this.report = report;
    }

    @Override
    public String toString() {
        return "Bitalino{" + "id=" + id + ", date=" + date + ", signal_type=" + signal_type + ", duration=" + duration + ", report=" + report + '}';
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.id);
        hash = 79 * hash + Objects.hashCode(this.date);
        hash = 79 * hash + Objects.hashCode(this.signal_type);
        hash = 79 * hash + Objects.hashCode(this.duration);
        hash = 79 * hash + Objects.hashCode(this.report);
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
        final Bitalino other = (Bitalino) obj;
        if (!Objects.equals(this.signal_type, other.signal_type)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.date, other.date)) {
            return false;
        }
        if (!Objects.equals(this.duration, other.duration)) {
            return false;
        }
        return Objects.equals(this.report, other.report);
    }

}