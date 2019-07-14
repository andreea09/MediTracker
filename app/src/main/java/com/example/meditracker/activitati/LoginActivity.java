package com.example.meditracker.activitati;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.meditracker.R;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {
    Button btnLogare;
    TextView tvEmail, tvParola, tvUitatParola;
    CheckBox cbRetine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btnLogare = findViewById(R.id.btn_logare);
        tvEmail = findViewById(R.id.et_email);
        tvParola = findViewById(R.id.et_parola);
        tvUitatParola = findViewById(R.id.tv_uitat_parola);
        cbRetine = findViewById(R.id.cb_retine);

        btnLogare.setOnClickListener(new View.OnClickListener() {
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

                try {

                    JSONObject jsonObject = new JSONObject(s);
                    try {
                        JSONArray jsonArray = jsonObject.getJSONArray("result");
                        String tip = jsonObject.getString("tip");

                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject persoana = jsonArray.getJSONObject(i);
                            Toast.makeText(getApplicationContext(), persoana.getString("nume") + persoana.getString("prenume"), Toast.LENGTH_LONG).show();
                        }}
                    catch (JSONException e){
                        Toast.makeText(getApplicationContext(), "E-mail sau parola gresite!", Toast.LENGTH_LONG).show();
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
                //GET Request
                //return RequestHandler.sendGet("https://prodevsblog.com/android_get.php");

                // POST Request
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("email", tvEmail.getText().toString().trim());
                postDataParams.put("parola", tvParola.getText().toString().trim());

                System.out.println("GOT HERE!");

                // Log.w("db",CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/create", postDataParams));
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
}
