package iss.workshop.appointmentapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.activity.PredictDoctorList;
import iss.workshop.appointmentapp.model.Staff;

public class DoctorAdapter extends ArrayAdapter<Object> {
    private final Context context;
    private List<Staff> staffs;
    private int departmentid = 0;
    public DoctorAdapter(Context context, List<Staff> staffs) {
        super(context, R.layout.patient);
        this.context = context;
        this.staffs = staffs;
        addAll(new Object[staffs.size()]);

    }

    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.doctor,parent,false);
        }
        TextView txtdoctornamelist = view.findViewById(R.id.txtdoctornamelist);
        txtdoctornamelist.setText(staffs.get(position).getName());
        TextView txtdepartmentdoctor = view.findViewById(R.id.txtdepartmentdoctor);
        txtdepartmentdoctor.setText(staffs.get(position).getDepartment().getName());
        if (staffs.get(position).getDepartment().getId()==departmentid){
            TextView txtrecommend = view.findViewById(R.id.txtrecommend);
            txtrecommend.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public void setDepartmentid(int departmentid){
        this.departmentid = departmentid;

    }
}
