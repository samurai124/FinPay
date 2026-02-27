package service;

import dao.FactureDAO;
import dao.TestDAO;
import model.Client;
import model.Facture;
import model.Prestataire;
import util.ValidationDonnees;

import java.sql.PreparedStatement;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static dao.ClientDAO.getClientById;
import static dao.FactureDAO.*;
import static dao.PrestataireDAO.getPrestataireById;
import static dao.CommissionFinPayDAO.getFacturesDB;

public class FactureService {

   TestDAO testDAO;

    public FactureService(TestDAO testDAO) {
        this.testDAO = testDAO;
    }

    public FactureService() {
    }

    private ClientService clientService = new ClientService();
    private PrestataireService prestataireService = new PrestataireService();

    public void ajouterFacture(Prestataire prestataire) {
        String numero = ValidationDonnees.validateString("le numéro de la facture");
        float montant = ValidationDonnees.validateFloats("montant de la facture");

        clientService.listerClient();
        int idClient = ValidationDonnees.validateInts("id de client pour cette facture");

        if (getClientById(idClient) == null) {
            System.out.println("Erreur: Client introuvable.");
            return;
        }

        Facture facture = new Facture(numero, montant, false);
        ajouterFactureDB(facture, idClient, prestataire.getId());
        System.out.println("Facture '" + numero + "' ajoutée avec succès.");
    }

    public void listerFacture(Prestataire prestataire) {
        List<Facture> allFactures = getFacturesDB();

        List<Facture> mesFactures = allFactures.stream()
                .filter(f -> f.getPrestataire().getId() == prestataire.getId())
                .collect(Collectors.toList());

        if (mesFactures.isEmpty()) {
            System.out.println("Vous n'avez aucune facture enregistrée.");
            return;
        }

        System.out.println("\n--- LISTE DE VOS FACTURES (" + prestataire.getNomEntreprise() + ") ---");
        afficherListeFactures(mesFactures);
    }

    public void modifierFacture(Prestataire prestataire) {
        listerFacture(prestataire);
        int id = ValidationDonnees.validateInts("L'id de la facture à modifier");

        Facture facture = getFactureById(id);

        if (facture == null || facture.getPrestataire().getId() != prestataire.getId()) {
            System.out.println("Accès refusé: Vous ne pouvez modifier que vos propres factures.");
            return;
        }

        afficherFacture(facture);
        String champ = ValidationDonnees.validateString("Champ à modifier (numero/montant/status/client)").toLowerCase();

        if (champ.equals("client")) {
            int idClient = chengerClientFacture();
            if (idClient != 0) modifierFactureDB(id, "idclient", String.valueOf(idClient));
        } else if (champ.equals("prestataire")) {
            System.out.println("Action interdite: Une facture ne peut pas changer de prestataire.");
        } else {
            String valeur = ValidationDonnees.validateString("Entrez la nouvelle valeur");
            modifierFactureDB(id, champ, valeur);
        }
    }

    public void filterParStatus(Prestataire prestataire) {
        System.out.println("1: Payées | 2: Non-payées");
        int choix = ValidationDonnees.validateInts("choix");
        boolean status = (choix == 1);

        List<Facture> filtered = getFacturesByStatut(status).stream()
                .filter(f -> f.getPrestataire().getId() == prestataire.getId())
                .collect(Collectors.toList());

        if (filtered.isEmpty()) {
            System.out.println("Aucune facture trouvée pour ce statut.");
        } else {
            afficherListeFactures(filtered);
        }
    }

    public void supprimerFacture(Prestataire prestataire) {
        listerFacture(prestataire);
        int id = ValidationDonnees.validateInts("ID de la facture à supprimer");

        Facture facture = getFactureById(id);

        if (facture == null || facture.getPrestataire().getId() != prestataire.getId()) {
            System.out.println("Erreur: Facture introuvable ou vous n'avez pas le droit de la supprimer.");
            return;
        }

        supprimerFactureDB(id);
        System.out.println("Facture supprimée avec succès.");
    }

    public void afficherListeFactures(List<Facture> factures) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        System.out.println("______________________________________________________________________________________________________");
        System.out.printf("| %-5s | %-12s | %-10s | %-8s | %-15s | %-17s |\n",
                "ID", "Numero", "Montant", "Status", "Client", "Date Creation");
        System.out.println("|-------|--------------|------------|----------|-----------------|-------------------|");

        for (Facture f : factures) {
            String dateFormatted = (f.getDate() != null) ? f.getDate().format(formatter) : "N/A";
            System.out.printf("| %-5d | %-12s | %-10.2f | %-8b | %-15s | %-17s |\n",
                    f.getId(), f.getNumero(), f.getMontant(), f.getStatut(), f.getClient().getNom(), dateFormatted);
        }
        System.out.println("______________________________________________________________________________________________________");
    }

    private void afficherFacture(Facture f) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String dateFormatted = (f.getDate() != null) ? f.getDate().format(formatter) : "N/A";

        System.out.printf("[Facture ID: %d] No: %s | Montant: %.2f | Payé: %b | Client: %s | Date: %s\n",
                f.getId(), f.getNumero(), f.getMontant(), f.getStatut(), f.getClient().getNom(), dateFormatted);
    }

    public int chengerClientFacture() {
        clientService.listerClient();
        int id = ValidationDonnees.validateInts("Nouveau client ID");
        return (getClientById(id) != null) ? id : 0;
    }

    public void listerFactureParClient(Client client) {
        List<Facture> allFactures = getFacturesDB();
        List<Facture> clientFactures = allFactures.stream()
                .filter(f -> f.getClient().getId() == client.getId())
                .toList();
        if (clientFactures.isEmpty()) {
            System.out.println("Aucune facture trouvée pour le client : " + client.getNom());
            return;
        }
        System.out.println("\n--- VOS FACTURES (" + client.getNom() + ") ---");
        afficherListeFactures(clientFactures);
    }

    public double calculersomme(List<Facture> pres){
return  pres.stream()
        .mapToDouble(Facture::getMontant).sum();

    }

    public double calculertotal(int id){
        List<Facture> pres = testDAO.getFacturesByPrestataire(id);
        return  pres.stream()
                .mapToDouble(Facture::getMontant).sum();

    }


}