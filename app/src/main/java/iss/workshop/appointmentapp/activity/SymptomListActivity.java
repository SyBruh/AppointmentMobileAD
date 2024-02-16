package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.adapter.PatientAdapter;
import iss.workshop.appointmentapp.adapter.StringAdapter;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Patient;
import iss.workshop.appointmentapp.model.User;

public class SymptomListActivity extends AppCompatActivity {

    ListView symptomlist;
    List<String> symptomscheck;

    Patient patient;
    List<Patient> patientList;
    Button btnSkip;
    Button btnNext;
    private User user;
    String symptomgroup;
    StringAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_symptom_list);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        symptomgroup = intent.getStringExtra("symptom-group");
        symptomlist = findViewById(R.id.SymptonList);
        btnNext =findViewById(R.id.btnNext);
        btnSkip = findViewById(R.id.btnSkip);
        DataService ds = new DataService(this);
        ds.GetSymptoms(symptomgroup, new DataService.GetSymptomsListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(List<String> symptoms) {
                symptomscheck = symptoms;
                adapter = new StringAdapter(SymptomListActivity.this,R.layout.symptomitem,symptomscheck);
                symptomlist.setAdapter(adapter);

            }
        });
        ds.GetPatients(user.getId(), new DataService.GetPatientsListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(List<Patient> patients) {
                patientList = patients;
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog(ds).show();
               // Toast.makeText(SymptomListActivity.this,"OK",Toast.LENGTH_LONG).show();
            }
        });
    }
    public Dialog CreateDialog(DataService ds){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Patient")
                .setAdapter(new PatientAdapter(this, patientList), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        List<String> symptoms = adapter.getchecksymptoms();
                        patient = patientList.get(which);
                        Intent intent = new Intent(SymptomListActivity.this,PredictDoctorList.class);
                        intent.putExtra("user",user);
                        intent.putExtra("patient",patient);
                        intent.putStringArrayListExtra("symptoms", (ArrayList<String>) symptoms);
                        startActivity(intent);
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
        return builder.create();
    }
}