package model;

import java.time.LocalDateTime;

public class Paiement {
    private int id;
    private double montant;
    private LocalDateTime datePaiement;
    private boolean statut;
    private double montantCommision;
    Facture facture;


    public Paiement(int id, double montant, LocalDateTime datePaiement, boolean statut, double montantCommision) {
        this.id = id;
        this.montant = montant;
        this.datePaiement = datePaiement;
        this.statut = statut;
        this.montantCommision = montantCommision;
    }

    public Paiement(){}

    public int getId() {
        return id;
    }

    public double getMontant() {
        return montant;
    }

    public LocalDateTime getDatePaiement() {
        return datePaiement;
    }

    public boolean isStatut() {
        return statut;
    }

    public double getMontantCommision() {
        return montantCommision;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDatePaiement(LocalDateTime datePaiement) {
        this.datePaiement = datePaiement;
    }

    public void setStatut(boolean statut) {
        this.statut = statut;
    }

    public void setMontantCommision(double montantCommision) {
        this.montantCommision = montantCommision;
    }
}
