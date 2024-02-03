package iss.workshop.appointmentapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class DashBoardActivity extends AppCompatActivity implements NavigationBarView.OnItemSelectedListener, AccountFragment.IAccountFragment, HomeFragment.IHomeFragment {

    User User;
    BottomNavigationView navmain;
    Bundle arguments = new Bundle();
    FragmentManager fm = getSupportFragmentManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);
        Intent intent = getIntent();
        User = (User) intent.getSerializableExtra("user");
        navmain = findViewById(R.id.navmain);
        navmain.setSelectedItemId(R.id.mhome);
        navmain.setOnItemSelectedListener(this);
        arguments.putSerializable("user", User);
        FragmentTransaction trans = fm.beginTransaction();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(arguments);
        trans.replace(R.id.fragcon, fragment).addToBackStack(null).commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        FragmentTransaction trans = fm.beginTransaction();
        if (item.getItemId() == R.id.mhome) {
            HomeFragment fragment = new HomeFragment();
            fragment.setArguments(arguments);
            trans.replace(R.id.fragcon, fragment).addToBackStack(null).commit();
            return true;
        } else if (item.getItemId() == R.id.maccount) {
            AccountFragment fragment = new AccountFragment();
            fragment.setArguments(arguments);
            trans.replace(R.id.fragcon, fragment).addToBackStack(null).commit();
            return true;
        } else {
            return false;
        }
    }
}