package model;

public class FacturePrestataire {
    private int idf;
    private int nmbrFacture;
    private double smontant;

    public FacturePrestataire(int idf, int nmbrFacture, double smontant) {
        this.idf = idf;
        this.nmbrFacture = nmbrFacture;
        this.smontant = smontant;
    }

    public int getIdf() {
        return idf;
    }

    public void setIdf(int idf) {
        this.idf = idf;
    }

    public int getNmbrFacture() {
        return nmbrFacture;
    }

    public void setNmbrFacture(int nmbrFacture) {
        this.nmbrFacture = nmbrFacture;
    }

    public double getSmontant() {
        return smontant;
    }

    public void setSmontant(double smontant) {
        this.smontant = smontant;
    }
}