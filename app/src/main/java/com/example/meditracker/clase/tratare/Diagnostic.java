package com.example.meditracker.clase.tratare;

import com.example.meditracker.clase.persoane.Angajat;

import java.util.List;

public class Diagnostic {
    String diagnostic;
    List<Tratament> tratament;
    List<Observatie> observatii;
    Angajat medic;

    public Diagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public Angajat getMedic() {
        return medic;
    }

    public void setMedic(Angajat medic) {
        this.medic = medic;
    }

    public String getDiagnostic() {
        return diagnostic;
    }

    public void setDiagnostic(String diagnostic) {
        this.diagnostic = diagnostic;
    }

    public List<Tratament> getTratament() {
        return tratament;
    }

    public void setTratament(List<Tratament> tratament) {
        this.tratament = tratament;
    }

    public List<Observatie> getObservatii() {
        return observatii;
    }

    public void setObservatii(List<Observatie> observatii) {
        this.observatii = observatii;
    }
}
