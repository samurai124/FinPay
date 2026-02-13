package service;

import model.Client;
import model.Facture;
import model.Prestataire;
import util.DBconnection;
import util.ValidationDonnees;

import java.util.List;

public class FinPay {

    // fonction pour ajouter un nouveau prestataire
    public void ajouterPrestatire(){
        String nomEntreprise = ValidationDonnees.validateString("nom d'Entreprise");
        String email = ValidationDonnees.validateString("l'email");
        float solde = ValidationDonnees.validateFloats("solde de base");
        Prestataire prestataire = new Prestataire(nomEntreprise,email,solde);
        DBconnection.ajouterPrestatireDB(prestataire);
    }

    // function pour lister tous les prestatire
    public void listerPrestataire(){
        List<Prestataire> prestataires = DBconnection.getPreatataireDB();
        if (prestataires.isEmpty()){
            System.out.println("Aucun prestataire trouvé dans la base de données.");
            return;
        }
        System.out.println("______________________________________________________________________________");
        System.out.printf("| %-15s | %-15s | %-15s | %-15s |\n",
                "ID","Entreprise","Email","Solde"
                );
        System.out.println("______________________________________________________________________________");
        prestataires.forEach(p->
                        System.out.printf("| %-15d | %-15s | %-15s | %-15.2f |\n",
                                p.getId(),p.getNomEntreprise(),p.getEmail(),p.getSolde()
                                )
                );
        System.out.println("______________________________________________________________________________");
    }

    // fonction pour modifier un prestataire
    public void modifierPrestataire(){
        listerPrestataire();
        int id = ValidationDonnees.validateInts("prestataire id tu veux modifier ");
        Prestataire prestataire = DBconnection.getPreatataireDB().stream().filter(element->element.getId() == id).findAny().orElse(null);
        if (prestataire == null){
            System.out.println("Le prestataire avec l'id"+id+"n'exist pas");
            return;
        }
        System.out.println("______________________________________________________________________________");
        System.out.println("|                 tu ne peux pas modifier l’id                              |");
        System.out.println("______________________________________________________________________________");
        System.out.printf("| %-15s | %-15s | %-15s | %-15s |\n",
                "ID","nomEntreprise","Email","Solde"
        );
        System.out.println("______________________________________________________________________________");
        System.out.printf("| %-15d | %-15s | %-15s | %-15.2f |\n",
                prestataire.getId(),prestataire.getNomEntreprise(),prestataire.getEmail(),prestataire.getSolde()
        );
        System.out.println("______________________________________________________________________________");
        String champ = ValidationDonnees.validateString("le champ tu veux modifier");
        String valeur = ValidationDonnees.validateString("la nouvelle valeur");
        DBconnection.modifierPrestatireDB(id,champ,valeur);

    }

    // fonction pour supprimer un prestataire
    public void supprimerPrestataire(){
        listerPrestataire();
        int id = ValidationDonnees.validateInts("prestataire id tu veux supprimer ");
        Prestataire prestataire = DBconnection.getPreatataireDB().stream().filter(element->element.getId() == id).findAny().orElse(null);
        if (prestataire == null){
            System.out.println("Le prestataire avec l'id"+id+"n'exist pas");
            return;
        }
        DBconnection.supprimerPrestatireDB(id);
    }


    // fonction pour ajouter un client
    public void ajouterClient(){
        String nom = ValidationDonnees.validateString("nom du client");
        Client client = new Client(nom);
        DBconnection.ajouterClientDB(client);

    }

    //founction pour lister les clients
    public void listerClient(){
        List<Client> clients = DBconnection.getClientDB();
        if (clients.isEmpty()){
            System.out.println("Aucun client trouvé dans la base de données.");
            return;
        }
        System.out.println("________________________________________________________");
        System.out.printf("| %-15s | %-15s |\n",
                "ID","Nom"
        );
        System.out.println("________________________________________________________");
        clients.forEach(c->
                System.out.printf("| %-15d | %-15s |\n",
                        c.getId(),c.getNom()
                )
        );
        System.out.println("________________________________________________________");
    }

    // fonction pour modifier le client
    public void modifierClient(){
        listerClient();
        int id = ValidationDonnees.validateInts("client id tu veux modifier ");
        Client client = DBconnection.getClientDB().stream().filter(element->element.getId() == id).findAny().orElse(null);
        if (client == null){
            System.out.println("Le client avec l'id "+id+" n'exist pas");
            return;
        }
        System.out.println("________________________________________________________");
        System.out.println("|            tu ne peux pas modifier l’id               |");
        System.out.println("________________________________________________________");
        System.out.printf("| %-15s | %-15s |\n",
                "ID","Nom"
        );
        System.out.println("________________________________________________________");
        System.out.printf("| %-15d | %-15s |\n",
                client.getId(),client.getNom()
        );
        System.out.println("________________________________________________________");
        String nom = ValidationDonnees.validateString("le champ tu veux modifier");
        String nvNom = ValidationDonnees.validateString("la nouvelle valeur");
        DBconnection.midifierClientDB(id,nom,nvNom);

    }

