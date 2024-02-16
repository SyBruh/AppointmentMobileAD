package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

    Button btnupdatepatientdetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detatil);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        patient = (Patient) intent.getSerializableExtra("patient");
        txtpatientnamedetail = findViewById(R.id.txtpatientnamedetail);
        txtpatientaddressdetail = findViewById(R.id.txtpatientaddressdetail);
        txtpatientallergydetail = findViewById(R.id.txtpatientallergydetail);
        txtaddinfodetail = findViewById(R.id.txtaddinfodetail);
        radiomale = findViewById(R.id.radiomale);
        radiofemale = findViewById(R.id.radiofemale);
        radioother = findViewById((R.id.radioother));
        radsex = findViewById(R.id.radsex);
        btnupdatepatientdetail = findViewById(R.id.btnupdatepatientdetail);
        settinginterface();
        DataService ds = new DataService(this);
        btnupdatepatientdetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                patient.setName(txtpatientnamedetail.getText().toString());
                patient.setAddress(txtpatientaddressdetail.getText().toString());
                RadioButton radcheck = findViewById(radsex.getCheckedRadioButtonId());
                patient.setSex(radcheck.getText().toString());
                patient.setAllergy(txtpatientallergydetail.getText().toString());
                patient.setAdditional_information(txtaddinfodetail.getText().toString());
                ds.UpdatePatient(user.getId(),patient, new DataService.UpdatePatientListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(Patient patient) {
                        Toast.makeText(getBaseContext(),"Update successful",Toast.LENGTH_LONG).show();
                        settinginterface();
                    }
                });
            }
        });
    }

    public void settinginterface(){
        txtpatientnamedetail.setText(patient.getName());
        txtpatientaddressdetail.setText(patient.getAddress());
        txtpatientallergydetail.setText(patient.getAllergy());
        txtaddinfodetail.setText(patient.getAdditional_information());
        switch (patient.getSex()){
            case "male" : radsex.check(R.id.radiomale);break;
            case "female" : radsex.check(R.id.radiofemale);break;
            case "other" : radsex.check(R.id.radioother);break;
        }
    }
}