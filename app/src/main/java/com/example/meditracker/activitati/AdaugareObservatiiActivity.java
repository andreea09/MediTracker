package com.example.meditracker.activitati;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditracker.R;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class AdaugareObservatiiActivity extends AppCompatActivity {
    Button btnAdaugare;
    TextView tvData, tvOra, tvDescriere;
    int angajatID, diagnosticID;
    String cnp, sectie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adaugare_observatii);

        btnAdaugare = findViewById(R.id.btn_adaugare_observatie);
        tvData = findViewById(R.id.et_data_observatie);
        tvOra = findViewById(R.id.et_ora_observatie);
        tvDescriere = findViewById(R.id.et_observatie);

        angajatID = getIntent().getExtras().getInt("angajatID");
        diagnosticID = getIntent().getExtras().getInt("diagnosticID");
        cnp = getIntent().getExtras().getString("cnp");
        sectie = getIntent().getExtras().getString("sectie");

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
                postDataParams.put("pacientCNP", cnp);
                postDataParams.put("sectie", sectie);
                postDataParams.put("angajatID", angajatID);
                postDataParams.put("diagnosticID", diagnosticID);
                postDataParams.put("continut", tvDescriere.getText().toString().trim());
                postDataParams.put("data_ora", tvData.getText().toString().trim());

                System.out.println("GOT HERE!");

                String result = CallAPI.sendPost("https://scenic-hydra-241121.appspot.com//pacient/diagnostic/observatii/creare", postDataParams);

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
            Toast.makeText(getApplicationContext(), "Observatia a fost adaugata!", Toast.LENGTH_LONG).show();
        }


    }
}
