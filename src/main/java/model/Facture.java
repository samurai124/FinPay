package model;

import java.time.LocalDateTime;

public class Facture {
    private int id ;
    private String numero;
    private double montant;
    private boolean statut;
    private LocalDateTime date;
    private Prestataire prestataire;
    private Client client;

    public Facture(String numero, double montant,boolean status, LocalDateTime date){
        this.numero = numero;
        this.montant = montant;
        this.statut = status;
        this.date = date;
    }

    public Facture(int id ,String numero, double montant,boolean status,Client client,Prestataire prestataire){
        this.id = id;
        this.numero = numero;
        this.montant = montant;
        this.statut = status;
        this.client=client;
        this.prestataire=prestataire;
    }

    public Facture(String numero, double montant,boolean status,Client client,Prestataire prestataire){
        this.id = id;
        this.numero = numero;
        this.montant = montant;
        this.statut = status;
        this.client=client;
        this.prestataire=prestataire;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public boolean isStatut() {
        return statut;
    }

    public void setStatut(boolean statut) {
    this.statut = statut;
    }
    public Boolean getStatut() {
return statut;
    }

    public Prestataire getPrestataire() {
        return prestataire;
    }

    public void setPrestataire(Prestataire prestataire) {
        this.prestataire = prestataire;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}
