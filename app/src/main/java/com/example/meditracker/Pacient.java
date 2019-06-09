package com.example.meditracker;

import com.example.meditracker.tratare.Diagnostic;

import java.util.List;

public class Pacient extends Persoana {
    List<Diagnostic> istoric_diagnostice;
    String dizabilitati;
    Boolean asigurat;
    Float costuri_existente;
    Diagnostic diagnostic;
    Salon salon;
    Boolean internat;

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
}
