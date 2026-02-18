package model;

import java.time.LocalDateTime;

public class Paiement {
    private int id;
    private double montant;
    private LocalDateTime datePaiement;
    private boolean statut;
    private double montantCommision;
    private  String modePaiement;
    private Facture facture;

    public Paiement(int id, double montant, LocalDateTime datePaiement, boolean statut, double montantCommision,String modePaiement ,Facture facture) {
        this.id = id;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.statut = statut;
        this.montantCommision = montantCommision;
        this.modePaiement=modePaiement;
        this.facture = facture;
    }

    public String getModePaiement() {
        return modePaiement;
    }

    public void setModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public double getMontantCommision() {
        return montantCommision;
    }

    public void setMontantCommision(double montantCommision) {
        this.montantCommision = montantCommision;
    }

    public Facture getFacture() {
        return facture;
    }

    public void setFacture(Facture facture) {
        this.facture = facture;
    }
}