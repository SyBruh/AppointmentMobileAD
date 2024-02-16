package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Appointment;
import iss.workshop.appointmentapp.model.Feedback;
import iss.workshop.appointmentapp.model.User;

public class FeedbackActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText txtFeedback;
    Button btnSubmit;
    private User user;
    private Appointment appointment;

    private Feedback feedbackog = new Feedback();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        appointment = (Appointment) intent.getSerializableExtra("appointment");
        ratingBar=findViewById(R.id.ratingBar);
        txtFeedback=findViewById(R.id.txtFeedback);
        btnSubmit = findViewById(R.id.btnSubmit);
        DataService ds = new DataService(this);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                feedbackog.setScore(ratingBar.getRating());
                feedbackog.setDescription(txtFeedback.getText().toString());
                ds.AddFeedback(user.getId(), appointment.getId(), feedbackog, new DataService.AddFeedbackListener() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(Feedback feedback) {
                        feedbackog = feedback;
                        Toast.makeText((Context) FeedbackActivity.this,"Successful",Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(FeedbackActivity.this,AppointmentHistory.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}