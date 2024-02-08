package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.adapter.AppointmentAdapter;
import iss.workshop.appointmentapp.adapter.MedicalRecordAdapter;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Appointment;
import iss.workshop.appointmentapp.model.Patient;
import iss.workshop.appointmentapp.model.User;

public class PatientDetatilActivity extends AppCompatActivity {

    private Patient patient;
    private User user;
    TextView txtpatientnamedetail;
    TextView txtpatientaddressdetail;
    RadioButton radiomale;
    RadioButton radiofemale;
    RadioButton radioother;
    RadioGroup radsex;
    TextView txtpatientallergydetail;
    TextView txtaddinfodetail;
    ListView listrecord;

    List<Appointment> filter = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detatil);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        patient = (Patient) intent.getSerializableExtra("patient");
        txtpatientnamedetail = findViewById(R.id.txtpatientnamedetail);
        txtpatientnamedetail.setText(patient.getName());
        txtpatientaddressdetail = findViewById(R.id.txtpatientaddressdetail);
        txtpatientaddressdetail.setText(patient.getAddress());
        txtpatientallergydetail = findViewById(R.id.txtpatientallergydetail);
        txtpatientallergydetail.setText(patient.getAllergy());
        txtaddinfodetail = findViewById(R.id.txtaddinfodetail);
        txtaddinfodetail.setText(patient.getAdditional_information());
        radiomale = findViewById(R.id.radiomale);
        radiofemale = findViewById(R.id.radiofemale);
        radioother = findViewById((R.id.radioother));
        radsex = findViewById(R.id.radsex);
        switch (patient.getSex()){
            case "male" : radsex.check(R.id.radiomale);
            case "female" : radsex.check(R.id.radiofemale);
            case "other" : radsex.check(R.id.radioother);
        }
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
                MedicalRecordAdapter adapter = new MedicalRecordAdapter(PatientDetatilActivity.this,filter);
                listrecord.setAdapter(adapter);
            }
        });
    }
}