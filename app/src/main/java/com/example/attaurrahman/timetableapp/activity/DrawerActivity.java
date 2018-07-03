package com.example.attaurrahman.timetableapp.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.attaurrahman.timetableapp.R;
import com.example.attaurrahman.timetableapp.fragment.LoginFragment;
import com.example.attaurrahman.timetableapp.fragment.MainFragment;
import com.example.attaurrahman.timetableapp.fragment.ProfileFragment;
import com.example.attaurrahman.timetableapp.fragment.RescheduleFragment;
import com.example.attaurrahman.timetableapp.fragment.SubjectFragment;
import com.example.attaurrahman.timetableapp.uitils.Utilities;

public class DrawerActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        Utilities.withOutBackStackConnectFragment(this, new MainFragment());
      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        TextView tvHeaderName = headerView.findViewById(R.id.tv_nav_header_drawer_name);
        TextView tvHeaderEmail = headerView.findViewById(R.id.tv_header_drawer_email);
        tvHeaderName.setText(Utilities.getSharedPreferences(this).getString("name",""));
        tvHeaderEmail.setText(Utilities.getSharedPreferences(this).getString("email",""));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
       /* if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_profile) {
            Utilities.withOutBackStackConnectFragment(this, new ProfileFragment());

        } else if (id == R.id.nav_timetable) {
            Utilities.withOutBackStackConnectFragment(this, new MainFragment());

        } else if (id == R.id.nav_subject) {
            Utilities.withOutBackStackConnectFragment(this, new SubjectFragment());

        } else if (id == R.id.nav_reschedule) {
            Utilities.withOutBackStackConnectFragment(this, new RescheduleFragment());

        } else if (id == R.id.nav_log_out) {
            Utilities.putValueInEditor(this).putBoolean("isLogin", false).commit();
            startActivity(new Intent(DrawerActivity.this, MainActivity.class));
            finish();

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
