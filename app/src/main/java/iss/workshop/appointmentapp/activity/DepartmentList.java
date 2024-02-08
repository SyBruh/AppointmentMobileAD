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
import iss.workshop.appointmentapp.adapter.DepartmentAdapter;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Department;
import iss.workshop.appointmentapp.model.User;

public class DepartmentList extends AppCompatActivity {

    private User user;
    List<Department> departmentList = new ArrayList<>();
    ListView departmentlistview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department_list);
        departmentlistview = findViewById(R.id.DepartmentListView);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        DataService ds = new DataService(this);
        ds.GetDepartments(new DataService.GetDepartmentsListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<Department> departments) {
                DepartmentAdapter adapter = new DepartmentAdapter(getBaseContext(),departments);
                departmentList = departments;
                if (departmentlistview!=null){
                    departmentlistview.setAdapter(adapter);
                }
            }
        });

        departmentlistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(DepartmentList.this,DoctorList.class);
                intent.putExtra("user",user);
                intent.putExtra("departmentid",departmentList.get(position).getId());
                startActivity(intent);
            }
        });


    }
}