package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.text.DecimalFormat;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.adapter.FeedbackAdapter;
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
    List<String> descriptions = new ArrayList<>();
    Patient patient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);
        Intent intent = getIntent();
        user = (User) intent.getSerializableExtra("user");
        staff = (Staff) intent.getSerializableExtra("staff");
        patient = (Patient) intent.getSerializableExtra("patient");
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
                if (patient!=null){
                    Intent intent = new Intent(DoctorDetail.this,DoctorScheduleActivity.class);
                    intent.putExtra("user",user);
                    intent.putExtra("staffid",staff.getId());
                    intent.putExtra("patient",patient);
                    startActivity(intent);
                }else{
                    CreateDialog(ds).show();
                }
            }
        });
        feedbacklistview = findViewById(R.id.feedbacklistview);
        ds.GetFeedbacks(staff.getId(), new DataService.GetFeedbacksListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(getBaseContext(),"Error Occur",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<Feedback> feedbacks) {
                feedbackList = feedbacks;
                FeedbackAdapter adapter = new FeedbackAdapter(DoctorDetail.this,feedbackList);
                feedbacklistview.setAdapter(adapter);
                for (Feedback f:feedbackList){
                    descriptions.add(f.getDescription());
                }
                ds.GetKeywords(descriptions, new DataService.KeywordsListener() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(List<String> keywords) {
                        while (keywords.size()<10){
                            keywords.add("null");
                        }
                        setkeywords(keywords);
                    }
                });
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
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
    public double calculateAverageFeedbackScore() {
        OptionalDouble average = feedbackList.stream().mapToDouble(Feedback::getScore).average();
        double avg = average.isPresent() ? average.getAsDouble() : 0.0;
        DecimalFormat df = new DecimalFormat("#.#");
        return Double.parseDouble(df.format(avg));
    }

    public void setkeywords(List<String> keywords){
        TextView txt1 = findViewById(R.id.txt1);
        TextView txt2 = findViewById(R.id.txt2);
        TextView txt3 = findViewById(R.id.txt3);
        TextView txt4 = findViewById(R.id.txt4);
        TextView txt5 = findViewById(R.id.txt5);
        TextView txt6 = findViewById(R.id.txt6);
        TextView txt7 = findViewById(R.id.txt7);
        TextView txt8 = findViewById(R.id.txt8);
        TextView txt9 = findViewById(R.id.txt9);
        TextView txt10 = findViewById(R.id.txt10);
        txt1.setText(keywords.get(0));
        txt2.setText(keywords.get(1));
        txt3.setText(keywords.get(2));
        txt4.setText(keywords.get(3));
        txt5.setText(keywords.get(4));
        txt6.setText(keywords.get(5));
        txt7.setText(keywords.get(6));
        txt8.setText(keywords.get(7));
        txt9.setText(keywords.get(8));
        txt10.setText(keywords.get(9));
    }
}