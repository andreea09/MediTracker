package com.example.meditracker.activitati;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.meditracker.R;
import com.example.meditracker.clase.persoane.Administrator;
import com.example.meditracker.db_connectors.CallAPI;
import com.google.android.material.navigation.NavigationView;

import org.json.JSONObject;

public class MainActivityAdmin extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView tvEmail, tvNume;
    Administrator administrator = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        tvEmail = headerView.findViewById(R.id.tv_email_utilizator);
        tvNume = headerView.findViewById(R.id.tv_nume_utilizator);

        administrator = (Administrator) getIntent().getExtras().getSerializable("admin");


        tvEmail.setText(administrator.getEmail().trim());
        tvNume.setText(administrator.getUtilizator().trim());

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();


        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_angajat) {
            Intent intent = new Intent(getApplicationContext(), AdaugareAngajatActivity.class);
            startActivity(intent);
        }  else if (id == R.id.nav_profil) {
            Intent intent = new Intent(getApplicationContext(), ProfilActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_feedback) {
            Intent intent = new Intent(getApplicationContext(), FeedbackActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
