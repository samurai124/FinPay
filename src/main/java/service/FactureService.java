package service;

import model.Client;
import model.Facture;
import model.Prestataire;
import util.DBconnection;
import util.ValidationDonnees;

import java.util.ArrayList;
import java.util.List;
import service.*;

import static dao.ClientDAO.getClientById;
import static dao.ClientDAO.supprimerClientDB;
import static dao.CommissionFinPayDAO.getFacturesDB;
import static dao.FactureDAO.*;
import static dao.PrestataireDAO.getPrestataireById;

public class FactureService {

    private ClientService  client = new ClientService();
    private PrestataireService  prestataire = new PrestataireService();
    private PaimentService paiment = new PaimentService();
    private ComisionFinPayService comision = new ComisionFinPayService();



    public void ajouterFacture(Prestataire prestataire) {
        String numero = ValidationDonnees.validateString("le numéro de la facture");
        float montant = ValidationDonnees.validateFloats("montant de la facture");
        client.listerClient();
        int idClient = ValidationDonnees.validateInts("id de client");
        Facture facture = new Facture(numero, montant, false);
        ajouterFactureDB(facture, idClient, prestataire.getId());
    }

    public void listerFacture(Prestataire prestataire) {
        List<Facture> factures = getFacturesDB().stream().filter(element -> element.getPrestataire().getId() == prestataire.getId()).toList();
        if (factures.isEmpty()) {
            System.out.println("Aucune facture trouvé dans base de donnée");
            return;
        }
        System.out.println(
                "________________________________________________________________________________________________________________");
        System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                "ID", "Numero", "Montant", "statut", "nom client", "nom prestataire");
        System.out.println(
                "________________________________________________________________________________________________________________");
        factures.forEach(f -> System.out.printf("| %-15d | %-15s | %-15.2f | %-15b | %-15s | %-15s |\n",
                f.getId(), f.getNumero(), f.getMontant(), f.getStatut(), f.getClient().getNom(),
                f.getPrestataire().getNomEntreprise()));
        System.out.println(
                "________________________________________________________________________________________________________________");
    }

    // fonction pour modifier un factutre
    public int chengerClientFacture() {
        client.listerClient();
        int id = ValidationDonnees.validateInts("le client id");
        if (getClientById(id) == null) {
            System.out.println("Ce client n'exist pas dans notre base");
            return 0;
        }
        return id;
    }

    public int chengerPrestataireFacture() {
        prestataire.listerPrestataire();
        int id = ValidationDonnees.validateInts("le Prestataire id");
        if (getPrestataireById(id) == null) {
            System.out.println("Ce client n'exist pas dans notre base");
            return 0;
        }
        return id;
    }

    public void afficherFacture(Facture facture) {
        System.out.println("_________________________________________________________________________________________");
        System.out.println("|                       tu ne peux pas modifier l’id                          |");
        System.out.println("_________________________________________________________________________________________");
        System.out.printf("| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |\n",
                "ID", "Numero", "Montant", "status", "client", "prestataire");
        System.out.println("_________________________________________________________________________________________");
        System.out.printf("| %-10d | %-10s | %-10.2f | %-10b | %-10s | %-10s |\n",
                facture.getId(), facture.getNumero(), facture.getMontant(), facture.getStatut(),
                facture.getClient().getNom(), facture.getPrestataire().getNomEntreprise());
        System.out.println("_________________________________________________________________________________________");
    }

    public void afficherListeFactures(List<Facture> factures) {
        if (factures == null || factures.isEmpty()) {
            System.out.println("La liste des factures est vide.");
            return;
        }
        System.out.println("_________________________________________________________________________________________");
        System.out.printf("| %-5s | %-12s | %-10s | %-8s | %-15s | %-15s |\n",
                "ID", "Numero", "Montant", "Status", "Client", "Prestataire");
        System.out.println("|-------|--------------|------------|----------|-----------------|-----------------|");
        for (Facture f : factures) {
            System.out.printf("| %-5d | %-12s | %-10.2f | %-8b | %-15s | %-15s |\n",
                    f.getId(), f.getNumero(), f.getMontant(), f.getStatut(), f.getClient().getNom(),
                    f.getPrestataire().getNomEntreprise());
        }
        System.out.println("_________________________________________________________________________________________");
    }

    public void modifierFacture(Prestataire prestataire) {
        listerFacture(prestataire);
        int id = ValidationDonnees.validateInts("l'id du facture tu veux modifier ");
        Facture facture = getFactureById(id);
        if (facture == null) {
            System.out.println("Facture avec l'id" + id + "n'exist pas");
            return;
        }
        afficherFacture(facture);
        String champ = ValidationDonnees.validateString("le champ tu veux modifier").toLowerCase();
        if (!champ.equals("numero") && !champ.equals("montant") && !champ.equals("status") && !champ.equals("client")
                && !champ.equals("prestataire")) {
            System.out.println("Champ invalide ! Choisissez entre: numero, montant, status,client,prestataire.");
            return;
        }
        if (champ.equals("client")) {
            int idClient = chengerClientFacture();
            if (idClient == 0) {
                return;
            }
            modifierFactureDB(id, "idclient", String.valueOf(idClient));
            return;
        }
        if (champ.equals("prestataire")) {
            int idPrestataire = chengerPrestataireFacture();
            if (idPrestataire == 0) {
                return;
            }
            modifierFactureDB(id, "idprestataire", String.valueOf(idPrestataire));
            return;
        }
        String valeur = ValidationDonnees.validateString("Entrez la nouvelle valeur : ").toLowerCase();
        modifierFactureDB(id, champ, valeur);
    }

    public void filterParStatus() {
        System.out.println("saisir 1 pour les  factures payée et 2 pour  les factures non payées");
        int choix = ValidationDonnees.validateInts("choix");
        List<Facture> factures = new ArrayList<>();
        if (choix == 1) {
            factures = getFacturesByStatut(true);
        } else if (choix == 2) {
            factures = getFacturesByStatut(false);
        } else {
            System.out.println("choix invalide vous devez entrez 1 ou 2");
            return;
        }
        if (factures.isEmpty()) {
            System.out.println("aucune facture trouvée");
            return;
        }
        afficherListeFactures(factures);
    }

    // function pour supprimer une facture
    public void supprimerFacture(Prestataire prestataire) {
        listerFacture(prestataire);
        int id = ValidationDonnees.validateInts("facture id tu veux supprimer ");
        Facture facture = getFactureById(id);
        if (facture == null) {
            System.out.println("Le client avec l'id " + id + " n'exist pas");
            return;
        }
        supprimerClientDB(id);
    }


    public static void listerFactureParClient(Client client) {
        List<Facture> factures = getFacturesDB().stream().filter(element -> element.getClient().getId() == client.getId()).toList();
        if (factures.isEmpty()) {
            System.out.println("Aucune facture trouvé dans base de donnée");
            return;
        }
        System.out.println(
                "________________________________________________________________________________________________________________");
        System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                "ID", "Numero", "Montant", "statut", "nom client", "nom prestataire");
        System.out.println(
                "________________________________________________________________________________________________________________");
        factures.forEach(f -> System.out.printf("| %-15d | %-15s | %-15.2f | %-15b | %-15s | %-15s |\n",
                f.getId(), f.getNumero(), f.getMontant(), f.getStatut(), f.getClient().getNom(),
                f.getPrestataire().getNomEntreprise()));
        System.out.println(
                "________________________________________________________________________________________________________________");
    }


}
