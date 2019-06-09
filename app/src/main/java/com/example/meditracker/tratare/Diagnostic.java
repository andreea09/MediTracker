package com.example.meditracker.tratare;

import java.util.List;

public class Diagnostic {
    String diagnostic;
    List<Tratament> tratament;
    List<Observatie> observatii;

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
