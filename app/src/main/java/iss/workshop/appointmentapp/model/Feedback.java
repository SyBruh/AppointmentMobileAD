package iss.workshop.appointmentapp.model;

import androidx.annotation.NonNull;

import org.json.JSONObject;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Feedback  extends JSONObject implements Serializable {
    private int id;
    private String Description;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private User user;

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    private double score;

    public Feedback() {
    }

    public Feedback(String Description, double score) {
        this.Description = Description;
        this.score  = score;
    }
    public Feedback(JSONObject json){
        this.id = json.optInt("id", 0);
        this.score = json.optDouble("score");
        this.Description = json.optString("description", "");
        this.user = new User(json.optJSONObject("user"));
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    @NonNull
    @Override
    public String toString() {
        String str = "Description="+Description+"&score="+score;
        return str;
    }
}
