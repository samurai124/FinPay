package service;

import model.Paiement;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaiementPdfTest {

    @DisplayName("recu_456.pdf")
    @Test
    void genererRecuePaiement() {
       assertEquals("recu_12.pdf",PaiementPdf.genererNomRecu(12));

    }
}