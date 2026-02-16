package service;

import model.Prestataire;
import util.DBconnection;
import util.ValidationDonnees;

import java.util.List;

import static dao.PrestataireDAO.*;

public class PrestataireService {
    private ClientService  client = new ClientService();


    // fonction pour ajouter un nouveau prestataire
    public void ajouterPrestatire() {
        String nomEntreprise = ValidationDonnees.validateString("nom d'Entreprise");
        String email = ValidationDonnees.validateString("l'email");
        float solde = ValidationDonnees.validateFloats("solde de base");
        Prestataire prestataire = new Prestataire(nomEntreprise, email, solde);
        ajouterPrestatireDB(prestataire);
    }

    // function pour lister tous les prestatire
    public void listerPrestataire() {
        List<Prestataire> prestataires = getPreatataireDB();
        if (prestataires.isEmpty()) {
            System.out.println("Aucun prestataire trouvé dans la base de données.");
            return;
        }
        System.out.println("______________________________________________________________________________");
        System.out.printf("| %-15s | %-15s | %-15s | %-15s |\n",
                "ID", "Entreprise", "Email", "Solde");
        System.out.println("______________________________________________________________________________");
        prestataires.forEach(p -> System.out.printf("| %-15d | %-15s | %-15s | %-15.2f |\n",
                p.getId(), p.getNomEntreprise(), p.getEmail(), p.getSolde()));
        System.out.println("______________________________________________________________________________");
    }

    // fonction pour modifier un prestataire
    public void modifierPrestataire() {
        listerPrestataire();
        int id = ValidationDonnees.validateInts("prestataire id tu veux modifier ");
        Prestataire prestataire = getPreatataireDB().stream().filter(element -> element.getId() == id)
                .findAny().orElse(null);
        if (prestataire == null) {
            System.out.println("Le prestataire avec l'id" + id + "n'exist pas");
            return;
        }
        System.out.println("______________________________________________________________________________");
        System.out.println("|                 tu ne peux pas modifier l’id                              |");
        System.out.println("______________________________________________________________________________");
        System.out.printf("| %-15s | %-15s | %-15s | %-15s |\n",
                "ID", "nomEntreprise", "Email", "Solde");
        System.out.println("______________________________________________________________________________");
        System.out.printf("| %-15d | %-15s | %-15s | %-15.2f |\n",
                prestataire.getId(), prestataire.getNomEntreprise(), prestataire.getEmail(), prestataire.getSolde());
        System.out.println("______________________________________________________________________________");
        String champ = ValidationDonnees.validateString("le champ tu veux modifier");
        String valeur = ValidationDonnees.validateString("la nouvelle valeur");
        modifierPrestatireDB(id, champ, valeur);

    }

    // fonction pour supprimer un prestataire
    public void supprimerPrestataire() {
        listerPrestataire();
        int id = ValidationDonnees.validateInts("prestataire id tu veux supprimer ");
        Prestataire prestataire = getPreatataireDB().stream().filter(element -> element.getId() == id)
                .findAny().orElse(null);
        if (prestataire == null) {
            System.out.println("Le prestataire avec l'id" + id + "n'exist pas");
            return;
        }
        supprimerPrestatireDB(id);
    }

    // fonction pour chenrcher un prestataire a partire de son id
    public void chercherPrestatire() {
        int id = ValidationDonnees.validateInts("l'id du prestataire tu cherche");
        if (getPrestataireById(id) == null) {
            System.out.println("Le prestataire avec l'id : " + id + "n'exist pas");
            return;
        }
        System.out.println("______________________________________________________________________________");
        System.out.printf("| %-15s | %-15s | %-15s | %-15s |\n",
                "ID", "Entreprise", "Email", "Solde");
        System.out.println("______________________________________________________________________________");
        System.out.printf("| %-15d | %-15s | %-15s | %-15.2f |\n",
                getPrestataireById(id).getId(), getPrestataireById(id).getNomEntreprise(),
                getPrestataireById(id).getEmail(), getPrestataireById(id).getSolde());
        System.out.println("______________________________________________________________________________");
    }


}
