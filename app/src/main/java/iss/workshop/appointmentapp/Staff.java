package iss.workshop.appointmentapp;

public class Staff {
    private int id;
    private String name;
    private String password;
    private String designation;

    public Staff() {
    }

    public Staff(String name, String password, String designation) {
        this.name = name;
        this.password = password;
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
