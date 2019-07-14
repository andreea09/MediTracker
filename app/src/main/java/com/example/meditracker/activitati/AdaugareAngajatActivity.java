package com.example.meditracker.activitati;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditracker.R;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONObject;

public class AdaugareAngajatActivity extends AppCompatActivity {
    Button btnAdaugare;
    TextView tvNume, tvPrenume, tvDataNasterii, tvAdresa, tvNumarTelefon, tvAdresaEmail, tvCNP, tvParola;
    Spinner spnSex, spnRol, spnSectie;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_angajat);

        btnAdaugare = findViewById(R.id.btn_creare_angajat);
        tvNume = findViewById(R.id.et_nume_angajat);
        tvPrenume = findViewById(R.id.et_prenume_angajat);
        tvDataNasterii = findViewById(R.id.et_dataN_angajat);
        tvNumarTelefon = findViewById(R.id.et_nrTel_angajat);
        tvAdresa = findViewById(R.id.et_adresa_angajat);
        tvAdresaEmail = findViewById(R.id.et_adresaE_angajat);
        tvCNP = findViewById(R.id.et_cnp_angajat);
        tvParola = findViewById(R.id.et_parola_angajat);
        spnSex = findViewById(R.id.sp_sex_angajat);
        spnRol = findViewById(R.id.sp_pozitie_angajat);
        spnSectie = findViewById(R.id.sp_sectie_angajat);

        ArrayAdapter<CharSequence> adapterSex = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spn_sex, android.R.layout.simple_spinner_dropdown_item);
        spnSex.setAdapter(adapterSex);

        ArrayAdapter<CharSequence> adapterRol = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spn_rol, android.R.layout.simple_spinner_dropdown_item);
        spnRol.setAdapter(adapterRol);

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
                postDataParams.put("pozitie", spnRol.getSelectedItem().toString().trim());
                postDataParams.put("sectie", spnSectie.getSelectedItem().toString().trim());
                postDataParams.put("parola", tvParola.getText().toString().trim());

                System.out.println("GOT HERE!");

               // Log.w("db",CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/create", postDataParams));
                return CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/create", postDataParams);


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
