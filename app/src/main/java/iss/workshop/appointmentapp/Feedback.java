package iss.workshop.appointmentapp;

import java.io.Serializable;
public class Feedback implements Serializable{
    private int id;
    private String Description;
    private String feedback_type;

    public Feedback() {
    }

    public Feedback(String Description, String feedback_type) {
        this.Description = Description;
        this.feedback_type = feedback_type;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getFeedback_type() {
        return feedback_type;
    }

    public void setFeedback_type(String feedback_type) {
        this.feedback_type = feedback_type;
    }

}