    //fonction supprimer client
    public void supprimerClient(){
        listerClient();
        int id = ValidationDonnees.validateInts("client id tu veux supprimer ");
        Client client = DBconnection.getClientDB().stream().filter(element->element.getId() == id).findAny().orElse(null);
        if (client == null){
            System.out.println("Le client avec l'id "+id+" n'exist pas");
            return;
        }
        DBconnection.supprimerClientDB(id);
    }

    public void ajouterFacture(){
        String numero = ValidationDonnees.validateString("le numéro de la facture");
        float montant = ValidationDonnees.validateFloats("montant de la facture");
        listerClient();
        int idClient=ValidationDonnees.validateInts("id de client");
        listerPrestataire();
        int idPrestataire=ValidationDonnees.validateInts("id de prestataire");
        Facture facture = new Facture(numero,montant,false);
        DBconnection.ajouterFactureDB(facture,idClient,idPrestataire);
    }

    public void listerFacture(){

            List<Facture> factures = DBconnection.getFacturesDB();
            if (factures.isEmpty()){
                System.out.println("Aucune facture trouvé dans base de donnée");
                return;
            }
            System.out.println("________________________________________________________________________________________________________________");
            System.out.printf("| %-15s | %-15s | %-15s | %-15s | %-15s | %-15s |\n",
                    "ID","Numero","Montant","statut","nom client","nom prestataire"
            );
            System.out.println("________________________________________________________________________________________________________________");
        factures.forEach(f->
                    System.out.printf("| %-15d | %-15s | %-15.2f | %-15b | %-15s | %-15s |\n",
                            f.getId(),f.getNumero(),f.getMontant(),f.getStatut(),f.getClient().getNom(),f.getPrestataire().getNomEntreprise()
                    )
            );
            System.out.println("________________________________________________________________________________________________________________");
        }



    // fonction pour modifier un factutre
    public int chengerClientFacture(){
        listerClient();
        int id = ValidationDonnees.validateInts("le client id");
        if (DBconnection.getClientById(id) == null){
            System.out.println("Ce client n'exist pas dans notre base");
            return 0;
        }
        return id;
    }

    public int chengerPrestataireFacture(){
        listerPrestataire();
        int id = ValidationDonnees.validateInts("le Prestataire id");
        if (DBconnection.getPrestataireById(id) == null){
            System.out.println("Ce client n'exist pas dans notre base");
            return 0;
        }
        return id;
    }
    public void afficherFacture(Facture facture){
        System.out.println();
        System.out.println("|                       tu ne peux pas modifier l’id                          |");
        System.out.println("__");
        System.out.printf("| %-10s | %-10s | %-10s | %-10s | %-10s | %-10s |\n",
                "ID", "Numero", "Montant", "status", "client", "prestataire"
        );
        System.out.println("__");
        System.out.printf("| %-10d | %-10s | %-10.2f | %-10b | %-10s | %-10s |\n",
                facture.getId(), facture.getNumero(), facture.getMontant(), facture.getStatut(), facture.getClient().getNom(), facture.getPrestataire().getNomEntreprise()
        );
        System.out.println("__");
    }

    public void modifierFacture() {
        listerFacture();
        int id = ValidationDonnees.validateInts("l'id du facture tu veux modifier ");
        Facture facture = DBconnection.getFactureById(id);
        if (facture == null){
            System.out.println("Facture avec l'id"+id+"n'exist pas");
            return;
        }
        afficherFacture(facture);
        String champ = ValidationDonnees.validateString("le champ tu veux modifier").toLowerCase();
        if (!champ.equals("numero") && !champ.equals("montant") && !champ.equals("status") && !champ.equals("client") && !champ.equals("prestataire")) {
            System.out.println("Champ invalide ! Choisissez entre: numero, montant, status,client,prestataire.");
            return;
        }
        if (champ.equals("client")){
            int idClient = chengerClientFacture();
            if (idClient == 0){
                return;
            }
            DBconnection.modifierFactureDB(id, "idclient", String.valueOf(idClient));
            return;
        }
        if (champ.equals("prestataire")){
            int idPrestataire = chengerPrestataireFacture();
            if (idPrestataire == 0){
                return;
            }
            DBconnection.modifierFactureDB(id, "idprestataire", String.valueOf(idPrestataire));
            return;
        }
        String valeur = ValidationDonnees.validateString("Entrez la nouvelle valeur : ").toLowerCase();
        DBconnection.modifierFactureDB(id, champ, valeur);
    }








    }













