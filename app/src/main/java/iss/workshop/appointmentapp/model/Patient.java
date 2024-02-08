package iss.workshop.appointmentapp.model;


import androidx.annotation.NonNull;

import java.io.Serializable;
import java.util.List;

public class Patient implements Serializable {

    private int id;
    private String name;
    private String address;
    private String sex;
    private String allergy;
    private String medical_condition;
    private String additional_information;

    public Patient() {

    }

    public Patient(String name, String address, String sex, String allergy, String medical_condition, String additional_information) {
        this.name = name;
        this.address = address;
        this.sex = sex;
        this.allergy = allergy;
        this.medical_condition = medical_condition;
        this.additional_information = additional_information;
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

