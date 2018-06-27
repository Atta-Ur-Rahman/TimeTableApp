package com.example.attaurrahman.timetableapp.activity;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.attaurrahman.timetableapp.R;
import com.example.attaurrahman.timetableapp.fragment.LoginFragment;
import com.example.attaurrahman.timetableapp.fragment.MainFragment;
import com.example.attaurrahman.timetableapp.uitils.Utilities;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        Handler handler = new Handler();
        Runnable r = new Runnable() {
            public void run() {
                boolean isLogin;
                isLogin = Utilities.getSharedPreferences(MainActivity.this).getBoolean("isLogin", false);
                if (isLogin) {
                    startActivity(new Intent(MainActivity.this, DrawerActivity.class));
                    finish();

                } else {
                    Utilities.withOutBackStackConnectFragment(MainActivity.this, new LoginFragment());
                }


                getSupportActionBar().show();
            }
        };
        handler.postDelayed(r, 1000);
    }
}
