package iss.workshop.appointmentapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class PatientsFragment extends Fragment implements PatientAdapter.OnButtonClickListener {

    ListView patientListView;
    private int UserID;
    List<Patient> patients = new ArrayList<>();
    public void setUserID(int id){
        UserID = id;
    }

    @Override
    public void onEditClickListener(int PatientID) {
        Intent intent = new Intent((Context) iPatientFragment,MainActivity.class);
        intent.putExtra("patientID",PatientID);
        startActivity(intent);
    }

    @Override
    public void onDeleteClickListener(int PatientID) {
        Intent intent = new Intent((Context) iPatientFragment,MainActivity.class);
        intent.putExtra("patientID",PatientID);
        startActivity(intent);
    }

    public interface IPatientFragment{
    }
    private IPatientFragment iPatientFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iPatientFragment = (IPatientFragment) context;
    }

    public PatientsFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_patients, container, false);
        patientListView = view.findViewById(R.id.patientListView);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null){
            UserID = bundle.getInt("userid");
        }
        DataService ds = new DataService((Context) iPatientFragment);
        ds.GetPatients(UserID, new DataService.GetPatientsListener() {
            @Override
            public void onError(String message) {
                Toast.makeText((Context) iPatientFragment,"Error Occur",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onResponse(List<Patient> patients) {
                PatientAdapter adapter = new PatientAdapter((Context) iPatientFragment,patients);
                adapter.setOnButtonClickListener(PatientsFragment.this);
                if(patientListView!=null){
                    patientListView.setAdapter(adapter);
                }
            }
        });
    }
}