package iss.workshop.appointmentapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.model.Appointment;

public class MedicalRecordAdapter extends ArrayAdapter<Object> {
    private final Context context;
    List<Appointment> appointments = new ArrayList<>();
    public MedicalRecordAdapter(Context context,List<Appointment> appointments) {
        super(context, R.layout.medicalrecord);
        this.context = context;
        this.appointments = appointments;
        addAll(new Object[appointments.size()]);
    }

    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.medicalrecord,parent,false);
        }
        Appointment appointment = appointments.get(position);
        TextView txtdoctorrecord = view.findViewById(R.id.txtdoctorrecord);
        txtdoctorrecord.setText(appointment.getStaff().getName());
        TextView txtdaterecord = view.findViewById(R.id.txtdaterecord);
        txtdaterecord.setText(appointment.getDate().toString());
        TextView txtrecord = view.findViewById(R.id.txtrecord);
        txtrecord.setText(appointment.getMedicalCondition());
        return view;
    }
}
