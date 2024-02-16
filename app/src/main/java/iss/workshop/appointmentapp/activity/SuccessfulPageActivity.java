package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.model.Appointment;
import iss.workshop.appointmentapp.model.User;

public class SuccessfulPageActivity extends AppCompatActivity {

    private Appointment appointment;
    private User user;
    Button btnOK;
    TextView PatientName;
    TextView QueueNumber;
    TextView Date;
    TextView DoctorName;
    TextView DepartmentName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_page);
        Intent intent = getIntent();
        appointment = (Appointment) intent.getSerializableExtra("appointment");
        user = (User) intent.getSerializableExtra("user");
        btnOK=findViewById(R.id.btnOK);
        PatientName=findViewById(R.id.PatientName);
        PatientName.setText(appointment.getPatient().getName());
        QueueNumber=findViewById(R.id.QueueNumber);
        QueueNumber.setText(String.valueOf(appointment.getQueue_number()));
        Date=findViewById(R.id.Date);
        Date.setText(appointment.getDate().toString());
        DoctorName=findViewById(R.id.DoctorName);
        DoctorName.setText(appointment.getStaff().getName());
        DepartmentName=findViewById(R.id.DepartmentName);
        DepartmentName.setText(appointment.getStaff().getDepartment().getName());
        btnOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SuccessfulPageActivity.this, DashBoardActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });

    }
}