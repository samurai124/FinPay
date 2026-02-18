package model;

public class Admin {
    private int id ;
    private String nom;
    private int password;

    public Admin(String nom, int password){
        this.nom = nom;
        this.password = password;
    }

    public Admin(int id , String nom , int password){
        this.id = id;
        this.nom = nom;
        this.password = password;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
