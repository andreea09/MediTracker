package com.example.meditracker.activitati;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.icu.text.UnicodeSetSpanner;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditracker.R;
import com.example.meditracker.clase.Constante;
import com.example.meditracker.clase.persoane.Pacient;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

public class VerificarePacientActivity extends AppCompatActivity {
    TextView tvCNP;
    Button btnVerificare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verificare_pacient);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat(Constante.SIMPLE_DATE_FORMAT);

        tvCNP = findViewById(R.id.et_CNP_verificare_pacient);
        btnVerificare = findViewById(R.id.btn_verificare_pacient);

        btnVerificare.setOnClickListener(new View.OnClickListener() {
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

                Intent intent = null;

                    try {
                        JSONObject jsonObject = new JSONObject(s);
                        JSONArray jsonArray = null;
                        try {
                             jsonArray= jsonObject.getJSONArray("result");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject persoana = jsonArray.getJSONObject(i);
                                Pacient pacient = new Pacient();
                                pacient.setNume(persoana.getString("nume"));
                                pacient.setPrenume(persoana.getString("prenume"));
                                pacient.setAdresa(persoana.getString("adresa"));
                                pacient.setData_nastere(simpleDateFormat.parse(persoana.getString("data_nastere")));
                                pacient.setCNP(persoana.getString("CNP"));
                                pacient.setPacientID(persoana.getInt("pacientID"));
                                pacient.setEmail(persoana.getString("email"));
                                pacient.setParola(persoana.getString("parola"));
                                intent =new Intent(getApplicationContext(), AdaugareDiagnosticActivity.class);
                                intent.putExtra("cnp", pacient.getCNP());
                                Toast.makeText(getApplicationContext(), "Pacientul exista deja! Se va trece la adaugarea diagnosticului.", Toast.LENGTH_LONG).show();

                        }
                        }catch (JSONException e) {
                            intent = new Intent(getApplicationContext(), AdaugarePacientActivity.class);
                            Toast.makeText(getApplicationContext(), "Pacientul nu exista! Se va trece la adaugarea pacientului.", Toast.LENGTH_LONG).show();
                        }
                        startActivity(intent);
                        } catch (ParseException e1) {
                        e1.printStackTrace();
                    } catch (JSONException e1) {
                        e1.printStackTrace();
                    }
            }
                }
        );
    }

    public class RequestAsync extends AsyncTask<String, String, String> {
        @SuppressLint("WrongThread")
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("CNP", tvCNP.getText().toString().trim());

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
}
