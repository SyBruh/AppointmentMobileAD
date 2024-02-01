package iss.workshop.appointmentapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class HomeFragment extends Fragment {

    private int UserID;
    Button btnaddpatient;
    Button btnlogout;
    public void setUserID(int id){
        UserID = id;
    }

    public HomeFragment() {
        // Required empty public constructor
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
        btnaddpatient = view.findViewById(R.id.btnaddpatient);
        btnlogout = view.findViewById(R.id.btnlogout);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null){
            UserID = bundle.getInt("userid");
        }
        btnaddpatient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent((Context) iHomeFragment,AddPatientActivity.class);
                intent.putExtra("userid",UserID);
                startActivity(intent);
            }
        });
    }
}