package com.example.meditracker.clase;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.meditracker.R;
import com.example.meditracker.activitati.AdaugareDiagnosticActivity;
import com.example.meditracker.activitati.AdaugareObservatiiActivity;
import com.example.meditracker.activitati.LoginActivity;
import com.example.meditracker.activitati.MainActivity;
import com.example.meditracker.clase.persoane.Pacient;
import com.example.meditracker.db_connectors.CallAPI;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class PacientAdapter extends RecyclerView.Adapter<PacientAdapter.ViewHolder> {
    private static ClickListener clickListener;
    public List<Pacient> pacienti;
    public Activity activitate;
    Context context = null;
    Pacient pacientSelectat = null;

    public PacientAdapter(List<Pacient> pacienti, Activity activitate) {
        this.pacienti = pacienti;
        this.activitate = activitate;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView descriere;
        public Button btnObservatii, btnDiagnostic;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            descriere = itemView.findViewById(R.id.tv_nume_pacient_main);
            btnObservatii = itemView.findViewById(R.id.btn_adaugare_observatie_main);
            btnDiagnostic = itemView.findViewById(R.id.btn_adaugare_diagnostic_main);

            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        PacientAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    @NonNull
    @Override
    public PacientAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pacient_card_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PacientAdapter.ViewHolder holder, int position) {
        final Pacient p = pacienti.get(position);
        pacientSelectat = p;
        holder.descriere.setText(p.getNume() + " " + p.getPrenume() + "\n" + p.getDiagnostic().getDiagnostic());

        holder.btnDiagnostic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdaugareDiagnosticActivity.class);
                intent.putExtra("cnp", p.getCNP());
                context.startActivity(intent);
            }
        });

        holder.btnObservatii.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AdaugareObservatiiActivity.class);
                intent.putExtra("cnp", p.getCNP());
                intent.putExtra("angajatID",MainActivity.angajat.getAngajatID());
                intent.putExtra("sectie", MainActivity.angajat.getSectie());

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
                        JSONObject diagnostic = (JSONObject) jsonArray.get(jsonArray.length()-1);

                        intent.putExtra("diagnosticID", diagnostic.getInt("diagnosticID"));
                        context.startActivity(intent);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });
    }

    @Override
    public int getItemCount() {
        if(pacienti == null) {
            return 0;
        } else {
            return pacienti.size();
        }
    }

    public class RequestAsync extends AsyncTask<String, String, String> {
        @SuppressLint("WrongThread")
        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        @Override
        protected String doInBackground(String... strings) {
            try {
                JSONObject postDataParams = new JSONObject();
                postDataParams.put("pacientID", pacientSelectat.getPacientID());

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
