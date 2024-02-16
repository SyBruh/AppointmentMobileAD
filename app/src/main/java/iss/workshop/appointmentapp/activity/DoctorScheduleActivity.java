package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.adapter.PatientAdapter;
import iss.workshop.appointmentapp.adapter.ScheduleAdapter;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Appointment;
import iss.workshop.appointmentapp.model.AppointmentStatusEnum;
import iss.workshop.appointmentapp.model.Patient;
import iss.workshop.appointmentapp.model.Schedule;
import iss.workshop.appointmentapp.model.User;

public class DoctorScheduleActivity extends AppCompatActivity {

    ListView doctorschedulelistview;
    List<Schedule> schedulelist = new ArrayList<>();
    private Patient patient;
    private User user;
    private int staffid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_schedule);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        staffid = intent.getIntExtra("staffid",0);
        patient = (Patient) intent.getSerializableExtra("patient");
        doctorschedulelistview = findViewById(R.id.doctorschedulelistview);
        DataService ds = new DataService(this);
        ds.GetSchedules(staffid, new DataService.GetSchedulesListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(),"Error Occur",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<Schedule> schedules) {
                ScheduleAdapter adapter = new ScheduleAdapter(getBaseContext(),schedules);
                schedulelist = schedules;
                if(doctorschedulelistview!=null){
                    doctorschedulelistview.setAdapter(adapter);
                }
            }
        });
        doctorschedulelistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Schedule schedule = schedulelist.get(position);
                AlertDialog.Builder builder = new AlertDialog.Builder(DoctorScheduleActivity.this);
                builder.setTitle("Checking the Booking Time")
                        .setMessage("Are you sure to make appointment for this time between " + schedule.getTime_start().toString() + " and "+ schedule.getTime_end().toString())
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Appointment appointment = new Appointment(schedule.getDate(),schedule.getTime_start(),schedule.getPatient_slot()+1, AppointmentStatusEnum.Proceeding,null);
                                ds.AddAppointment(user.getId(), staffid, patient.getId(), appointment, new DataService.AddAppointmentListener() {
                                    @Override
                                    public void onError(String message) {
                                        Toast.makeText(getBaseContext(),"Error Occur",Toast.LENGTH_LONG).show();
                                    }

                                    @Override
                                    public void onResponse(Appointment appointment) {
                                        Intent intent = new Intent(DoctorScheduleActivity.this, SuccessfulPageActivity.class);
                                        intent.putExtra("user",user);
                                        intent.putExtra("appointment",appointment);
                                        startActivity(intent);
                                    }
                                });
                            }
                        });
                builder.create().show();
            }
        });


    }

}