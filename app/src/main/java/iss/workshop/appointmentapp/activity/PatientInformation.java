package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import iss.workshop.appointmentapp.R;

public class PatientInformation extends AppCompatActivity {


    TextView Name;
    TextView Sex;
    TextView Address;
    TextView Allergy;
    TextView Additional;
    Button btnviewrecords;
    Button btnpatientedit;
    Button btnpatientdelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_information);
        Name=findViewById(R.id.Name);
        Sex=findViewById(R.id.Sex);
        Address=findViewById(R.id.Address);
        Allergy=findViewById(R.id.Address);
        Additional=findViewById(R.id.Additional);
        btnviewrecords=findViewById(R.id.btnviewrecords);
        btnpatientdelete=findViewById(R.id.btnpatientdelete);
        btnpatientedit=findViewById(R.id.btnpatientedit);
    }
}