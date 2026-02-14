package org.example;

import model.Client;
import model.Prestataire;
import service.FinPay;
import util.DBconnection;
import util.ValidationDonnees;

import java.util.Scanner;

import static util.DBconnection.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static Scanner sc = new Scanner(System.in);
    static FinPay finPay = new FinPay();

    static void main() {

        int choix;
        do {
            System.out.println("------------------------------------------------------");
            System.out.println("-----------------------MENU---------------------------");
            System.out.println("-------------------------------------------------------");
            System.out.println("1 - ADMIN \n" +
                    "2 - PRESTATAIRE \n" +
                    "3 - CLIENT\n" +
                    "4 - QUITTER");

            choix = entreChoix();

            if (choix == 1) {
                System.out.println("------------------------------------------------------");
                System.out.println("1- Gérer les prestataires\n" +
                        "2- Gérer les clients\n" +
                        "3- Statistiques\n" +
                        "4- Retour");
                System.out.println("------------------------------------------------------");
                int c = entreChoix();
                switch (c) {
                    case 1 -> fonctionsPrestataire();
                    case 2 -> fonctionsClient();
                    case 3 -> {
                        util.Statistiques.afficherStatistiquesGlobales();
                        util.Statistiques.afficherHistoriqueFinancier();
                    }
                    case 4 -> System.out.println("Retour");
                    default -> System.out.println("entrez un nombre entre 1 et 4");
                }
            } else if (choix == 2) {
                Prestataire pr = getPrestataireById(ValidationDonnees.validateInts("votre id  prestataire :"));
                if (pr != null) {
                    System.out.println("------------------------------------------------------");
                    System.out.println("1- Gérer les clients\n" +
                            "2- Gérer les factures \n" +
                            "3- Statistiques \n" +
                            "4- Retour");
                    System.out.println("------------------------------------------------------");
                    int c = entreChoix();
                    switch (c) {
                        case 1 -> fonctionsClient();
                        case 2 -> fonctionsFacture();
                        case 3 -> {
                            util.Statistiques.afficherStatistiquesGlobales();
                            util.Statistiques.afficherHistoriqueFinancier();
                        }
                        case 4 -> System.out.println("Retour");
                        default -> System.out.println("entrez un nombre entre 1 et 4 ");
                    }
                } else {
                    System.out.println("ce prestataire n'existe pas");
                }
            } else if (choix == 3) {
                int c;
                do {
                    System.out.println("-------------------------------------------------------");
                    System.out.println("1 - enregistrer paiement: ");
                    System.out.println("2 - Lister paiements; ");
                    System.out.println("3 - modifier Paiemen: ");
                    System.out.println("4 - supprimer Paiemen: ");
                    System.out.println("5 - Total commissions FinPay: ");
                    System.out.println("0 - Retour");
                    System.out.println("-------------------------------------------------------");

                    c = entreChoix();

                    switch (c) {
                        case 1 -> finPay.enregistrerPaiement();
                        case 2 -> finPay.listerPaiement();
                        case 3 -> finPay.modifierPaiement();
                        case 4 -> finPay.supprimerPaiement();
                        case 5 -> finPay.afficherStatistiquesFinPay();
                        case 0 -> System.out.println("Retour...");
                        default -> System.out.println("Choix invalide !");
                    }

                } while (c != 0);
            }

        } while (choix != 4);
    }

    public static int entreChoix() {
        System.out.print("entrez votre choix: ");
        return sc.nextInt();
    }

    public static void fonctionsPrestataire() {
        int c;
        do {
            System.out.println("-------------------------------------------------------");
            System.out.println("1 - Ajouter prestataire\n" +
                    "2- Modifier prestataire\n" +
                    "3- Supprimer prestataire\n" +
                    "4- Lister prestataire\n" +
                    "5- Chercher prestataire\n" +
                    "6- Retour");
            System.out.println("-------------------------------------------------------");

            c = entreChoix();
            switch (c) {
                case 1 -> finPay.ajouterPrestatire();
                case 2 -> finPay.modifierPrestataire();
                case 3 -> finPay.supprimerPrestataire();
                case 4 -> finPay.listerPrestataire();
                case 5 -> finPay.chercherPrestatire();
                case 6 -> System.out.println("Retour");
                default -> System.out.println("entre un nombre entre 1 et 6");
            }
        } while (c != 6);
    }

    public static void fonctionsClient() {
        int c;
        do {
            System.out.println("-------------------------------------------------------");
            System.out.println("1- Ajouter client\n" +
                    "2- Modifier client\n" +
                    "3- Supprimer client\n" +
                    "4- Lister clients\n" +
                    "5- Chercher client\n" +
                    "6- Retour");
            System.out.println("-------------------------------------------------------");

            c = entreChoix();
            switch (c) {
                case 1 -> finPay.ajouterClient();
                case 2 -> finPay.modifierClient();
                case 3 -> finPay.supprimerClient();
                case 4 -> finPay.listerClient();
                case 5 -> finPay.chercherClient();
                case 6 -> System.out.println("Retour");
                default -> System.out.println("entrez un nombre entre 1 et 6");
            }
        } while (c != 6);
    }

    public static void fonctionsFacture() {
        int c;
        do {
            System.out.println("-------------------------------------------------------");
            System.out.println("1- Ajouter facture\n" +
                    "2- Modifier facture\n" +
                    "3- Supprimer facture\n" +
                    "4- Lister factures \n" +
                    "5- filter les factures par status\n" +
                    "6- Retour");
            System.out.println("-------------------------------------------------------");

            c = entreChoix();
            switch (c) {
                case 1 -> finPay.ajouterFacture();
                case 2 -> finPay.modifierFacture();
                case 3 -> finPay.supprimerFacture();
                case 4 -> finPay.listerFacture();
                case 5 -> finPay.filterParStatus();
                case 6 -> System.out.println("Retour");
                default -> System.out.println("entrez un nombre entre 1 et 6");
            }
        } while (c != 6);
    }

    public static int chercher(String phrase) {
        System.out.print(phrase);
        return sc.nextInt();
    }
}
