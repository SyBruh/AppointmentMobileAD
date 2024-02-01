package iss.workshop.appointmentapp;


import androidx.annotation.NonNull;

import java.util.List;

public class Patient {

    private int id;
    private String name;
    private String address;
    private String sex;
    private String allergy;
    private String medical_condition;
    private String additional_information;

    public Patient() {

    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getSex() {
        return sex;
    }
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAllergy() {
        return allergy;
    }
    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }
    public String getMedical_condition() {
        return medical_condition;
    }
    public void setMedical_condition(String medical_condition) {
        this.medical_condition = medical_condition;
    }
    public String getAdditional_information() {
        return additional_information;
    }
    public void setAdditional_information(String additional_information) {
        this.additional_information = additional_information;
    }

    @NonNull
    @Override
    public String toString() {
        String str =  "name="+name+"&address="+address+"&sex="+sex+"&allergy="+allergy+"&medical_condition="+medical_condition+"&additional_information"+additional_information;
        return str;
    }
}

