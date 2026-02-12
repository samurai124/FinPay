package service;

import model.Client;
import model.Prestataire;
import util.DBconnection;
import util.ValidationDonnees;

import java.util.List;

public class FinPay {

    // fonction pour ajouter un nouveau prestataire
    public void ajouterPrestatire(){
        String nomEntreprise = ValidationDonnees.validateString("nom d'Entreprise");
        String email = ValidationDonnees.validateString("l'email");
        Prestataire prestataire = new Prestataire(nomEntreprise,email);
        DBconnection.ajouterPrestatireDB(prestataire);
    }

    // function pour lister tous les prestatire
    public void listerPrestataire(){
        List<Prestataire> prestataires = DBconnection.getPreatataireDB();
        if (prestataires.isEmpty()){
            System.out.println("Aucun prestataire trouvé dans la base de données.");
            return;
        }
        System.out.println("________________________________________________________");
        System.out.printf("| %-15s | %-15s | %-15s |\n",
                "ID","Entreprise","Email"
                );
        System.out.println("________________________________________________________");
        prestataires.forEach(p->
                        System.out.printf("| %-15d | %-15s | %-15s |\n",
                                p.getId(),p.getNomEntreprise(),p.getEmail()
                                )
                );
        System.out.println("________________________________________________________");
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
        System.out.println("________________________________________________________");
        System.out.println("|            tu ne peux pas modifier l’id               |");
        System.out.println("________________________________________________________");
        System.out.printf("| %-15s | %-15s | %-15s |\n",
                "ID","Entreprise","Email"
        );
        System.out.println("________________________________________________________");
        System.out.printf("| %-15d | %-15s | %-15s |\n",
                prestataire.getId(),prestataire.getNomEntreprise(),prestataire.getEmail()
        );
        System.out.println("________________________________________________________");
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
        listerPrestataire();
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
                "ID","Entreprise"
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


}
