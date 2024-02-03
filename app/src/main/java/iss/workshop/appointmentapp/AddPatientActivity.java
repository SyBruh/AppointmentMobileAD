package iss.workshop.appointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class AddPatientActivity extends AppCompatActivity {

    private User user;
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
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        txtadpatientname = findViewById(R.id.txtadpatientname);
        radiomale = findViewById(R.id.radiomale);
        radiofemale = findViewById(R.id.radiofemale);
        radioother = findViewById((R.id.radioother));
        radsex = findViewById(R.id.radsex);
        txtadaddress = findViewById(R.id.txtadaddress);
        txtadallergy = findViewById(R.id.txtadallergy);
        txtadaddinfo = findViewById(R.id.txtadaddinfo);
        btnsavepatient = findViewById(R.id.btnsavepatient);
        DataService ds = new DataService(this);
        btnsavepatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int radid = radsex.getCheckedRadioButtonId();
                RadioButton check = findViewById(radid);
                String name = String.valueOf(txtadpatientname.getText());
                String sex = (String) check.getText();
                String address = String.valueOf(txtadaddress.getText());
                String allergy = String.valueOf(txtadallergy.getText());
                String addinfo = String.valueOf(txtadaddinfo.getText());
                Patient patient = new Patient(name,address,sex,allergy,null,addinfo);
                ds.AddPatient(user.getId(), patient, new DataService.AddPatientListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(AddPatientActivity.this, message, Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(Patient patient) {
                        Toast.makeText(AddPatientActivity.this, "patient save successfully", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });


    }
}

