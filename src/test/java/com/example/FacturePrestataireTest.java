package com.example;

import dao.FactureDAO;
import model.FacturePrestataire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FacturePrestataireTest {
//    List<FacturePrestataire> facturePrestataires = List.of(
//            new FacturePrestataire(1,2,535.5),
//            new FacturePrestataire(2,1,1200),
//            new FacturePrestataire(3,1,3000)
//            );

//    @Test
//    void test(){
//        List<FacturePrestataire> testresults = FactureDAO.totalFacturesChaquePresatataire();
//        assertEquals(facturePrestataires.size(),testresults.size());
//        for (int i = 0 ; i < facturePrestataires.size() ; i++){
//            assertEquals(facturePrestataires.get(i).getIdf(),testresults.get(i).getIdf());
//            assertEquals(facturePrestataires.get(i).getNmbrFacture(),testresults.get(i).getNmbrFacture());
//            assertEquals(facturePrestataires.get(i).getSmontant(),testresults.get(i).getSmontant());
//        }
//    }

    @Test

    @DisplayName("Somme correcte des montants")
    void TestSommeMontant(){
        List<FacturePrestataire> resultats=FactureDAO.totalFacturesChaquePresatataire();
       assertEquals(535.5,resultats.get(0).getSmontant());

    }
    @Test
    @DisplayName("Cas liste vide")
    void TestListeVide(){
        List<FacturePrestataire> resultats=FactureDAO.totalFacturesChaquePresatataire();
//        assertTrue(resultats.isEmpty());
        assertFalse(resultats.isEmpty());
    }
    @Test
    @DisplayName(" Test plusieurs Facture")
    void TestPlsFactures(){
        List<FacturePrestataire> resultats=FactureDAO.totalFacturesChaquePresatataire();
        assertEquals(3,resultats.size());

    }

}
