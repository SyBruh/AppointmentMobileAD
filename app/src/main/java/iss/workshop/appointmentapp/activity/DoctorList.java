package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.adapter.DoctorAdapter;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Staff;
import iss.workshop.appointmentapp.model.User;

public class DoctorList extends AppCompatActivity {
    ListView doctorlistview;
    private User user;
    private int departmentid;
    List<Staff> staffList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_list);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        departmentid = intent.getIntExtra("departmentid",0);
        doctorlistview = findViewById(R.id.doctorlistview);
        DataService ds = new DataService(this);
        ds.GetDoctors(departmentid, new DataService.GetDoctorsListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(),"Error Occur",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<Staff> staffs) {
                DoctorAdapter adapter = new DoctorAdapter(getBaseContext(),staffs);
                staffList = staffs;
                if(doctorlistview!=null){
                    doctorlistview.setAdapter(adapter);
                }
            }
        });
        doctorlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DoctorList.this,DoctorDetail.class);
                intent.putExtra("user",user);
                intent.putExtra("staff",staffList.get(position));
                startActivity(intent);
            }
        });
    }
}