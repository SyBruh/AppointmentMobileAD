package iss.workshop.appointmentapp.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Schedule extends JSONObject implements Serializable {
    private int id;
    private LocalDate date;
    private LocalTime time_start;
    private LocalTime time_end;
    private int patient_slot;

    public Schedule() {
    }

    public Schedule(LocalDate date, LocalTime time_start, LocalTime time_end, int patient_slot) {
        this.date = date;
        this.time_start = time_start;
        this.time_end = time_end;
        this.patient_slot = patient_slot;
    }
    public Schedule(JSONObject json) {
        this.id = json.optInt("id", 0);
        this.date = LocalDate.parse(json.optString("date", ""));
        this.time_start = LocalTime.parse(json.optString("time_start", ""));
        this.time_end = LocalTime.parse(json.optString("time_end", ""));
        this.patient_slot = json.optInt("patient_slot", 0);
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime_start() {
        return time_start;
    }

    public void setTime_start(LocalTime time_start) {
        this.time_start = time_start;
    }

    public LocalTime getTime_end() {
        return time_end;
    }

    public void setTime_end(LocalTime time_end) {
        this.time_end = time_end;
    }

    public int getPatient_slot() {
        return patient_slot;
    }

    public void setPatient_slot(int patient_slot) {
        this.patient_slot = patient_slot;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}

