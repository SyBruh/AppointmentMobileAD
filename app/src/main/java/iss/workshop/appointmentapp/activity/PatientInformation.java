package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Patient;
import iss.workshop.appointmentapp.model.User;

public class PatientInformation extends AppCompatActivity implements View.OnClickListener {
    private Patient patient;
    private User user;
    TextView Name;
    TextView Sex;
    TextView Address;
    TextView Allergy;
    TextView Additional;
    Button btnviewrecords;
    Button btnpatientedit;
    Button btnpatientdelete;

    DataService ds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information);
        Intent intent = getIntent();
        patient = (Patient) intent.getSerializableExtra("patient");
        user = (User) intent.getSerializableExtra("user");
        Name=findViewById(R.id.Name);
        Name.setText(patient.getName());
        Sex=findViewById(R.id.Sex);
        Sex.setText(patient.getSex());
        Address=findViewById(R.id.Address);
        Address.setText(patient.getAddress());
        Allergy=findViewById(R.id.Address);
        Allergy.setText(patient.getAllergy());
        Additional=findViewById(R.id.Additional);
        Additional.setText(patient.getAdditional_information());
        btnviewrecords=findViewById(R.id.btnviewrecords);
        btnviewrecords.setOnClickListener(this);
        btnpatientdelete=findViewById(R.id.btnpatientdelete);
        btnpatientdelete.setOnClickListener(this);
        btnpatientedit=findViewById(R.id.btnpatientedit);
        btnpatientedit.setOnClickListener(this);
        ds = new DataService(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnviewrecords){
            Intent intent = new Intent(PatientInformation.this,MedicalRecordActivity.class);
            intent.putExtra("user",user);
            intent.putExtra("patient",patient);
            startActivity(intent);
        } else if (id == R.id.btnpatientedit) {
            Intent intent = new Intent(PatientInformation.this, PatientDetatilActivity.class);
            intent.putExtra("user",user);
            intent.putExtra("patient",patient);
            startActivity(intent);
        } else if (id == R.id.btnpatientdelete) {
            ds.RemovePatient(user.getId(), patient.getId(), new DataService.RemovePatientListener() {
                @Override
                public void onError(String message) {
                    Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onResponse(String success) {
                    Toast.makeText(getBaseContext(),success,Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(PatientInformation.this,PatientsList.class);
                    intent.putExtra("user",user);
                    startActivity(intent);
                }
            });
        }
    }
}