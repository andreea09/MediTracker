package com.example.meditracker.activitati;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.meditracker.R;
import com.example.meditracker.clase.Constante;
import com.example.meditracker.clase.PacientAdapter;
import com.example.meditracker.clase.persoane.Angajat;

import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;
import android.view.MenuItem;

import com.example.meditracker.clase.persoane.Pacient;
import com.example.meditracker.clase.tratare.Diagnostic;
import com.example.meditracker.db_connectors.CallAPI;
import com.google.android.material.navigation.NavigationView;
import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    TextView tvEmail, tvNume;
    public static Angajat angajat=null;
    RecyclerView recyclerView;
    PacientAdapter pacientAdapter;
    List<Pacient> pacienti;
    SimpleDateFormat format = new SimpleDateFormat(Constante.SIMPLE_DATE_FORMAT);
    List<Integer> listaID = new ArrayList<>();
    int ID;
    List<String> descriereDiagnostice = new ArrayList<>();
    List<Integer> diagnosticeID = new ArrayList<>();

    @Override
    protected void onResume() {
        super.onResume();
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);

        tvEmail = headerView.findViewById(R.id.tv_email_utilizator);
        tvNume = headerView.findViewById(R.id.tv_nume_utilizator);


        if (getIntent().getSerializableExtra("medic")!=null) {
            angajat = (Angajat) getIntent().getSerializableExtra("medic");
        }
        else if (getIntent().getSerializableExtra("asistent")!=null) {
            angajat = (Angajat) getIntent().getSerializableExtra("asistent");
        }

        pacienti = new ArrayList<>();
        System.out.println("Lista este" + pacienti.toString());

        tvEmail.setText(angajat.getEmail().trim());
        tvNume.setText(angajat.getNume().trim() + " " + angajat.getPrenume().trim());

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);

        String s = null;
        RequestAsync req = new RequestAsync();

        try {
            s = req.execute().get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(s);

        try {

            JSONObject jsonObject = new JSONObject(s);

            try {
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                for(int i=0; i < jsonArray.length();i++) {
                    JSONObject diagnostic = jsonArray.getJSONObject(i);

                    boolean gasit = false;

                    for (int k = 0; k < listaID.size(); k++) {
                        if (listaID.get(k) == diagnostic.getInt("pacientID")) {
                            descriereDiagnostice.set(k, diagnostic.getString("descriere"));
                            diagnosticeID.set(k, diagnostic.getInt("diagnosticID"));
                            gasit = true;
                        }
                    }
                    if (!gasit) {
                        listaID.add(diagnostic.getInt("pacientID"));
                        descriereDiagnostice.add(diagnostic.getString("descriere"));
                        diagnosticeID.add(diagnostic.getInt("diagnosticID"));
                    }
                }

                int numar=0;

                System.out.println("Lista ids" + listaID.toString());

                for (Integer id : listaID) {
                    ID = id;

                    String s1 = null;
                    PacietAsync req1 = new PacietAsync();

                    try {
                        s1 = req1.execute().get();
                    } catch (ExecutionException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println(s1);

                    try {

                        JSONObject jsonObject1 = new JSONObject(s1);

                        try {
                            JSONArray jsonArray1 = jsonObject1.getJSONArray("result");

                            for (int j = 0; j < jsonArray1.length(); j++) {
                                JSONObject persoana = jsonArray1.getJSONObject(j);

                                JSONObject asigurat = persoana.getJSONObject("asigurat");
                                JSONObject internat = persoana.getJSONObject("internat");

                                Pacient p = new Pacient(persoana.getString("nume"), persoana.getString("prenume"), format.parse(persoana.getString("data_nastere")),
                                        persoana.getInt("sex"), persoana.getString("adresa"), persoana.getString("telefon"), persoana.getString("email"),
                                        persoana.getString("CNP"), null, String.valueOf(persoana.getInt("dizabilitati")), asigurat.getString("data").equals("[1]") ? true : false, Float.valueOf(persoana.getInt("costuri_existente")),
                                        new Diagnostic(descriereDiagnostice.get(numar)), null, internat.getString("data").equals("[1]") ? true : false, persoana.getInt("pacientID"), persoana.getString("parola"), null, 0);
                                pacienti.add(p);
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    } catch (ParseException e1) {
                        e1.printStackTrace();
                    }
                    numar++;
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        recyclerView = findViewById(R.id.recycler_pacienti_main);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


        pacientAdapter = new PacientAdapter(pacienti, MainActivity.this);
        pacientAdapter.setOnItemClickListener(new PacientAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getApplicationContext(), ProfilPacientActivity.class);
                intent.putExtra("cnp", pacienti.get(position).getCNP());
                intent.putExtra("diagnostic", descriereDiagnostice.get(position));
                intent.putExtra("diagnosticID", diagnosticeID.get(position));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(pacientAdapter);



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
            Intent intent = new Intent(getApplicationContext(), DespreActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profil) {
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

    public class RequestAsync extends AsyncTask<String, String, String> {
        @SuppressLint("WrongThread")
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("angajatID", angajat.getAngajatID());

                System.out.println("GOT HERE!");

                String result = CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/angajat/diagnostic", postDataParams);

                System.out.println("I have received a result!");
                System.out.println(result);

                return result;

            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


    }

    public class PacietAsync extends AsyncTask<String, String, String> {
        @SuppressLint("WrongThread")
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("pacientID", ID);

                System.out.println("GOT HERE!");

                String result = CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/pacient/cautareID", postDataParams);

                System.out.println("I have received a result!");
                System.out.println(result);

                return result;

            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }

        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
}
