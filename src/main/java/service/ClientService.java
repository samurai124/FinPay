package service;

import model.Client;
import util.DBconnection;
import util.ValidationDonnees;

import java.util.List;

import static dao.ClientDAO.*;

public class ClientService {
    // fonction pour ajouter un client
    public void ajouterClient() {
        String nom = ValidationDonnees.validateString("nom du client");
        Client client = new Client(nom);
        ajouterClientDB(client);

    }

    // founction pour lister les clients
    public void listerClient() {
        List<Client> clients = getClientDB();
        if (clients.isEmpty()) {
            System.out.println("Aucun client trouvé dans la base de données.");
            return;
        }
        System.out.println("________________________________________________________");
        System.out.printf("| %-15s | %-15s |\n",
                "ID", "Nom");
        System.out.println("________________________________________________________");
        clients.forEach(c -> System.out.printf("| %-15d | %-15s |\n",
                c.getId(), c.getNom()));
        System.out.println("________________________________________________________");
    }

    // fonction pour modifier le client
    public void modifierClient() {
        listerClient();
        int id = ValidationDonnees.validateInts("client id tu veux modifier ");
        Client client = getClientDB().stream().filter(element -> element.getId() == id).findAny()
                .orElse(null);
        if (client == null) {
            System.out.println("Le client avec l'id " + id + " n'exist pas");
            return;
        }
        System.out.println("________________________________________________________");
        System.out.println("|            tu ne peux pas modifier l’id               |");
        System.out.println("________________________________________________________");
        System.out.printf("| %-15s | %-15s |\n",
                "ID", "Nom");
        System.out.println("________________________________________________________");
        System.out.printf("| %-15d | %-15s |\n",
                client.getId(), client.getNom());
        System.out.println("________________________________________________________");
        String nom = ValidationDonnees.validateString("le champ tu veux modifier");
        String nvNom = ValidationDonnees.validateString("la nouvelle valeur");
        midifierClientDB(id, nom, nvNom);

    }

    // fonction supprimer client
    public void supprimerClient() {
        listerClient();
        int id = ValidationDonnees.validateInts("client id tu veux supprimer ");
        Client client = getClientDB().stream().filter(element -> element.getId() == id).findAny()
                .orElse(null);
        if (client == null) {
            System.out.println("Le client avec l'id " + id + " n'exist pas");
            return;
        }
       supprimerClientDB(id);
    }

    // fonction puur chercher client a partire de son id
    public void chercherClient() {
        int id = ValidationDonnees.validateInts("l'id du client tu cherche");
        if (getClientById(id) == null) {
            System.out.println("Le prestataire avec l'id : " + id + "n'exist pas");
            return;
        }
        System.out.println("_______________________________");
        System.out.printf("| %-10s | %-10s |\n",
                "ID", "Nom");
        System.out.println("_______________________________");
        System.out.printf("| %-10d | %-10s |\n",
                getClientById(id).getId(), getClientById(id).getNom());
        System.out.println("_______________________________");
    }

}
