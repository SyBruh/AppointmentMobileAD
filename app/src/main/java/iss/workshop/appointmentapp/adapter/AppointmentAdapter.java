package iss.workshop.appointmentapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.model.Appointment;
import iss.workshop.appointmentapp.model.Department;

public class AppointmentAdapter extends ArrayAdapter<Object> {
    private final Context context;
    List<Appointment> appointments = new ArrayList<>();
    public AppointmentAdapter(Context context,List<Appointment> appointments) {
        super(context, R.layout.appointmentitem);
        this.context = context;
        this.appointments = appointments;
        addAll(new Object[appointments.size()]);
    }

    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.appointmentitem,parent,false);
        }
        Appointment appointment = appointments.get(position);
        TextView txtappointmentdoctor = view.findViewById(R.id.txtappointmentdoctor);
        txtappointmentdoctor.setText(appointment.getStaff().getName());
        TextView txtscheduletime = view.findViewById(R.id.txtscheduletime);
        txtscheduletime.setText(appointment.getDate().toString() + appointment.getTime().toString());
        TextView txtqueuenum = view.findViewById(R.id.txtqueuenum);
        txtqueuenum.setText(String.valueOf(appointment.getQueue_number()));
        TextView txtstatusqqppointment = view.findViewById(R.id.txtstatusappointment);
        txtstatusqqppointment.setText(appointment.getStatus().toString());
        return view;
    }
}
