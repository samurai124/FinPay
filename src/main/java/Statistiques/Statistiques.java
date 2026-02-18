package Statistiques;

import model.Facture;
import model.Paiement;

import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;

import static dao.CommissionFinPayDAO.calculerTotalCommssion;
import static dao.CommissionFinPayDAO.getFacturesDB;
import static dao.PaimentDAO.getPaimentDB;

public class Statistiques {

    public static void afficherStatistiquesGlobales() {
        List<Paiement> paiements = getPaimentDB();
        List<Facture> factures = getFacturesDB();

        double totalPaiements = paiements.stream()
                .mapToDouble(Paiement::getMontant)
                .sum();

        double totalCommissions = paiements.stream()
                .mapToDouble(Paiement::getMontantCommision)
                .sum();

        long facturesPayees = factures.stream()
                .filter(Facture::getStatut)
                .count();

        long facturesImpayees = factures.stream()
                .filter(f -> !f.getStatut())
                .count();

        System.out.println("_______________________________________________________");
        System.out.println("               STATISTIQUES GLOBALES                   ");
        System.out.println("_______________________________________________________");
        System.out.printf("| Total des Paiements    : %15.2f MAD |\n", totalPaiements);
        System.out.printf("| Total des Commissions  : %15.2f MAD |\n", totalCommissions);
        System.out.println("|-----------------------------------------------------|");
        System.out.printf("| Factures Payées        : %15d     |\n", facturesPayees);
        System.out.printf("| Factures Impayées      : %15d     |\n", facturesImpayees);
        System.out.println("_______________________________________________________");
    }

    public static void afficherHistoriqueFinancier() {
        List<Paiement> paiements = getPaimentDB();

        if (paiements.isEmpty()) {
            System.out.println("Aucun historique financier disponible.");
            return;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("__________________________________________________________________________________");
        System.out.println("                            HISTORIQUE FINANCIER                                  ");
        System.out.println("__________________________________________________________________________________");
        System.out.printf("| %-20s | %-15s | %-15s | %-15s |\n", "Date", "Type", "Montant", "Commission");
        System.out.println("__________________________________________________________________________________");

        paiements.stream()
                .sorted(Comparator.comparing(Paiement::getDatePaiement).reversed())
                .forEach(p -> {
                    System.out.printf("| %-20s | %-15s | %-15.2f | %-15.2f |\n",
                            p.getDatePaiement().format(formatter),
                            "Paiement",
                            p.getMontant(),
                            p.getMontantCommision());
                });
        System.out.println("__________________________________________________________________________________");
    }

    public static void afficherStatistiquesFinPay() {
        double total = calculerTotalCommssion();
        System.out.println("---------------------------------------------------------");
        System.out.println("********* RAPPORT FINANCIER DE LA FINPAY ************");
        System.out.println("---------------------------------------------------------");
        System.out.println(" Total des commissions perçues :"+ total+ "MAD\n");
        System.out.println("----------------------------------------------------------");
    }




}
