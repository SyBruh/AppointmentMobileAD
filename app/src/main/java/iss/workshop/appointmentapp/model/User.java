package iss.workshop.appointmentapp.model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.Serializable;

public class User extends JSONObject implements Serializable {

    private int id;

    private String name;
    private String password;
    public User() {

    }
    public User(JSONObject json){
        this.id = json.optInt("id");
        this.name = json.optString("name");
        this.password = json.optString("password");
    }
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    @NonNull
    @Override
    public String toString() {
        String str =  "name="+name+"&password="+password;
        return str;
    }

}

