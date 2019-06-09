package com.example.meditracker;

import java.time.LocalDate;

public class Raport {
    LocalDate data_ora;
    String descriere;

    public LocalDate getData_ora() {
        return data_ora;
    }

    public void setData_ora(LocalDate data_ora) {
        this.data_ora = data_ora;
    }

    public String getDescriere() {
        return descriere;
    }

    public void setDescriere(String descriere) {
        this.descriere = descriere;
    }
}
