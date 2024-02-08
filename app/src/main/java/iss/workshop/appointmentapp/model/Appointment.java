package iss.workshop.appointmentapp.model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment extends JSONObject implements Serializable {

    private int id;
    private LocalDate date;
    private LocalTime time;
    private int queue_number;

    private String MedicalCondition;
    private AppointmentStatusEnum status;

    private Staff staff;

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
//Mapping


    public Appointment() {
    }

    public Appointment(LocalDate date, LocalTime time, int queue_number, AppointmentStatusEnum status, String MedicalCondition) {
        this.date = date;
        this.time = time;
        this.queue_number = queue_number;
        this.status = status;
        this.MedicalCondition = MedicalCondition;
    }
    public Appointment(JSONObject json) {
        this.id = json.optInt("id", 0);
        this.date = LocalDate.parse(json.optString("date", ""));
        this.time = LocalTime.parse(json.optString("time", ""));
        this.queue_number = json.optInt("queue_number", 0);
        this.status = AppointmentStatusEnum.valueOf(json.optString("status", ""));
        this.MedicalCondition = json.optString("medical_condition","");
        this.staff = new Staff(json.optJSONObject("staff"));
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public int getQueue_number() {
        return queue_number;
    }

    public void setQueue_number(int queue_number) {
        this.queue_number = queue_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppointmentStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AppointmentStatusEnum status) {
        this.status = status;
    }

    @NonNull
    @Override
    public String toString() {
        String str =  "date="+date+"&time="+time+"&queue_number="+queue_number+"&status="+status.toString()+"&medical_condition="+MedicalCondition;
        return str;
    }

    public String getMedicalCondition() {
        return MedicalCondition;
    }

    public void setMedicalCondition(String medicalCondition) {
        MedicalCondition = medicalCondition;
    }
}
