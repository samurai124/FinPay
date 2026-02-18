package model;

public class Client {
    private int id;
    private String nom;
    private float solde;

    public Client(String nom , float solde){
        this.nom = nom;
        this.solde = solde;
    }

    public Client(int id, String nom,float solde) {
        this.id = id;
        this.nom = nom;
        this.solde = solde;
    }

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
