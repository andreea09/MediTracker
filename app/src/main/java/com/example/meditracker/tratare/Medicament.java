package com.example.meditracker.tratare;

public class Medicament {
    String medicamentID;
    String nume;
    double Cantitate;
    String frecventa_administrate;
    double cantitate_administrare;
    String durata;
    Boolean inStoc;

    public String getMedicamentID() {
        return medicamentID;
    }

    public void setMedicamentID(String medicamentID) {
        this.medicamentID = medicamentID;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public double getCantitate() {
        return Cantitate;
    }

    public void setCantitate(double cantitate) {
        Cantitate = cantitate;
    }

    public String getFrecventa_administrate() {
        return frecventa_administrate;
    }

    public void setFrecventa_administrate(String frecventa_administrate) {
        this.frecventa_administrate = frecventa_administrate;
    }

    public double getCantitate_administrare() {
        return cantitate_administrare;
    }

    public void setCantitate_administrare(double cantitate_administrare) {
        this.cantitate_administrare = cantitate_administrare;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public Boolean getInStoc() {
        return inStoc;
    }

    public void setInStoc(Boolean inStoc) {
        this.inStoc = inStoc;
    }
}
