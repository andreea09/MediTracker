package com.example.meditracker.clase.persoane;

import com.example.meditracker.clase.Feedback;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class Angajat implements Serializable {
    public String nume;
    public String prenume;
    public Date data_nastere;
    public int sex; // 1 for F, 0 for M
    public String Adresa;
    public String Telefon;
    public String Email;
    public String CNP;
    public int angajatID;
    public String sectie;
    public Date data_angajare;
    public int salariu;
    public String contract_angajare;
    public String pozitie;
    public String Parola;
    public List<Pacient> pacienti_activi;

    public Angajat(String nume, String prenume, int angajatID) {
        this.nume = nume;
        this.prenume = prenume;
        this.angajatID = angajatID;
    }

    public Angajat(String nume, String prenume, Date data_nastere, int sex, String adresa, String telefon, String email, String CNP, int angajatID, String sectie, Date data_angajare, int salariu, String contract_angajare, String pozitie, String parola, List<Pacient> pacienti_activi) {
        this.nume = nume;
        this.prenume = prenume;
        this.data_nastere = data_nastere;
        this.sex = sex;
        Adresa = adresa;
        Telefon = telefon;
        Email = email;
        this.CNP = CNP;
        this.angajatID = angajatID;
        this.sectie = sectie;
        this.data_angajare = data_angajare;
        this.salariu = salariu;
        this.contract_angajare = contract_angajare;
        this.pozitie = pozitie;
        Parola = parola;
        this.pacienti_activi = pacienti_activi;
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

    public int getAngajatID() {
        return angajatID;
    }

    public void setAngajatID(int angajatID) {
        this.angajatID = angajatID;
    }

    public String getSectie() {
        return sectie;
    }

    public void setSectie(String sectie) {
        this.sectie = sectie;
    }

    public Date getData_angajare() {
        return data_angajare;
    }

    public void setData_angajare(Date data_angajare) {
        this.data_angajare = data_angajare;
    }

    public int getSalariu() {
        return salariu;
    }

    public void setSalariu(int salariu) {
        this.salariu = salariu;
    }

    public String getContract_angajare() {
        return contract_angajare;
    }

    public void setContract_angajare(String contract_angajare) {
        this.contract_angajare = contract_angajare;
    }

    public String getPozitie() {
        return pozitie;
    }

    public void setPozitie(String pozitie) {
        this.pozitie = pozitie;
    }

    public String getParola() {
        return Parola;
    }

    public void setParola(String parola) {
        Parola = parola;
    }

    public List<Pacient> getPacienti_activi() {
        return pacienti_activi;
    }

    public void setPacienti_activi(List<Pacient> pacienti_activi) {
        this.pacienti_activi = pacienti_activi;
    }

    Feedback generareFeedback(){
        return new Feedback();
    }


    @Override
    public String toString() {
        return "Angajat{" +
                "angajatID=" + angajatID +
                ", sectie='" + sectie + '\'' +
                ", data_angajare=" + data_angajare +
                ", salariu=" + salariu +
                ", contract_angajare='" + contract_angajare + '\'' +
                ", pozitie='" + pozitie + '\'' +
                ", Parola='" + Parola + '\'' +
                ", pacienti_activi=" + pacienti_activi +
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
