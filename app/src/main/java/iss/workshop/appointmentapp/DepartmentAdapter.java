package iss.workshop.appointmentapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DepartmentAdapter extends ArrayAdapter<Object> {

    private final Context context;
    List<Department> departments = new ArrayList<>();
    public DepartmentAdapter(Context context,List<Department> departments) {
        super(context, R.layout.department);
        this.context = context;
        this.departments = departments;
        addAll(new Object[departments.size()]);
    }

    @NonNull
    public View getView(int position,View view, @NonNull ViewGroup parent) {
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.department,parent,false);
        }
        TextView txtdepartmentnamelist = view.findViewById(R.id.txtdepartmentnamelist);
        txtdepartmentnamelist.setText(departments.get(position).getName());
        return view;
    }
}
