package service;

import dao.AdminDAO;
import model.Admin;
import model.Client;
import model.Prestataire;
import util.ValidationDonnees;

import static dao.LoginDAO.*;

public class LogInService {
    public Client clientLogIn(){
        System.out.printf("╔══════════════════════════════════════╗\n" +
                          "║   SYSTEM : FINPAY_SECURE_TERMINAL    ║\n" +
                          "║   STATUS : Client login              ║\n" +
                          "╚══════════════════════════════════════╝\n");
        int id = ValidationDonnees.validateInts("Votre id pour l'authentification ");
        Client client = clientLogInDB(id);
        if (client == null){
            System.out.printf("l'ID : %d  nexist pas\n",id);
            return null;
        }

        System.out.printf("╔══════════════════════════════════════╗\n" +
                          "║               Welcome                ║\n" +
                          "║                %-10s            ║\n" +
                          "╚══════════════════════════════════════╝\n",
                client.getNom());
        return client;
    }

    public Admin adminLogIN(){

        System.out.printf("╔══════════════════════════════════════╗\n" +
                          "║   SYSTEM : FINPAY_SECURE_TERMINAL    ║\n" +
                          "║   STATUS :  Admin login              ║\n" +
                          "╚══════════════════════════════════════╝\n");
        int id = ValidationDonnees.validateInts("Votre id pour l'authentification  ");
        Admin admin = adminLogInDB(id);
        if (admin == null){
            System.out.printf("l'ID : %d  nexist pas\n",id);
            return null;
        }

        int password = ValidationDonnees.validateInts("votre password");
        if (admin.getPassword() != password){
            System.out.println("Password incorrect !!!");
            return null;
        }

        System.out.printf("╔══════════════════════════════════════╗\n" +
                          "║               Welcome                ║\n" +
                          "║                %-10s            ║\n" +
                          "╚══════════════════════════════════════╝\n",
                admin.getNom());
        return admin;
    }


    public Prestataire prestataireLogIN(){

        System.out.printf("╔══════════════════════════════════════╗\n" +
                          "║   SYSTEM : FINPAY_SECURE_TERMINAL    ║\n" +
                          "║   STATUS :  Prestataire login        ║\n" +
                          "╚══════════════════════════════════════╝\n");
        int id = ValidationDonnees.validateInts("Votre id pour l'authentification  ");
        Prestataire prestataire = prestataireLogInDB(id);
        if (prestataire == null){
            System.out.printf("l'ID : %d  nexist pas\n",id);
            return null;
        }

        System.out.printf("╔══════════════════════════════════════╗\n" +
                          "║               Welcome                ║\n" +
                          "║           %-15s            ║\n" +
                          "╚══════════════════════════════════════╝\n",
                prestataire.getNomEntreprise());
        return prestataire;
    }
}
