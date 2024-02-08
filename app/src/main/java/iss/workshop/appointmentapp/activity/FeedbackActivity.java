package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.RatingBar;

import iss.workshop.appointmentapp.R;

public class FeedbackActivity extends AppCompatActivity {

    RatingBar ratingBar;
    EditText txtFeedback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        ratingBar=findViewById(R.id.ratingBar);
        txtFeedback=findViewById(R.id.txtFeedback);

    }
}