package model;

import java.time.LocalDateTime;

public class CommissionFinPay {
    private int id;
    private double pourcentage;
    private double montantTotal;
    private LocalDateTime datecommission;
    Paiement paiement;

    public CommissionFinPay(int id, double pourcentage, double montantTotal, LocalDateTime date) {
        this.id = id;
        this.pourcentage = pourcentage;
        this.montantTotal = montantTotal;
        this.datecommission = date;
    }

    public CommissionFinPay(){}

    public int getId() {
        return id;
    }

    public double getPourcentage() {
        return pourcentage;
    }

    public double getMontantTotal() {
        return montantTotal;
    }

    public LocalDateTime getDatecommission() {
        return datecommission;
    }

    public Paiement getPaiement() {
        return paiement;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPourcentage(double pourcentage) {
        this.pourcentage = pourcentage;
    }

    public void setMontantTotal(double montantTotal) {
        this.montantTotal = montantTotal;
    }

    public void setDatecommission(LocalDateTime datecommission) {
        this.datecommission = datecommission;
    }

    public void setPaiement(Paiement paiement) {
        this.paiement = paiement;
    }
}
