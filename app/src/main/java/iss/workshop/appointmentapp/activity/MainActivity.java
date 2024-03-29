package iss.workshop.appointmentapp.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

import iss.workshop.appointmentapp.R;
import iss.workshop.appointmentapp.dataservice.DataService;
import iss.workshop.appointmentapp.model.User;

public class MainActivity extends AppCompatActivity {

    EditText txtname;
    EditText txtpassword;

    TextView txtregisteruser;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txtname = findViewById(R.id.txtname);
        txtpassword = findViewById(R.id.txtpassword);
        btnLogin = findViewById(R.id.btnlogin);
        txtregisteruser = findViewById(R.id.txtregisteruser);
        DataService ds = new DataService(this);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = txtname.getText().toString();
                String password = txtpassword.getText().toString();
                ds.UserAuthen(name, password, new DataService.UserAuthenListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(MainActivity.this, "Authentication Interrupt", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onResponse(JSONObject user) {
                        if(user.length()!=0) {
                            User user1 = new User();
                            try {
                                JSONObject data = user;
                                user1.setId(data.getInt("id"));
                                user1.setName(data.getString("name"));
                                user1.setPassword(data.getString("password"));
                                Intent intent = new Intent(MainActivity.this,DashBoardActivity.class);
                                intent.putExtra("user", (Serializable) user1);
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Authentication Fail", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
        txtregisteruser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UserRegisterationActivity.class);
                startActivity(intent);
            }
        });
    }
}