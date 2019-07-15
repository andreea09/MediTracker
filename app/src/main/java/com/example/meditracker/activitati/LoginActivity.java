package com.example.meditracker.activitati;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditracker.R;
import com.example.meditracker.clase.persoane.Administrator;
import com.example.meditracker.clase.Constante;
import com.example.meditracker.clase.persoane.Pacient;
import com.example.meditracker.clase.persoane.Angajat;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    Button btnLogare;
    TextView tvEmail, tvParola, tvUitatParola;
    CheckBox cbRetine;
    static String email = "";
    static String parola = "";

    @Override
    protected void onResume() {
        super.onResume();
        tvEmail.setText(email);
        tvParola.setText(parola);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogare = findViewById(R.id.btn_logare);
        tvEmail = findViewById(R.id.et_email);
        tvParola = findViewById(R.id.et_parola);
        tvUitatParola = findViewById(R.id.tv_uitat_parola);
        cbRetine = findViewById(R.id.cb_retine);

        tvEmail.setText(email);
        tvParola.setText(parola);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constante.SIMPLE_DATE_FORMAT);

        tvUitatParola.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ParolaUitataActivity.class);
                startActivity(intent);
            }
        });

        btnLogare.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
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

                if (cbRetine.isChecked())
                {
                    email = tvEmail.getText().toString();
                    parola = tvParola.getText().toString();
                }
                else {
                    email = "";
                    parola = "";
                }

                try {

                    JSONObject jsonObject = new JSONObject(s);
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        String tip = jsonObject.getString("tip");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject persoana = jsonArray.getJSONObject(i);

                            System.out.println(tip);

                            if(tip.equals("administrator")) {
                                Toast.makeText(getApplicationContext(), "Bine ai venit, " + persoana.getString("utilizator") + " !", Toast.LENGTH_LONG).show();
                            }
                                else Toast.makeText(getApplicationContext(), "Bine ai venit, "+ persoana.getString("nume") + " " +persoana.getString("prenume") + " !", Toast.LENGTH_LONG).show();

                            Intent intent = null;

                            switch (tip) {
                                case "angajat":
                                    Angajat angajat = new Angajat(persoana.getString("nume"), persoana.getString("prenume"),
                                            simpleDateFormat.parse(persoana.getString("data_nastere")),persoana.getInt("sex"), persoana.getString("adresa"), persoana.getString("telefon"),
                                            persoana.getString("email"), persoana.getString("CNP"), persoana.getInt("angajatID"), persoana.getString("sectie"),
                                            simpleDateFormat.parse(persoana.getString("data_angajare")),0, null, persoana.getString("pozitie"),persoana.getString("parola"), null );

                                    switch (persoana.getString("pozitie").toLowerCase()) {
                                        case "medic":
                                            intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.putExtra("medic", angajat);
                                            break;
                                        case "asistent":
                                            intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.putExtra("asistent", angajat);
                                            break;
                                        case "upu":
                                            intent = new Intent(getApplicationContext(), MainActivityUPU.class);
                                            intent.putExtra("upu", angajat);
                                            break;
                                        default:
                                            break;
                                    }
                                    break;
                                case "administrator":
                                    intent = new Intent(getApplicationContext(), MainActivityAdmin.class);
                                    Administrator admin = new Administrator();
                                    admin.setEmail(persoana.getString("email"));
                                    admin.setParola(persoana.getString("parola"));
                                    admin.setUtilizator(persoana.getString("utilizator"));
                                    intent.putExtra("admin", admin);
                                    break;
                                case "pacient":
                                    intent = new Intent(getApplicationContext(), MainActivityPacient.class);
                                    Pacient pacient = new Pacient();
                                    pacient.setNume(persoana.getString("nume"));
                                    pacient.setPrenume(persoana.getString("prenume"));
                                    pacient.setAdresa(persoana.getString("adresa"));
                                    pacient.setData_nastere(simpleDateFormat.parse(persoana.getString("data_nastere")));
                                    pacient.setCNP(persoana.getString("CNP"));
                                    pacient.setPacientID(persoana.getInt("pacientID"));
                                    pacient.setEmail(persoana.getString("email"));
                                    pacient.setParola(persoana.getString("parola"));
                                    intent.putExtra("pacient", pacient);
                                    break;
                                default:
                                    break;
                            }
                            startActivity(intent);
                        }}
                    catch (JSONException e){
                        Toast.makeText(getApplicationContext(), "E-mail sau parola gresite!", Toast.LENGTH_LONG).show();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }


        });
    }

    public class RequestAsync extends AsyncTask<String, String, String> {
        @SuppressLint("WrongThread")
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("email", tvEmail.getText().toString().trim());
                postDataParams.put("parola", tvParola.getText().toString().trim());

                System.out.println("GOT HERE!");

                String result = CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/login_angajat", postDataParams);

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
    public void onBackPressed() {

    }
}
