package com.example.meditracker.activitati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.meditracker.R;

public class WelcomeActivity extends AppCompatActivity {
    private Button pacient, angajat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        pacient = findViewById(R.id.btn_pacient);
        angajat = findViewById(R.id.btn_angajat);

        pacient.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PacientLoginActivity.class);
                startActivity(intent);
            }
        });

        angajat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AngajatLoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
