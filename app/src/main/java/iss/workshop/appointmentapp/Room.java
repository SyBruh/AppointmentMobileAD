package iss.workshop.appointmentapp;


import java.io.Serializable;

public class Room implements Serializable{
    private int id;
    private String name;

    public Room() {
    }

    public Room(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
