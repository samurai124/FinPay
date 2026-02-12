package org.example;

import service.FinPay;
import util.ValidationDonnees;

public class Main {
    static void main() {

        FinPay finPay = new FinPay();
        //prestataire
        finPay.ajouterPrestatire();
        finPay.listerPrestataire();
        finPay.modifierPrestataire();
        finPay.supprimerPrestataire();
        //client
        finPay.ajouterClient();
        finPay.modifierClient();
        finPay.listerClient();
        finPay.supprimerClient();
    }
}
