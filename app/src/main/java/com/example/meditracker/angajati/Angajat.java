package com.example.meditracker.angajati;

import com.example.meditracker.Feedback;
import com.example.meditracker.Pacient;
import com.example.meditracker.Persoana;

import java.time.LocalDate;
import java.util.List;

public abstract class Angajat extends Persoana {

    String angajatID;
    String sectie;
    LocalDate data_angajare;
    int salariu;
    String contract_angajare;
    String pozitie;
    String Parola;
    List<Pacient> pacienti_activi;

    public String getAngajatID() {
        return angajatID;
    }

    public void setAngajatID(String angajatID) {
        this.angajatID = angajatID;
    }

    public String getSectie() {
        return sectie;
    }

    public void setSectie(String sectie) {
        this.sectie = sectie;
    }

    public LocalDate getData_angajare() {
        return data_angajare;
    }

    public void setData_angajare(LocalDate data_angajare) {
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

}
