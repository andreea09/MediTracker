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

public class IstoricAdapter extends RecyclerView.Adapter<IstoricAdapter.ViewHolder> {
    private static ClickListener clickListener;
    public List<String> diagnostice;
    public Activity activitate;
    Context context = null;
    String diagnosticSelectat = null;

    public IstoricAdapter(List<String> diagnostice, Activity activitate) {
        this.diagnostice = diagnostice;
        this.activitate = activitate;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView descriere;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            descriere = itemView.findViewById(R.id.tv_diagnostic_istoric);

            itemView.setOnClickListener(this);
            context = itemView.getContext();
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }

    public void setOnItemClickListener(ClickListener clickListener) {
        IstoricAdapter.clickListener = clickListener;
    }

    public interface ClickListener {
        void onItemClick(int position, View v);
    }

    @NonNull
    @Override
    public IstoricAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.pacient_istoric_layout, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull IstoricAdapter.ViewHolder holder, int position) {
        final String p = diagnostice.get(position);
        diagnosticSelectat = p;
        holder.descriere.setText(p.toString());


    }

    @Override
    public int getItemCount() {
        if(diagnostice == null) {
            return 0;
        } else {
            return diagnostice.size();
        }
    }

   
}