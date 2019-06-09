package com.example.meditracker.tratare;

import java.time.LocalDate;

public class Observatie {
    String angajatID;
    String pacientCNP;
    String sectie;
    String continut;
    LocalDate data_ora;

    public String getAngajatID() {
        return angajatID;
    }

    public void setAngajatID(String angajatID) {
        this.angajatID = angajatID;
    }

    public String getPacientCNP() {
        return pacientCNP;
    }

    public void setPacientCNP(String pacientCNP) {
        this.pacientCNP = pacientCNP;
    }

    public String getSectie() {
        return sectie;
    }

    public void setSectie(String sectie) {
        this.sectie = sectie;
    }

    public String getContinut() {
        return continut;
    }

    public void setContinut(String continut) {
        this.continut = continut;
    }

    public LocalDate getData_ora() {
        return data_ora;
    }

    public void setData_ora(LocalDate data_ora) {
        this.data_ora = data_ora;
    }
}
