package iss.workshop.appointmentapp;

import java.io.Serializable;
import java.util.List;

public class Department implements Serializable {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private int id;
    private String name;
    public Department() {

    }

    public Department(String name) {
        this.name = name;
    }
}
