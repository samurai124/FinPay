package service;

import Statistiques.Statistiques;
import model.Admin;
import model.Client;
import model.Prestataire;
import util.ValidationDonnees;

import static Excel.ExcelAdmin.exelData;
import static Excel.ExcelAdmin.exportFacturesImpayees;

public class Menu {

    private static ClientService clientService = new ClientService();
    private static PrestataireService prestataireService = new PrestataireService();
    private static PaimentService paimentService = new PaimentService();
    private static FactureService factureService = new FactureService();
    private static LogInService logInService = new LogInService();

    public void menuPrencipal() {
        int choix;
        do {
            System.out.println("------------------------------------------------------");
            System.out.println("-----------------------MENU---------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("1 - ADMIN \n2 - PRESTATAIRE \n3 - CLIENT\n4 - QUITTER");
            choix = ValidationDonnees.validateInts("votre choix");
            switch (choix) {
                case 1 -> adminMenu();
                case 2 -> prestataireMenu();
                case 3 -> clientMenu();
                case 4 -> System.out.println("Merci d'avoir utilisé FinPay. Au revoir !");
                default -> System.out.println("Entrez un nombre entre 1 et 4");
            }
        } while (choix != 4);
    }

    public void adminMenu() {
        Admin admin = logInService.adminLogIN();
        if (admin == null) {
            System.out.println("Échec de l'authentification Admin.");
            return;
        }

        int choix;
        do {
            System.out.println("------------------------------------------------------");
            System.out.println("--- ESPACE ADMIN : " + admin.getNom() + " ---");
            System.out.println("1- Gérer les prestataires\n2- Gérer les clients\n3- Statistiques\n4- Export Rapport Global Mensuel\n5- Export des Factures Impayées\n6- Retour");
            System.out.println("------------------------------------------------------");
            choix = ValidationDonnees.validateInts("votre choix");
            switch (choix) {
                case 1 -> gererPrestataire();
                case 2 -> gererClient();
                case 3 -> {
                    Statistiques.afficherStatistiquesGlobales();
                    Statistiques.afficherHistoriqueFinancier();
                }
                case 4 -> exelData() ;
                case 5 -> exportFacturesImpayees();
                case 6 -> System.out.println("Retour au menu principal...");
                default -> System.out.println("Entrez un nombre entre 1 et 5");
            }
        } while (choix != 6);
    }

    public void prestataireMenu() {
        Prestataire prestataire = logInService.prestataireLogIN();
        if (prestataire == null) {
            System.out.println("Échec de l'authentification Prestataire.");
            return;
        }

        int choix;
        do {
            System.out.println("------------------------------------------------------");
            System.out.println("--- ESPACE PRESTATAIRE : " + prestataire.getNomEntreprise() + " ---");
            System.out.println("1- Gérer les clients\n2- Gérer vos factures\n3- Statistiques\n4- générer le fichier excel facture par prestatire \n5 Retour");
            System.out.println("------------------------------------------------------");
            choix = ValidationDonnees.validateInts("votre choix");
            switch (choix) {
                case 1 -> gererClient();
                case 2 -> facturesMenu(prestataire);
                case 3 -> {
                    Statistiques.afficherStatistiquesGlobales();
                    Statistiques.afficherHistoriqueFinancier();
                }
                case 4 -> Excel.ExcelPrestatire.afficherFacturePrestatire();
                case 5 -> System.out.println("Retour au menu principal...");
                default -> System.out.println("Entrez un nombre entre 1 et 5");
            }
        } while (choix != 5);
    }

    public void clientMenu() {
        Client client = logInService.clientLogIn();
        if (client == null) {
            System.out.println("Échec de l'authentification Client.");
            return;
        }

        int choix;
        do {
            System.out.println("-------------------------------------------------------");
            System.out.println("--- ESPACE CLIENT : " + client.getNom() + " ---");
            System.out.println("1 - Lister vos factures");
            System.out.println("2 - Enregistrer un paiement");
            System.out.println("3 - Lister vos paiements");
            System.out.println("4 - Retour");
            System.out.println("-------------------------------------------------------");

            choix = ValidationDonnees.validateInts("votre choix");
            switch (choix) {
                case 1 -> new FactureService().listerFactureParClient(client);
                case 2 -> paimentService.enregistrerPaiement(client);
                case 3 -> paimentService.listerPaiement(client);
                case 4 -> System.out.println("Retour au menu principal...");
                default -> System.out.println("Choix invalide !");
            }
        } while (choix != 4);
    }

    public void facturesMenu(Prestataire prestataire) {
        int choix;
        do {
            System.out.println("-------------------------------------------------------");
            System.out.println("--- GESTION DES FACTURES ---");
            System.out.println("1- Ajouter facture\n2- Modifier facture\n3- Supprimer facture\n4- Lister vos factures\n5- Filtrer par statut\n6- Retour");
            System.out.println("-------------------------------------------------------");

            choix = ValidationDonnees.validateInts("votre choix");
            switch (choix) {
                case 1 -> factureService.ajouterFacture(prestataire);
                case 2 -> factureService.modifierFacture(prestataire);
                case 3 -> factureService.supprimerFacture(prestataire);
                case 4 -> factureService.listerFacture(prestataire);
                case 5 -> factureService.filterParStatus(prestataire);
                case 6 -> System.out.println("Retour au menu prestataire...");
                default -> System.out.println("Entrez un nombre entre 1 et 6");
            }
        } while (choix != 6);
    }

    public void gererClient() {
        int choix;
        do {
            System.out.println("-------------------------------------------------------");
            System.out.println("--- GESTION DES CLIENTS ---");
            System.out.println("1- Ajouter client\n2- Modifier client\n3- Supprimer client\n4- Lister clients\n5- Chercher client\n6- Retour");
            System.out.println("-------------------------------------------------------");

            choix = ValidationDonnees.validateInts("votre choix");
            switch (choix) {
                case 1 -> clientService.ajouterClient();
                case 2 -> clientService.modifierClient();
                case 3 -> clientService.supprimerClient();
                case 4 -> clientService.listerClient();
                case 5 -> clientService.chercherClient();
                case 6 -> System.out.println("Retour...");
            }
        } while (choix != 6);
    }

    public void gererPrestataire() {
        int choix;
        do {
            System.out.println("-------------------------------------------------------");
            System.out.println("--- GESTION DES PRESTATAIRES ---");
            System.out.println("1- Ajouter prestataire\n2- Modifier prestataire\n3- Supprimer prestataire\n4- Lister prestataires\n5- Chercher prestataire\n6- Retour");
            System.out.println("-------------------------------------------------------");

            choix = ValidationDonnees.validateInts("votre choix");
            switch (choix) {
                case 1 -> prestataireService.ajouterPrestatire();
                case 2 -> prestataireService.modifierPrestataire();
                case 3 -> prestataireService.supprimerPrestataire();
                case 4 -> prestataireService.listerPrestataire();
                case 5 -> prestataireService.chercherPrestatire();
                case 6 -> System.out.println("Retour...");
            }
        } while (choix != 6);
    }
}