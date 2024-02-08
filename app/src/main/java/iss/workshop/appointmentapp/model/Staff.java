package iss.workshop.appointmentapp.model;

import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Staff extends JSONObject implements Serializable {
    private int id;
    private String name;
    private String password;
    private String designation;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    private Department department;

    public Staff() {
    }

    public Staff(JSONObject json){
        this.id = json.optInt("id", 0);
        this.name = json.optString("name", "");
        this.password = json.optString("password", "");
        this.designation = json.optString("designation", "");;
        this.department = new Department(json.optJSONObject("department"));
    }

    public Staff(String name, String password, String designation, Department department) {
        this.name = name;
        this.password = password;
        this.designation = designation;
        this.department = department;
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
