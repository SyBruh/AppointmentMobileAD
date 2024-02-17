package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.adapter.DoctorAdapter;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Department;
import iss.workshop.appointmentapp.model.Patient;
import iss.workshop.appointmentapp.model.Staff;
import iss.workshop.appointmentapp.model.User;

public class PredictDoctorList extends AppCompatActivity {

    ListView doctorlistview;
    private User user;

    private Patient patient;
    private Department departmentpredict;
    DoctorAdapter adapter;
    List<String> symptoms;
    List<Staff> staffList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_predict_doctor_list);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        patient = (Patient) intent.getSerializableExtra("patient");
        symptoms = intent.getStringArrayListExtra("symptoms");
        doctorlistview = findViewById(R.id.doctorlistview);
        DataService ds = new DataService(this);
        ds.GetDoctors(0, new DataService.GetDoctorsListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(),"Error Occur",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<Staff> staffs) {
                adapter = new DoctorAdapter(getBaseContext(),staffs);
                //adapter.setDepartmentid(departmentpredict.getId());
                staffList = staffs;
                if(doctorlistview!=null){
                    doctorlistview.setAdapter(adapter);
                }
            }
        });
        ds.Predict(patient.getId(), user.getId(), symptoms, new DataService.PredictListener() {
            @Override
            public void onError(String message) {

            }

            @Override
            public void onResponse(Department department) {
                departmentpredict = department;
                adapter.setDepartmentid(departmentpredict.getId());
                adapter.notifyDataSetChanged();
            }
        });
        doctorlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PredictDoctorList.this,DoctorDetail.class);
                intent.putExtra("user",user);
                intent.putExtra("staff",staffList.get(position));
                intent.putExtra("patient",patient);
                startActivity(intent);
            }
        });
    }

    public int getDepartmentid(){
        return departmentpredict.getId();
    }
}