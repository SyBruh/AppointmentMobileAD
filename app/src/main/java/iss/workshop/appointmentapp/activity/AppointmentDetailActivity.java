package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Appointment;
import iss.workshop.appointmentapp.model.AppointmentStatusEnum;
import iss.workshop.appointmentapp.model.User;

public class AppointmentDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private User user;
    private Appointment appointment;
    TextView txtpatientname;
    TextView Additional;
    TextView txtQueueNumber;
    TextView Date;
    TextView DoctorName;
    TextView Status;
    Button btnok;
    Button btnFeedback;
    Button btnCancel;
    DataService ds;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_detail);
        Intent intent = getIntent();
        user= (User) intent.getSerializableExtra("user");
        appointment = (Appointment) intent.getSerializableExtra("appointment");
        txtpatientname = findViewById(R.id.txtpatientname);
        txtpatientname.setText(appointment.getPatient().getName());
        txtQueueNumber = findViewById(R.id.txtqueuenumber);
        txtQueueNumber.setText(String.valueOf(appointment.getQueue_number()));
        Date = findViewById(R.id.Date);
        Date.setText(appointment.getDate().toString());
        Additional = findViewById(R.id.Additional);
        Additional.setText(appointment.getStaff().getDepartment().getName());
        DoctorName = findViewById(R.id.DoctorName);
        DoctorName.setText(appointment.getStaff().getName());
        Status = findViewById(R.id.Status);
        Status.setText(appointment.getStatus().toString());
        btnok = findViewById(R.id.BtnOK);
        btnok.setOnClickListener(this);
        btnFeedback = findViewById(R.id.BtnFeedback);
        btnFeedback.setOnClickListener(this);
        btnCancel = findViewById(R.id.BtnCancelAppointment);
        btnCancel.setOnClickListener(this);
        if (appointment.getStatus().equals(AppointmentStatusEnum.Proceeding)||appointment.getStatus().equals(AppointmentStatusEnum.Cancelled)){
            btnFeedback.setVisibility(View.INVISIBLE);
        }else{
            btnCancel.setVisibility(View.INVISIBLE);
        }
        ds = new DataService(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.BtnOK){
            
        }
        else if(id == R.id.BtnFeedback){
            Intent intent = new Intent(AppointmentDetailActivity.this,FeedbackActivity.class);
            intent.putExtra("user",user);
            intent.putExtra("appointment",appointment);
            startActivity(intent);
        }
        else if (id==R.id.BtnCancelAppointment) {
            ds.CancelAppointment(appointment.getId(), new DataService.CancelAppointmentListener() {
                @Override
                public void onError(String message) {

                }

                @Override
                public void onResponse(Appointment appointment) {
                    Toast.makeText(getBaseContext(),"Cancel Successful",Toast.LENGTH_LONG).show();
                    Status.setText(appointment.getStatus().toString());
                }
            });
        }
    }
}