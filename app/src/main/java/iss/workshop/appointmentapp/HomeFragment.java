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
import android.widget.ListView;
import android.widget.TextView;


public class HomeFragment extends Fragment implements View.OnClickListener {

    private User user;

    TextView txthello;
    Button btndepartment;
    Button btnappointment;
    Button btnmap;
    ListView ListAppointments;
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
    }
}