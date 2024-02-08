package iss.workshop.appointmentapp.model;

import java.io.Serializable;


public class Disease implements Serializable {
    private int id;
    private String name;

    public Disease() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
