package service;

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













}
