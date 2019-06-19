package com.example.meditracker.clase;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public abstract class Persoana {
    String nume;
    String prenume;
    LocalDate data_nastere;
    Boolean sex; // 1 for F, 0 for M
    String Adresa;
    String Telefon;
    String Email;
    String CNP;

    @RequiresApi(api = Build.VERSION_CODES.O)
    int varsta(){
        LocalDate now = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();;
        return Period.between(this.data_nastere, now).getYears();
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public String getPrenume() {
        return prenume;
    }

    public void setPrenume(String prenume) {
        this.prenume = prenume;
    }

    public LocalDate getData_nastere() {
        return data_nastere;
    }

    public void setData_nastere(LocalDate data_nastere) {
        this.data_nastere = data_nastere;
    }

    public Boolean getSex() {
        return sex;
    }

    public void setSex(Boolean sex) {
        this.sex = sex;
    }

    public String getAdresa() {
        return Adresa;
    }

    public void setAdresa(String adresa) {
        Adresa = adresa;
    }

    public String getTelefon() {
        return Telefon;
    }

    public void setTelefon(String telefon) {
        Telefon = telefon;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCNP() {
        return CNP;
    }

    public void setCNP(String CNP) {
        this.CNP = CNP;
    }
}
