package com.example.meditracker.activitati;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.meditracker.R;
import com.example.meditracker.clase.IstoricAdapter;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class IstoricTratamenteActivity extends AppCompatActivity {
    List<String> tratamente = new ArrayList<>();
    List<String> observatii = new ArrayList<>();

    RecyclerView rvTratamente, rvObservatii;
    IstoricAdapter obsAdapter, tratamentAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric_tratamente);

        rvTratamente = findViewById(R.id.recycler_tratamente_istoric);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvTratamente.setLayoutManager(layoutManager);

        rvObservatii = findViewById(R.id.recycler_observatii_istoric);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        rvObservatii.setLayoutManager(layoutManager2);

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

                tratamentAdapter = new IstoricAdapter(tratamente, IstoricTratamenteActivity.this);
                rvTratamente.setAdapter(tratamentAdapter);


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

                        obsAdapter = new IstoricAdapter(observatii, IstoricTratamenteActivity.this);
                        rvObservatii.setAdapter(obsAdapter);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }catch (JSONException e) {
                e.printStackTrace();
            }
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }
            public class TratamentAsync extends AsyncTask<String, String, String> {
                @SuppressLint("WrongThread")
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                protected String doInBackground(String... strings) {
                    try {
                        JSONObject postDataParams = new JSONObject();
                        postDataParams.put("pacientID", getIntent().getExtras().getInt("pacientID"));
                        postDataParams.put("diagnosticID", getIntent().getExtras().getInt("diagnosticID"));

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
                        postDataParams.put("pacientCNP", getIntent().getExtras().getString("cnp"));
                        postDataParams.put("diagnosticID", getIntent().getExtras().getInt("diagnosticID"));

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

