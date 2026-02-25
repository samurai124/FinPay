package com.example;

import Excel.ExcelAdmin;
import dao.FactureDAO;
import dao.PaimentDAO;
import model.Facture;
import model.Paiement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.FacturePDF;
import service.PaiementPdf;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GenerationNom {
    private Facture facture;
    private Paiement paiement;

    @BeforeEach
    public void setup() {
        facture = FactureDAO.getFactureById(1);
        paiement = PaimentDAO.getPaimentDB().getFirst();
    }

    @Test
    @DisplayName("Test - 1 : Nom du fichier Facture PDF")
    public void generationNom1() {
        String nom = FacturePDF.facturepdf(facture);
        assertEquals("facture_FAC-2026-001.pdf", nom);
    }

    @Test
    @DisplayName("Test - 2 : Nom du fichier Reçu de Paiement PDF")
    public void generationNom2() {
        String nom = PaiementPdf.genererRecuePaiement(paiement);
        assertEquals("recu_paiement_1.pdf", nom);
    }

    @Test
    @DisplayName("Test - 3 : Nom du rapport Excel Admin")
    public void generationNom3() {
        String nom = ExcelAdmin.exelData();
        assertEquals("rapportGenerale02_2026.xls", nom);
    }
}