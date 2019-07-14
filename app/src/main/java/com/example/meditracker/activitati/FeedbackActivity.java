package com.example.meditracker.activitati;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.meditracker.R;
import com.example.meditracker.clase.Constante;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        final EditText email = findViewById(R.id.et_e_mail_feedback);
        final EditText message = findViewById(R.id.et_mesaj_feecback);

        Button send = findViewById(R.id.btn_trimite_feedback);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String i_email = email.getText().toString();
                String i_message = message.getText().toString();

                if(!isValid(i_email)){

                    email.setError("Va rugam adaugati adresa de e-mail!");
                    return;
                }
                if(TextUtils.isEmpty(i_message)){
                    message.setError("Mesajul este gol!");
                }
                Intent sendEmail = new Intent(android.content.Intent.ACTION_SEND);


                sendEmail.setType("plain/text");
                sendEmail.putExtra(android.content.Intent.EXTRA_EMAIL,
                        new String[]{"andreeab09.05@gmail.com"});
                sendEmail.putExtra(android.content.Intent.EXTRA_TEXT,
                         "Message:" + '\n'+i_message);
                startActivity(Intent.createChooser(sendEmail, "E-mailul se trimite..."));

            }
        });
    }

        private boolean isValid(String email) {

            Pattern pattern = Pattern.compile(Constante.EMAIL_PATTERN);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
}