package iss.workshop.appointmentapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.model.User;


public class AccountFragment extends Fragment implements View.OnClickListener {

    private User user;

    TextView txtusername;
    Button btnpatientList;
    Button btnappointmenthistory;
    Button btnlogout;

    public void setUser(User user){
        this.user = user;
    }
    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnpatientlist){
            Intent intent = new Intent((Context) iAccountFragment,PatientsList.class);
            intent.putExtra("user",user);
            startActivity(intent);
        } else if (id == R.id.btnappointmenthistory) {
            Intent intent = new Intent((Context) iAccountFragment,AppointmentHistory.class);
            intent.putExtra("user",user);
            startActivity(intent);
        }
    }

    public interface IAccountFragment{
    }
    private IAccountFragment iAccountFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        iAccountFragment = (IAccountFragment) context;
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
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        txtusername = view.findViewById(R.id.txtusername);
        btnpatientList = view.findViewById(R.id.btnpatientlist);
        btnappointmenthistory = view.findViewById(R.id.btnappointmenthistory);
        btnlogout = view.findViewById(R.id.btnlogout);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        Bundle bundle = getArguments();
        if (bundle != null){
            user = (User) bundle.getSerializable("user");
        }
        txtusername.setText(user.getName());
        btnappointmenthistory.setOnClickListener(this);
        btnlogout.setOnClickListener(this);
        btnpatientList.setOnClickListener(this);
    }
}