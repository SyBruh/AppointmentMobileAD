package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.adapter.AppointmentAdapter;
import iss.workshop.appointmentapp.adapter.PatientAdapter;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Appointment;
import iss.workshop.appointmentapp.model.User;

public class AppointmentHistory extends AppCompatActivity {

    private User user;
    ListView appointmenthistorylistview;
    List<Appointment> appointmentlist = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_history);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        appointmenthistorylistview = findViewById(R.id.appointmenthistorylistview);
        DataService ds = new DataService(this);
        ds.GetAppointments(user.getId(), new DataService.GetAppointmentsListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(),message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<Appointment> appointments) {
                AppointmentAdapter adapter = new AppointmentAdapter(getBaseContext(),appointments);
                appointmentlist = appointments;
                if(appointmenthistorylistview!=null){
                    appointmenthistorylistview.setAdapter(adapter);
                }
            }
        });
        appointmenthistorylistview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(AppointmentHistory.this,AppointmentDetailActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("appointment",appointmentlist.get(position));
                startActivity(intent);
            }
        });
    }
}