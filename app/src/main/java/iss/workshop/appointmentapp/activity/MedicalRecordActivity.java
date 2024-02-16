package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.adapter.MedicalRecordAdapter;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Appointment;
import iss.workshop.appointmentapp.model.Patient;
import iss.workshop.appointmentapp.model.User;

public class MedicalRecordActivity extends AppCompatActivity {

    private Patient patient;
    private User user;
    ListView listrecord;

    List<Appointment> filter = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_record);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        patient = (Patient) intent.getSerializableExtra("patient");
        listrecord = findViewById(R.id.listrecordview);
        DataService ds = new DataService(this);
        ds.GetAppointments(user.getId(), new DataService.GetAppointmentsListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<Appointment> appointments) {
                for (Appointment appointment: appointments){
                    if (appointment.getPatient().getId() == patient.getId()){
                        filter.add(appointment);
                    }
                }
                MedicalRecordAdapter adapter = new MedicalRecordAdapter(MedicalRecordActivity.this,filter);
                listrecord.setAdapter(adapter);
            }
        });
    }
}