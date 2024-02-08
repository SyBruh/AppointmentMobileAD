package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import iss.workshop.appointmentapp.R;

public class SuccessfulPageActivity extends AppCompatActivity {

    Button btnOK;
    TextView PatientName;
    TextView AppointmentID;
    TextView Date;
    TextView DoctorName;
    TextView DepartmentName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_page);
        btnOK=findViewById(R.id.btnOK);
        PatientName=findViewById(R.id.PatientName);
        AppointmentID=findViewById(R.id.AppointmentID);
        Date=findViewById(R.id.Date);
        DoctorName=findViewById(R.id.DoctorName);
        DepartmentName=findViewById(R.id.DepartmentName);

    }
}