package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.User;

public class UserRegisterationActivity extends AppCompatActivity {

    EditText txtusernameregister;
    EditText txtpasswordregister;
    Button btnregister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_registeration);
        txtpasswordregister = findViewById(R.id.txtpasswordregister);
        txtusernameregister = findViewById(R.id.txtusernameregister);
        btnregister = findViewById(R.id.btnregister);
        DataService ds = new DataService(this);
        btnregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = new User();
                user.setName(txtusernameregister.getText().toString());
                user.setPassword(txtpasswordregister.getText().toString());
                ds.AddUser(user, new DataService.AddUserListener() {
                    @Override
                    public void onError(String message) {

                    }

                    @Override
                    public void onResponse(User user) {
                        Intent intent = new Intent(UserRegisterationActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}