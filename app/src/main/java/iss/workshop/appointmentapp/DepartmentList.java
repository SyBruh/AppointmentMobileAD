package iss.workshop.appointmentapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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


    }
}