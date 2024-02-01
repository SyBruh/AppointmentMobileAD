package iss.workshop.appointmentapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class PatientAdapter extends ArrayAdapter<Object> {
    private final Context context;
    private List<Patient> patients;
    public PatientAdapter(Context context, List<Patient> patients) {
        super(context, R.layout.patient_profile);
        this.context = context;
        this.patients = patients;
        addAll(new Object[patients.size()]);
    }

    public interface OnButtonClickListener{
        void onEditClickListener(int PatientID);
        void onDeleteClickListener(int PatientID);

    }
    private OnButtonClickListener onButtonClickListener;
    public void setOnButtonClickListener(OnButtonClickListener onButtonClickListener){
        this.onButtonClickListener = onButtonClickListener;
    }

    @NonNull
    public View getView(int position,View view, @NonNull ViewGroup parent) {
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.patient_profile,parent,false);
        }
        Patient patient = patients.get(position);
        TextView txtpatientname = view.findViewById(R.id.txtpatientname);
        txtpatientname.setText(patient.getName());
        TextView txtpatientaddress = view.findViewById(R.id.txtpatientaddress);
        txtpatientaddress.setText(patient.getAddress());
        TextView txtpatientcond = view.findViewById(R.id.txtpatientcond);
        txtpatientcond.setText(patient.getMedical_condition());
        TextView txtpatientsex = view.findViewById(R.id.txtpatientsex);
        txtpatientsex.setText(patient.getSex());
        TextView txtpatientallergy = view.findViewById(R.id.txtpatientallergy);
        txtpatientallergy.setText(patient.getAllergy());
        TextView txtadditionalinfo = view.findViewById(R.id.txtadditionalinfo);
        txtadditionalinfo.setText(patient.getAdditional_information());
        Button btnpatientEdit = view.findViewById(R.id.btnpatientedit);
        btnpatientEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onButtonClickListener!=null){
                    onButtonClickListener.onEditClickListener(patient.getId());
                }

            }
        });
        Button btnpatientDelete = view.findViewById(R.id.btnpatientdelete);
        btnpatientDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onButtonClickListener!= null){
                    onButtonClickListener.onDeleteClickListener(patient.getId());
                }
            }
        });


        return view;
    }


}
