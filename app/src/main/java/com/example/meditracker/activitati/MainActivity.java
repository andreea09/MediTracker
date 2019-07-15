package com.example.meditracker.activitati;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.meditracker.R;
import com.example.meditracker.clase.PacientAdapter;
import com.example.meditracker.clase.persoane.Angajat;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.example.meditracker.clase.persoane.Pacient;
import com.example.meditracker.clase.tratare.Diagnostic;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvEmail, tvNume;
    public static Angajat angajat=null;
    RecyclerView recyclerView;
    PacientAdapter pacientAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        tvEmail = headerView.findViewById(R.id.tv_email_utilizator);
        tvNume = headerView.findViewById(R.id.tv_nume_utilizator);

        recyclerView = findViewById(R.id.recycler_pacienti_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        final List<Pacient> pacienti = new ArrayList<>();
        Pacient p = new Pacient();
        p.setNume("Popescu");
        p.setPrenume("Ionel");
        p.setDiagnostic(new Diagnostic());
        p.getDiagnostic().setDiagnostic("Cancer");
        pacienti.add(p);
        Pacient p1 = new Pacient();
        p1.setNume("Ionescu");
        p1.setPrenume("Popel");
        p1.setCNP("2000925123456");
        p1.setDiagnostic(new Diagnostic());
        p1.getDiagnostic().setDiagnostic("Alzheimer");
        pacienti.add(p1);


        pacientAdapter = new PacientAdapter(pacienti, MainActivity.this);
        pacientAdapter.setOnItemClickListener(new PacientAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilPacientActivity.class);
                intent.putExtra("cnp", pacienti.get(position).getCNP());
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(pacientAdapter);



        if (getIntent().getSerializableExtra("medic")!=null) {
            angajat = (Angajat) getIntent().getSerializableExtra("medic");
        }
        else if (getIntent().getSerializableExtra("asistent")!=null) {
            angajat = (Angajat) getIntent().getSerializableExtra("asistent");
        }

        tvEmail.setText(angajat.getEmail().trim());
        tvNume.setText(angajat.getNume().trim() + " " + angajat.getPrenume().trim());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

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
        // Inflate the menu; this adds items to the action bar if it is present.
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

        if (id == R.id.nav_pacienti) {
            Intent intent = new Intent(getApplicationContext(), PacientiActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_sectie) {
            Intent intent = new Intent(getApplicationContext(), StatisticiSectieActivity.class);
            startActivity(intent);


        } else if (id == R.id.nav_profil) {
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
