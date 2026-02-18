package App;

import Statistiques.Statistiques;
import dao.FactureDAO;
import model.Prestataire;
import service.*;
import util.ValidationDonnees;
import java.util.Scanner;

import static dao.FactureDAO.getFacturesByPrestataire;
import static dao.PrestataireDAO.*;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static ClientService client = new ClientService();
    private static PrestataireService prestataire = new PrestataireService();
    private static PaimentService paiment = new PaimentService();
    private static ComisionFinPayService comision = new ComisionFinPayService();
    private static FactureService facture = new FactureService();
    private static Statistiques statistiques = new Statistiques();

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
                        Statistiques.afficherStatistiquesGlobales();
                        Statistiques.afficherHistoriqueFinancier();
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
                            Statistiques.afficherStatistiquesGlobales();
                            Statistiques.afficherHistoriqueFinancier();
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
                        case 1 -> paiment.enregistrerPaiement();
                        case 2 -> paiment.listerPaiement();
                        case 3 -> paiment.modifierPaiement();
                        case 4 -> paiment.supprimerPaiement();
                        case 5 -> statistiques.afficherStatistiquesFinPay();
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
                case 1 -> prestataire.ajouterPrestatire();
                case 2 -> prestataire.modifierPrestataire();
                case 3 -> prestataire.supprimerPrestataire();
                case 4 -> prestataire.listerPrestataire();
                case 5 -> prestataire.chercherPrestatire();
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
                case 1 -> client.ajouterClient();
                case 2 -> client.modifierClient();
                case 3 -> client.supprimerClient();
                case 4 -> client.listerClient();
                case 5 -> client.chercherClient();
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
                    "6- filter les factures par prestataire\n" +

                    "7- Retour");
            System.out.println("-------------------------------------------------------");

            c = entreChoix();
            switch (c) {
                case 1 -> facture.ajouterFacture();
                case 2 -> facture.modifierFacture();
                case 3 -> facture.supprimerFacture();
                case 4 -> facture.listerFacture();
                case 5 -> facture.filterParStatus();
                case 6 ->new FactureService().afficherFactureparPrestatire();
                case 7 -> System.out.println("Retour");
                default -> System.out.println("entrez un nombre entre 1 et 7");
            }
        } while (c != 7);
    }

    public static int chercher(String phrase) {
        System.out.print(phrase);
        return sc.nextInt();
    }
}
