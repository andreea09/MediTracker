package com.example.meditracker.clase;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.meditracker.clase.tratare.Diagnostic;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Pacient implements Serializable {
    public String nume;
    public String prenume;
    public Date data_nastere;
    public int sex; // 1 for F, 0 for M
    public String Adresa;
    public String Telefon;
    public String Email;
    public String CNP;
    List<Diagnostic> istoric_diagnostice;
    String dizabilitati;
    Boolean asigurat;
    Float costuri_existente;
    Diagnostic diagnostic;
    Salon salon;
    Boolean internat;
    int PacientID;
    String Parola;
    Date data_internare;
    int zile_internare;

    public Pacient(String nume, String prenume, Date data_nastere, int sex, String adresa, String telefon, String email, String CNP, List<Diagnostic> istoric_diagnostice, String dizabilitati, Boolean asigurat, Float costuri_existente, Diagnostic diagnostic, Salon salon, Boolean internat, int pacientID, String parola, Date data_internare, int zile_internare) {
        this.nume = nume;
        this.prenume = prenume;
        this.data_nastere = data_nastere;
        this.sex = sex;
        Adresa = adresa;
        Telefon = telefon;
        Email = email;
        this.CNP = CNP;
        this.istoric_diagnostice = istoric_diagnostice;
        this.dizabilitati = dizabilitati;
        this.asigurat = asigurat;
        this.costuri_existente = costuri_existente;
        this.diagnostic = diagnostic;
        this.salon = salon;
        this.internat = internat;
        PacientID = pacientID;
        Parola = parola;
        this.data_internare = data_internare;
        this.zile_internare = zile_internare;
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

    public Date getData_nastere() {
        return data_nastere;
    }

    public void setData_nastere(Date data_nastere) {
        this.data_nastere = data_nastere;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
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

    public Date getData_internare() {
        return data_internare;
    }

    public void setData_internare(Date data_internare) {
        this.data_internare = data_internare;
    }

    public int getZile_internare() {
        return zile_internare;
    }

    public void setZile_internare(int zile_internare) {
        this.zile_internare = zile_internare;
    }

    public String getParola() {
        return Parola;
    }

    public void setParola(String parola) {
        Parola = parola;
    }

    public int getPacientID() {
        return PacientID;
    }

    public void setPacientID(int pacientID) {
        PacientID = pacientID;
    }

    public List<Diagnostic> getIstoric_diagnostice() {
        return istoric_diagnostice;
    }

    public void setIstoric_diagnostice(List<Diagnostic> istoric_diagnostice) {
        this.istoric_diagnostice = istoric_diagnostice;
    }

    public String getDizabilitati() {
        return dizabilitati;
    }

    public void setDizabilitati(String dizabilitati) {
        this.dizabilitati = dizabilitati;
    }

    public Boolean getAsigurat() {
        return asigurat;
    }

    public void setAsigurat(Boolean asigurat) {
        this.asigurat = asigurat;
    }

    public Float getCosturi_existente() {
        return costuri_existente;
    }

    public void setCosturi_existente(Float costuri_existente) {
        this.costuri_existente = costuri_existente;
    }

    public Diagnostic getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(Diagnostic diagnostic) {
        this.diagnostic = diagnostic;
    }

    public Salon getSalon() {
        return salon;
    }

    public void setSalon(Salon salon) {
        this.salon = salon;
    }

    public Boolean getInternat() {
        return internat;
    }

    public void setInternat(Boolean internat) {
        this.internat = internat;
    }

    @Override
    public String toString() {
        return "Pacient{" +
                "istoric_diagnostice=" + istoric_diagnostice +
                ", dizabilitati='" + dizabilitati + '\'' +
                ", asigurat=" + asigurat +
                ", costuri_existente=" + costuri_existente +
                ", diagnostic=" + diagnostic +
                ", salon=" + salon +
                ", internat=" + internat +
                ", nume='" + nume + '\'' +
                ", prenume='" + prenume + '\'' +
                ", data_nastere=" + data_nastere +
                ", sex=" + sex +
                ", Adresa='" + Adresa + '\'' +
                ", Telefon='" + Telefon + '\'' +
                ", Email='" + Email + '\'' +
                ", CNP='" + CNP + '\'' +
                '}';
    }


}
