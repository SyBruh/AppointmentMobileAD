package iss.workshop.appointmentapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import iss.workshop.appointmentapp.R;

public class StringAdapter extends ArrayAdapter<Object> {
    private Context context;
    private List<String> strings;
    private int layoutid;
    private List<Boolean> check = new ArrayList<>();
    public StringAdapter(@NonNull Context context,int layoutid, List<String> strings) {
        super(context, layoutid);
        this.layoutid = layoutid;
        this.context=context;
        this.strings=strings;
        for (String s:strings){
            check.add(false);
        }
        addAll(new Object[strings.size()]);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(layoutid,parent,false);
        }
        if (layoutid == R.layout.stringitem){
            TextView txtstring = view.findViewById(R.id.txtstring);
            txtstring.setText(strings.get(position));
        }
        else if(layoutid == R.layout.symptomitem){
            CheckBox checksymptom = view.findViewById(R.id.checkedsymptom);
            checksymptom.setText(strings.get(position));
            checksymptom.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    check.set(position,isChecked);

                }
            });
        }
        return view;
    }
    public List<String> getchecksymptoms()
    {
        List<String> checkedsymptoms = new ArrayList<>();
        for (int i =0;i<strings.size();i++){
            if (check.get(i)){
                checkedsymptoms.add(strings.get(i));
            }
        }
        return checkedsymptoms;
    }
}
