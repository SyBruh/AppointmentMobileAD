package iss.workshop.appointmentapp;

import java.io.Serializable;

public class Symptom implements Serializable{
    private int id;
    private String name;

    public Symptom() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}