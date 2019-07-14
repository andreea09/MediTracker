package com.example.meditracker.clase.persoane;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Administrator implements Serializable {
    String utilizator;
    String parola;
    String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUtilizator() {
        return utilizator;
    }

    public void setUtilizator(String utilizator) {
        this.utilizator = utilizator;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    @Override
    public String toString() {
        return "Administrator{" +
                "utilizator='" + utilizator + '\'' +
                ", parola='" + parola + '\'' +
                ", email='" + email + '\'' +
                '}';
    }


}
