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
import iss.workshop.appointmentapp.model.Schedule;
import iss.workshop.appointmentapp.model.Staff;

public class ScheduleAdapter extends ArrayAdapter<Object> {
    private final Context context;
    private List<Schedule> schedules;
    public ScheduleAdapter(Context context, List<Schedule> schedules) {
        super(context, R.layout.scheduleitem);
        this.context = context;
        this.schedules = schedules;
        addAll(new Object[schedules.size()]);
    }

    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.scheduleitem,parent,false);
        }
        Schedule schedule = schedules.get(position);
        TextView txtscheduledate = view.findViewById(R.id.txtscheduledate);
        txtscheduledate.setText(schedule.getDate().toString());
        TextView txttimeschedule = view.findViewById(R.id.txttimeschedule);
        txttimeschedule.setText(schedule.getTime_start().toString() + " to " + schedule.getTime_end().toString());
        return view;
    }
}
