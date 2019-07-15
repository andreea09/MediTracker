package com.example.meditracker.activitati;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;

import com.example.meditracker.R;
import com.example.meditracker.clase.IstoricAdapter;
import com.example.meditracker.clase.PacientAdapter;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class IstoricPacientActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    IstoricAdapter istoricAdapter;
    List<String> diagnostice = new ArrayList<>();
    List<Integer> diagnosticeID = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_istoric_pacient);

        recyclerView = findViewById(R.id.recycler_istoric);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

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
                    JSONObject diagnostic = jsonArray.getJSONObject(i);
                    diagnostice.add(diagnostic.getString("descriere"));
                    diagnosticeID.add(diagnostic.getInt("diagnosticID"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        istoricAdapter = new IstoricAdapter(diagnostice, IstoricPacientActivity.this);
        istoricAdapter.setOnItemClickListener(new IstoricAdapter.ClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricTratamenteActivity.class);
                intent.putExtra("diagnosticID", diagnosticeID.get(position));
                intent.putExtra("pacientID",getIntent().getExtras().getInt("pacientID") );
                intent.putExtra("cnp", getIntent().getExtras().getString("cnp"));
                startActivity(intent);
            }
        });
        recyclerView.setAdapter(istoricAdapter);
    }

    public class RequestAsync extends AsyncTask<String, String, String> {
        @SuppressLint("WrongThread")
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("pacientID", getIntent().getExtras().getInt("pacientID"));


                System.out.println("GOT HERE!");

                String result = CallAPI.sendPost("https://scenic-hydra-241121.appspot.com/pacient/istoric", postDataParams);

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
