package iss.workshop.appointmentapp.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.adapter.AppointmentAdapter;
import iss.workshop.appointmentapp.adapter.PatientAdapter;
import iss.workshop.appointmentapp.adapter.StringAdapter;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.Appointment;
import iss.workshop.appointmentapp.model.AppointmentStatusEnum;
import iss.workshop.appointmentapp.model.User;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private User user;

    TextView txthello;
    Button btndepartment;
    Button btnappointment;
    Button btnmap;
    ListView ListPendingAppointments;

    DataService ds;
    List<String> SymptomsGroup = new ArrayList<>();
    List<Appointment> appointmentlist = new ArrayList<>();
    public void setUser(User user){
        this.user = user;
    }

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.btndepartment){
            Intent intent = new Intent((Context) iHomeFragment,DepartmentList.class);
            intent.putExtra("user",user);
            startActivity(intent);
        } else if (id == R.id.btnappointment) {
            CreateDialog(ds).show();
        }else  if (id == R.id.btnmap){
            Intent intent = new Intent((Context) iHomeFragment,MapActivity.class);
            startActivity(intent);
        }
    }

    public interface IHomeFragment{

    }

    private IHomeFragment iHomeFragment;

    public void setiHomeFragment(IHomeFragment iHomeFragment){
        this.iHomeFragment = iHomeFragment;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iHomeFragment = (IHomeFragment) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        txthello = view.findViewById(R.id.txtHello);
        btndepartment = view.findViewById(R.id.btndepartment);
        btnappointment = view.findViewById(R.id.btnappointment);
        btnmap = view.findViewById(R.id.btnmap);
        ListPendingAppointments = view.findViewById(R.id.ListPendingAppointments);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null){
            user = (User) bundle.getSerializable("user");
        }
        txthello.setText("Hello " + user.getName());
        btndepartment.setOnClickListener(this);
        btnappointment.setOnClickListener(this);
        btnmap.setOnClickListener(this);
        ds = new DataService((Context) iHomeFragment);
        ds.GetAppointments(user.getId(), new DataService.GetAppointmentsListener() {
            @Override
            public void onError(String message) {
                Toast.makeText((Context) iHomeFragment,message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<Appointment> appointments) {
                for (Appointment appointment:appointments){
                    if (appointment.getStatus().equals(AppointmentStatusEnum.Proceeding)){
                        appointmentlist.add(appointment);
                    }
                }
                AppointmentAdapter adapter = new AppointmentAdapter((Context) iHomeFragment,appointmentlist);
                if(ListPendingAppointments!=null){
                    ListPendingAppointments.setAdapter(adapter);
                }
            }
        });
        ListPendingAppointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent((Context) iHomeFragment,AppointmentDetailActivity.class);
                intent.putExtra("user",user);
                intent.putExtra("appointment",appointmentlist.get(position));
                startActivity(intent);
            }
        });
        ds.GetSymptomsGroup(new DataService.GetSymptomsGroupListener() {
            @Override
            public void onError(String message) {
                Toast.makeText((Context) iHomeFragment,message,Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<String> symptomGroups) {
                SymptomsGroup = symptomGroups;
            }
        });
    }
    public Dialog CreateDialog(DataService ds){

        AlertDialog.Builder builder = new AlertDialog.Builder((Context) iHomeFragment);
        builder.setTitle("Choose Symptoms Group")
                .setAdapter(new StringAdapter((Context) iHomeFragment,R.layout.stringitem,SymptomsGroup), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent((Context) iHomeFragment, SymptomListActivity.class);
                        intent.putExtra("user",user);
                        intent.putExtra("symptom-group",SymptomsGroup.get(which));
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