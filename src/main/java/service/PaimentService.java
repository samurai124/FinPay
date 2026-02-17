package service;

import model.CommissionFinPay;
import model.Facture;
import model.Paiement;
import util.DBconnection;
import util.ValidationDonnees;

import java.time.LocalDateTime;
import java.util.List;

import static dao.CommissionFinPayDAO.enregistrerCommissionDB;
import static dao.CommissionFinPayDAO.getFacturesDB;
import static dao.PaimentDAO.*;

public class PaimentService {
    private ClientService  client = new ClientService();

    public void enregistrerPaiement() {
        List<Facture> factures =getFacturesDB();

        List<Facture> impayees = factures.stream().filter(f ->!f.getStatut()).toList();

        if (impayees.isEmpty()) {
            System.out.println("Aucune facture impayée.");
            return;
        }
        for (Facture f : impayees) {
            System.out.println("ID: " + f.getId() + " | Num: " + f.getNumero() + " | Montant: " + f.getMontant());
        }
        int id = ValidationDonnees.validateInts("ID facture:");
        double mantant=ValidationDonnees.validateFloats("Enter le montat a payee: ");
        Facture facture = impayees.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
        if (facture == null) {
            System.out.println("ID invalide !");
            return;
        }
        double montantimpyee=facture.getMontant() - mantant;
        double commission = facture.getMontant() * 0.02;
        Paiement paiement = new Paiement(0, montantimpyee, LocalDateTime.now(), true, commission, facture);
        int idPaiement = ajouterPaimentDB(paiement, facture.getId());

        if (idPaiement > 0) {
            paiement.setId(idPaiement);
            PaiementPdf.genererRecuePaiement(paiement);

        } else {
            System.out.println("Erreur lors de l'enregistrement du paiement !");
            return;
        }
        CommissionFinPay com = new CommissionFinPay();
        com.setPourcentage(0.05);
        com.setMontantTotal(commission);
        com.setDatecommission(LocalDateTime.now());
        enregistrerCommissionDB(com, idPaiement);
        System.out.println("Paiement effectué avec succès.");
    }

    // function litser paiment
    public void listerPaiement() {
        List<Paiement> paiements = getPaimentDB();
        if (paiements.isEmpty()) {
            System.out.println("Aucune paiment trouvé dans base de donnée");
            return;
        }
        System.out.println("_____________________________________________________________________________");
        System.out.printf("|ID| Montant | Date | Statut | Commission |\n");
        System.out.println("_____________________________________________________________________________");
        for (Paiement p : paiements) {
            System.out.println("|"+ p.getId() + " |" + p.getMontant() + " | " + p.getDatePaiement() + " | " + p.isStatut()  +" | "+ p.getMontantCommision());
        }
        System.out.println("_____________________________________________________________________________");

    }
    // function supprimer paiement
    public void supprimerPaiement() {
        listerPaiement();
        int id = ValidationDonnees.validateInts("l'ID du paiement à supprimer");
        supprimerPaimentDB(id);

    }

    // function modifier Paiement
    public void modifierPaiement() {
        listerPaiement();
        int id = ValidationDonnees.validateInts("l'ID du paiement a modifier");
        String champ = ValidationDonnees.validateString("le nom du champ (ex: montant, statut)");
        String valeur = ValidationDonnees.validateString("la nouvelle valeur");
        modifierPaimentDB(id, champ, valeur);
    }


}
