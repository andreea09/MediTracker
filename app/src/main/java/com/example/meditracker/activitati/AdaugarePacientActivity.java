package com.example.meditracker.activitati;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditracker.R;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONObject;

public class AdaugarePacientActivity extends AppCompatActivity {
    Button btnAdaugare;
    TextView tvNume, tvPrenume, tvDataNasterii, tvAdresa, tvNumarTelefon, tvAdresaEmail, tvCNP, tvParola, tvDiagnostic, tvSalon;
    Spinner spnSex, spnMedic, spnSectie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_pacient);

        btnAdaugare = findViewById(R.id.btn_creare_pacient);
        tvNume = findViewById(R.id.et_nume_pacient);
        tvPrenume = findViewById(R.id.et_prenume_pacient);
        tvDataNasterii = findViewById(R.id.et_dataN_pacient);
        tvNumarTelefon = findViewById(R.id.et_nrTel_pacient);
        tvAdresa = findViewById(R.id.et_adresa_pacient);
        tvAdresaEmail = findViewById(R.id.et_adresaE_pacient);
        tvCNP = findViewById(R.id.et_cnp_pacient);
        tvParola = findViewById(R.id.et_parola_pacient);
        tvDiagnostic = findViewById(R.id.et_diagnostic_pacient);
        tvSalon = findViewById(R.id.et_salon_pacient);
        spnSex = findViewById(R.id.sp_sex_angajat);
        spnMedic = findViewById(R.id.sp_medic_pacient);
        spnSectie = findViewById(R.id.sp_sectie_angajat);

        ArrayAdapter<CharSequence> adapterSex = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spn_sex, android.R.layout.simple_spinner_dropdown_item);
        spnSex.setAdapter(adapterSex);
        ArrayAdapter<CharSequence> adapterSectie = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spn_sectie, android.R.layout.simple_spinner_dropdown_item);
        spnSectie.setAdapter(adapterSectie);

        btnAdaugare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new RequestAsync().execute();
            }
        });

    }

    public class RequestAsync extends AsyncTask<String, String, String> {
        @SuppressLint("WrongThread")
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                //GET Request
                //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("nume", tvNume.getText().toString().trim());
                postDataParams.put("prenume", tvPrenume.getText().toString().trim());
                postDataParams.put("data_nastere", tvDataNasterii.getText().toString().trim());
                postDataParams.put("sex", spnSex.getSelectedItemId());
                postDataParams.put("adresa", tvAdresa.getText().toString().trim());
                postDataParams.put("telefon", tvNumarTelefon.getText().toString().trim());
                postDataParams.put("email", tvAdresaEmail.getText().toString().trim());
                postDataParams.put("cnp", tvCNP.getText().toString().trim());
                postDataParams.put("sectie", spnSectie.getSelectedItem().toString().trim());
                postDataParams.put("parola", tvParola.getText().toString().trim());

                System.out.println("GOT HERE!");

                return CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/pacient/creare", postDataParams);


            } catch (Exception e) {
                return "Exception: " + e.getMessage();
            }

        }

        @Override
        protected void onPostExecute(String s) {
            if (s != null) {
                Toast.makeText(getApplicationContext(), "Angajatul a fost adaugat!", Toast.LENGTH_LONG).show();
            }
        }

    }
}
