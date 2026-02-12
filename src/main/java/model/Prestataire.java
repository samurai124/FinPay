package model;

public class Prestataire {
    private int id;
    private String nomEntreprise;
    private String email;
    private float solde;

    public Prestataire(String nomEntreprise, String email,float solde) {
        this.nomEntreprise = nomEntreprise;
        this.email = email;
        this.solde = solde;
    }

    public Prestataire(int id, String nomEntreprise, String email,float solde) {
        this.id = id;
        this.nomEntreprise = nomEntreprise;
        this.email = email;
        this.solde = solde;
    }

    public float getSolde() {
        return solde;
    }

    public void setSolde(float solde) {
        this.solde = solde;
    }

    public Prestataire(){}

    public int getId() {
        return id;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
