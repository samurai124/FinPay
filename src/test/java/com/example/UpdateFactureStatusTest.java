package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import service.FactureService;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UpdateFactureStatusTest {
    private FactureService factureService;

    @BeforeEach
    void setUp() {
        factureService = new FactureService();
    }

    @Test
    @DisplayName("Test 2 – Mise à jour du statut facture 'PAID'")
    void update_facture_status_case1() {

        boolean result = factureService.updateFactureStatus(1, true);


        assertTrue(result);
    }


    @Test
    @DisplayName("Test 2 – Mise à jour du statut facture 'NOT PAID")
    void update_facture_status_case2() {

        boolean result = factureService.updateFactureStatus(1, false);

        assertTrue(result);
    }
}
