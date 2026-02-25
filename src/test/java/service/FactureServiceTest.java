package service;

import model.Facture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static dao.FactureDAO.getFacturesByPrestataire;
import static org.junit.jupiter.api.Assertions.*;

class FactureServiceTest {
    FactureService f ;

  @BeforeEach
  void inti(){
      f = new FactureService();
  }
    @Test
    void calculersomme1() {
        List<Facture> factures = getFacturesByPrestataire(1);
        assertEquals(535.5,f.calculersomme(factures));
    }
    @Test
    void calculersomme2() {
        List<Facture> factures = getFacturesByPrestataire(4);
        assertEquals(0,f.calculersomme(factures));
    }

    @Test
    void calculersomme3() {
        List<Facture> factures = getFacturesByPrestataire(3);
        assertEquals( 5950,f.calculersomme(factures));
    }

}