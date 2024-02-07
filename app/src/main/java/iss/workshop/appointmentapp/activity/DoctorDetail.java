package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.adapter.PatientAdapter;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Feedback;
import iss.workshop.appointmentapp.model.Patient;
import iss.workshop.appointmentapp.model.Staff;
import iss.workshop.appointmentapp.model.User;

public class DoctorDetail extends AppCompatActivity {
    private User user;
    private Staff staff;
    TextView txtdoctornamedetail;
    TextView txtdepartmentnamedetail;
    Button btnmakeappointment;
    ListView feedbacklistview;
    List<Feedback> feedbackList = new ArrayList<>();
    List<Patient> patientList = new ArrayList<>();
    Patient patient = new Patient();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        staff = (Staff) intent.getSerializableExtra("staff");
        txtdoctornamedetail = findViewById(R.id.txtdoctornamedetail);
        txtdoctornamedetail.setText(staff.getName());
        txtdepartmentnamedetail = findViewById(R.id.txtdepartmentnamedetail);
        txtdepartmentnamedetail.setText(staff.getDepartment().getName());
        btnmakeappointment = findViewById(R.id.btnmakeappointment);
        DataService ds = new DataService(this);
        ds.GetPatients(user.getId(), new DataService.GetPatientsListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(),"Error Occur",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<Patient> patients) {
                patientList = patients;
            }
        });
        btnmakeappointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CreateDialog(ds).show();
            }
        });
        feedbacklistview = findViewById(R.id.feedbacklistview);

    }
    public Dialog CreateDialog(DataService ds){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose Patient")
                .setAdapter(new PatientAdapter(this, patientList), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        patient = patientList.get(which);
                        Intent intent = new Intent(DoctorDetail.this,DoctorScheduleActivity.class);
                        intent.putExtra("user",user);
                        intent.putExtra("staffid",staff.getId());
                        intent.putExtra("patient",patient);
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