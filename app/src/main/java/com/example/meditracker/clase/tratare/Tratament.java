package com.example.meditracker.clase.tratare;

import com.example.meditracker.clase.tratare.Medicament;

import java.util.List;

public class Tratament {
    List<Medicament> medicamente;
    int durata;
    String indicatii_suplimentare;

    public List<Medicament> getMedicamente() {
        return medicamente;
    }

    public void setMedicamente(List<Medicament> medicamente) {
        this.medicamente = medicamente;
    }

    public int getDurata() {
        return durata;
    }

    public void setDurata(int durata) {
        this.durata = durata;
    }

    public String getIndicatii_suplimentare() {
        return indicatii_suplimentare;
    }

    public void setIndicatii_suplimentare(String indicatii_suplimentare) {
        this.indicatii_suplimentare = indicatii_suplimentare;
    }
}
