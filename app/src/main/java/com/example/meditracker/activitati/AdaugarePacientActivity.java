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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditracker.R;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AdaugarePacientActivity extends AppCompatActivity {
    Button btnAdaugare;
    TextView tvNume, tvPrenume, tvDataNasterii, tvAdresa, tvNumarTelefon, tvAdresaEmail, tvCNP, tvParola, tvDiagnostic, tvSalon, tvPat;
    Spinner spnSex;
    CheckBox cbAsigurat, cbDizabilitati;

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
        tvSalon = findViewById(R.id.et_salon_pacient);
        tvPat = findViewById(R.id.et_pat_pacienti);
        spnSex = findViewById(R.id.sp_sex_pacient);
        cbAsigurat = findViewById(R.id.cb_asigurat_pacient);
        cbDizabilitati = findViewById(R.id.cb_dizabilitati_pacient);


        ArrayAdapter<CharSequence> adapterSex = ArrayAdapter.createFromResource(getApplicationContext(), R.array.spn_sex, android.R.layout.simple_spinner_dropdown_item);
        spnSex.setAdapter(adapterSex);


        btnAdaugare.setOnClickListener(new View.OnClickListener() {
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
                Intent intent = new Intent(getApplicationContext(), AdaugareDiagnosticActivity.class);
                intent.putExtra("cnp",tvCNP.getText().toString());
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
                postDataParams.put("CNP", tvCNP.getText().toString().trim());
                postDataParams.put("pat", tvPat.getText().toString().trim());
                postDataParams.put("salon", tvSalon.getText().toString().trim());
                postDataParams.put("internat", 1);
                postDataParams.put("costuri_existente", 0);
                postDataParams.put("dizabilitati", cbDizabilitati.isChecked()? 1:0);
                postDataParams.put("asigurat", cbAsigurat.isChecked()? 1:0);
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
                Toast.makeText(getApplicationContext(), "Pacientul a fost adaugat!", Toast.LENGTH_LONG).show();
            }
        }

    }
}
