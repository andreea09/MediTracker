package com.example.meditracker.activitati;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.meditracker.R;
import com.example.meditracker.clase.Constante;
import com.example.meditracker.clase.IstoricAdapter;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class ProfilPacientActivity extends AppCompatActivity {
    Button btnAdaugare;
    TextView tvNume, tvPrenume, tvDataNasterii, tvAdresa, tvNumarTelefon, tvAdresaEmail, tvCNP, tvDiagnostic, tvSalon, tvPat, tvSex;
    CheckBox cbAsigurat, cbDizabilitati;
    ListView lvTratamente, lvObservatii;
    String CNP;
    Integer diagnosticID;
    Integer pacientID;
    SimpleDateFormat format = new SimpleDateFormat(Constante.SIMPLE_DATE_FORMAT);
    List<String> tratamente = new ArrayList<>();
    List<String> observatii = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_pacient);

        btnAdaugare = findViewById(R.id.btn_istoric_profil);
        tvNume = findViewById(R.id.et_nume_pacient_profil);
        tvPrenume = findViewById(R.id.et_prenume_pacient_profil);
        tvDataNasterii = findViewById(R.id.et_dataN_pacient_profil);
        tvNumarTelefon = findViewById(R.id.et_nrTel_pacient_profil);
        tvAdresa = findViewById(R.id.et_adresa_pacient_profil);
        tvAdresaEmail = findViewById(R.id.et_adresaE_pacient_profil);
        tvCNP = findViewById(R.id.et_cnp_pacient_profil);
        tvSalon = findViewById(R.id.et_salon_pacient_profil);
        tvPat = findViewById(R.id.et_pat_pacienti_profil);
        tvSex = findViewById(R.id.et_sex_pacient_profil);
        tvDiagnostic = findViewById(R.id.et_diagnostic_profil_profil);
        cbAsigurat = findViewById(R.id.cb_asigurat_pacient_profil);
        cbDizabilitati = findViewById(R.id.cb_dizabilitati_pacient_profil);
        lvTratamente = findViewById(R.id.lv_tratamente_profil);
        lvObservatii = findViewById(R.id.lv_observatii_profil);

        CNP = getIntent().getExtras().getString("cnp");
        diagnosticID = getIntent().getExtras().getInt("diagnosticID");

        System.out.println("Date pacient " +CNP+ " " +pacientID+ " " +diagnosticID);

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

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject persoana = jsonArray.getJSONObject(i);

                    tvNume.setText(persoana.getString("nume"));
                    tvPrenume.setText(persoana.getString("prenume"));
                    tvDataNasterii.setText(persoana.getString("data_nastere").split("T")[0]);
                    tvSex.setText(persoana.getInt("sex")==1?"F": "M");
                    tvAdresa.setText(persoana.getString("adresa"));
                    tvAdresaEmail.setText(persoana.getString("email"));
                    tvNumarTelefon.setText(persoana.getString("telefon"));
                    tvCNP.setText(persoana.getString("CNP"));

                    pacientID = persoana.getInt("pacientID");

                    JSONObject asigurat = persoana.getJSONObject("asigurat");

                    if(asigurat.getString("data").equals("[1]"))
                    {
                        cbAsigurat.setChecked(true);
                    }
                    if(persoana.getInt("dizabilitati")==1)
                    {
                        cbDizabilitati.setChecked(true);
                    }
                    tvSalon.setText(String.valueOf(persoana.getInt("salon")));
                    tvPat.setText(String.valueOf(persoana.getInt("pat")));
                    tvDiagnostic.setText(getIntent().getExtras().getString("diagnostic"));

                    String s1 = null;
                    TratamentAsync req1 = new TratamentAsync();
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
                                JSONObject tratament = jsonArray1.getJSONObject(j);

                                tratamente.add(tratament.getString("indicatii_suplimentare") + " pe durata " + tratament.getInt("durata") + " zile incepand cu data " + tratament.getString("incepere_tratament").split("T")[0]);

                            }

                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,tratamente);
                            lvTratamente.setAdapter(adapter);


                            String s2 = null;
                            DiagnosticAsync req2 = new DiagnosticAsync();
                            try {
                                s2 = req2.execute().get();
                            } catch (ExecutionException e) {
                                e.printStackTrace();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            System.out.println(s2);

                            try {

                                JSONObject jsonObject2 = new JSONObject(s2);
                                try {
                                    JSONArray jsonArray2 = jsonObject2.getJSONArray("result");

                                    for (int k = 0; k < jsonArray2.length(); k++) {
                                        JSONObject observatie = jsonArray2.getJSONObject(k);

                                        observatii.add(observatie.getString("data_ora").split("T")[0] + " - " + observatie.getString("continut"));
                                    }

                                    ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,observatii);
                                    lvObservatii.setAdapter(adapter1);

                                }catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnAdaugare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), IstoricPacientActivity.class);
                intent.putExtra("pacientID", pacientID);
                intent.putExtra("diagnosticID", diagnosticID);
                intent.putExtra("cnp", CNP);
                startActivity(intent);
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

    public class TratamentAsync extends AsyncTask<String, String, String> {
        @SuppressLint("WrongThread")
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("pacientID", pacientID);
                postDataParams.put("diagnosticID", diagnosticID);

                System.out.println("GOT HERE!");

                String result = CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/pacient/diagnostic/tratament", postDataParams);

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
                postDataParams.put("pacientCNP", CNP);
                postDataParams.put("diagnosticID", diagnosticID);

                System.out.println("GOT HERE!");

                String result = CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/pacient/diagnostic/observatii", postDataParams);

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
}
