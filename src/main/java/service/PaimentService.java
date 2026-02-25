package service;

import dao.FactureDAO;
import dao.PaimentDAO;
import model.Client;
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

    public void enregistrerPaiement(Client client) {
        List<Facture> factures = getFacturesDB().stream().filter(element -> element.getClient().getId() == client.getId()).toList();
        List<Facture> impayees = factures.stream().filter(f ->!f.getStatut()).toList();

        if (impayees.isEmpty()) {
            System.out.println("Aucune facture impayée.");
            return;
        }
        for (Facture f : impayees) {
            System.out.println("ID: " + f.getId() + " | Num: " + f.getNumero() + " | Montant: " + f.getMontant());
        }
        int id = ValidationDonnees.validateInts("ID facture:");
        double montantSaisi = ValidationDonnees.validateFloats("Entrez le montant à payer: ");
        System.out.println("Choisissez le mode de paiement :");
        System.out.println("1- Espèces | 2- Carte | 3- Virement");
        int choixMode = ValidationDonnees.validateInts("Choix:");
        String mode = switch (choixMode) {
            case 1 -> "Espèces";
            case 2 -> "Carte";
            case 3 -> "Virement";
            default -> "Autre";
        };

        Facture facture = impayees.stream().filter(f -> f.getId() == id).findFirst().orElse(null);
        if (facture == null) {
            System.out.println("ID invalide !");
            return;
        }

        double commission = calculerCommistion(montantSaisi);
        Paiement paiement = new Paiement(0, montantSaisi, LocalDateTime.now(), true, commission,mode ,facture);
        paiement.setModePaiement(mode);
        int idPaiement = ajouterPaimentDB(paiement, facture.getId());

        if (idPaiement > 0) {
            paiement.setId(idPaiement);
            PaiementPdf.genererRecuePaiement(paiement);
        } else {
            System.out.println("Erreur lors de l'enregistrement !");
            return;
        }
        CommissionFinPay com = new CommissionFinPay();
        com.setPourcentage(0.05);
        com.setMontantTotal(commission);
        com.setDatecommission(LocalDateTime.now());
        enregistrerCommissionDB(com, idPaiement);
        System.out.println("Paiement effectué avec succès.");
    }

    public double calculerCommistion(double montant){
        return montant * 0.02;
    }


    public void listerPaiement(Client client) {
        List<Paiement> paiements = getPaimentDB().stream().filter(element -> element.getFacture().getClient().getId() == client.getId() ).toList();
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

    public void supprimerPaiement(Client client) {
        listerPaiement(client);
        int id = ValidationDonnees.validateInts("l'ID du paiement à supprimer");
        supprimerPaimentDB(id);

    }

    public void modifierPaiement(Client client) {
        listerPaiement(client);
        int id = ValidationDonnees.validateInts("l'ID du paiement a modifier");
        String champ = ValidationDonnees.validateString("le nom du champ (ex: montant, statut)");
        String valeur = ValidationDonnees.validateString("la nouvelle valeur");
        modifierPaimentDB(id, champ, valeur);
    }

    public boolean updateFactureStatus(int id,Paiement paiement){
        if(paiement != null){
            if(paiement.getFacture().getMontant() <= paiement.getMontant()){
                PaimentDAO.updateFactureStatus(id);
                System.out.println("paid");
                return true;
            } else{
                System.out.println("pending");
                return false;
            }
        }
        System.out.println("pending");
        return false;
    }

}
