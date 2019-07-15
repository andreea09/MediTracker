package com.example.meditracker.activitati;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditracker.R;
import com.example.meditracker.clase.Constante;
import com.example.meditracker.clase.persoane.Angajat;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class AdaugareDiagnosticActivity extends AppCompatActivity {
    TextView tvDescriere, tvIndicatii, tvDurata, tvIncepereTratament, tvDataInternare, tvZileInternare;
    Button btnAdaugare;
    Spinner spnSectie, spnMedic;
    String CNP = null;
    int pacientID, medicID;
    String sectie;
    ArrayList<Angajat> medici = null;
    ArrayList<String> numeMedici = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_diagnostic);

        tvDescriere = findViewById(R.id.et_descriere_diagnostic);
        tvIndicatii = findViewById(R.id.et_indicatii_tratament);
        tvDurata = findViewById(R.id.et_durata_tratament);
        tvIncepereTratament = findViewById(R.id.et_data_tratament);
        tvDataInternare = findViewById(R.id.et_data_internare);
        tvZileInternare = findViewById(R.id.et_numar_internare);
        btnAdaugare = findViewById(R.id.btn_adauagare_diagnostic);
        spnMedic = findViewById(R.id.spn_medic_diagnostic);
        spnSectie = findViewById(R.id.spn_sectie_diagnostic);

        ArrayAdapter<CharSequence> adapterSectie = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spn_sectie, android.R.layout.simple_spinner_dropdown_item);
        spnSectie.setAdapter(adapterSectie);

        CNP = getIntent().getExtras().getString("cnp");

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
            JSONArray jsonArray = null;
            try {
                jsonArray = jsonObject.getJSONArray("result");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject persoana = jsonArray.getJSONObject(i);
                    pacientID = persoana.getInt("pacientID");
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        spnSectie.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                sectie = spnSectie.getItemAtPosition(i).toString();

                String s = null;
                SectieAsync req = new SectieAsync();
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

                        medici = new ArrayList<>();
                        numeMedici = new ArrayList<>();

                        for (int j = 0; j < jsonArray.length(); j++) {
                            JSONObject persoana = jsonArray.getJSONObject(j);
                            Angajat angajat = new Angajat(persoana.getString("nume"), persoana.getString("prenume"), persoana.getInt("angajatID"));
                            medici.add(angajat);
                            numeMedici.add(angajat.getNume() + " " + angajat.getPrenume());
                        }

                        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, numeMedici);
                        spnMedic.setAdapter(adapter);
                        adapter.notifyDataSetChanged();
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnAdaugare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = (int) spnMedic.getSelectedItemId();
                medicID = medici.get(id).getAngajatID();

                new DiagnosticAsync().execute();
                finish();
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
                postDataParams.put("CNP", CNP);

                System.out.println("GOT HERE!");

                String result = CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/pacient/cautare", postDataParams);

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

    public class SectieAsync extends AsyncTask<String, String, String> {

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("sectie", sectie);

                System.out.println("GOT HERE!");

                String result = CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/angajat/cautare", postDataParams);

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

    public class DiagnosticAsync extends AsyncTask<String, String, String> {

        @SuppressLint("WrongThread")
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("descriere", tvDescriere.getText().toString().trim());
                postDataParams.put("data_internare", tvDataInternare.getText().toString().trim());
                postDataParams.put("zile_internare", tvZileInternare.getText().toString().trim());
                postDataParams.put("incepere_tratament", tvIncepereTratament.getText().toString().trim());
                postDataParams.put("durata", tvDurata.getText().toString().trim());
                postDataParams.put("indicatii_suplimentare", tvIndicatii.getText().toString().trim());
                postDataParams.put("pacientID", pacientID);
                postDataParams.put("angajatID", medicID);


                System.out.println("GOT HERE!");

                String result = CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/pacient/diagnostic", postDataParams);

                System.out.println("I have received a result!");
                System.out.println(result);

                return result;

            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(getApplicationContext(), "Diagnosticul a fost adaugat!", Toast.LENGTH_LONG).show();
        }
    }
}
