package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.adapter.PatientAdapter;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Patient;
import iss.workshop.appointmentapp.model.User;

public class PatientsList extends AppCompatActivity {

    private User user;
    Button btnaddnewpatient;
    ListView patientlistview;

    List<Patient> patientslist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patients_list);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        btnaddnewpatient = findViewById(R.id.btnaddpatient);
        btnaddnewpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PatientsList.this,AddPatientActivity.class);
                intent.putExtra("user",user);
                startActivity(intent);
            }
        });
        patientlistview = findViewById(R.id.patientListView);
        DataService ds = new DataService(this);
        ds.GetPatients(user.getId(), new DataService.GetPatientsListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(),"Error Occur",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<Patient> patients) {
                PatientAdapter adapter = new PatientAdapter(getBaseContext(),patients);
                patientslist = patients;
                if(patientlistview!=null){
                    patientlistview.setAdapter(adapter);
                }
            }
        });
        patientlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getBaseContext(),PatientDetatilActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("patient",patientslist.get(position));
                startActivity(intent);
            }
        });
    }
}