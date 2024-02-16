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
import iss.workshop.appointmentapp.model.Feedback;

public class FeedbackAdapter extends ArrayAdapter<Object> {
    private final Context context;
    private List<Feedback> feedbacks;
    public FeedbackAdapter(Context context, List<Feedback> feedbacks) {
        super(context, R.layout.comment);
        this.context = context;
        this.feedbacks = feedbacks;
        addAll(new Object[feedbacks.size()]);
    }

    @NonNull
    public View getView(int position, View view, @NonNull ViewGroup parent) {
        if (view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.comment,parent,false);
        }
        TextView txtusername = view.findViewById(R.id.txtpatientnamelistitem);
        txtusername.setText(feedbacks.get(position).getUser().getName() + " : ");
        TextView txtcomment = view.findViewById(R.id.txtcomment);
        txtcomment.setText(feedbacks.get(position).getDescription());
//        TextView txtlistcount = view.findViewById(R.id.txtlistcount);
//        txtlistcount.setText(String.valueOf(position+1));
        return view;
    }
}
