package iss.workshop.appointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class AddPatientActivity extends AppCompatActivity {

    EditText txtadpatientname;
    RadioButton radiomale;
    RadioButton radiofemale;
    RadioButton radioother;
    RadioGroup radsex;
    EditText txtadaddress;
    EditText txtadallergy;
    EditText txtadaddinfo;
    Button btnsavepatient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_patient);
        txtadpatientname = findViewById(R.id.txtadpatientname);
        radiomale = findViewById(R.id.radiomale);
        radiofemale = findViewById(R.id.radiofemale);
        radioother = findViewById((R.id.radioother));
        radsex = findViewById(R.id.radsex);
        txtadaddress = findViewById(R.id.txtadaddress);
        txtadallergy = findViewById(R.id.txtadallergy);
        txtadaddinfo = findViewById(R.id.txtadaddinfo);
        btnsavepatient = findViewById(R.id.btnsavepatient);


    }
}