package service;

import dao.FactureDAO;
import dao.TestDAO;
import model.Facture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static dao.FactureDAO.getFacturesByPrestataire;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
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


    // utilisant mock

@Mock
    TestDAO testDAO;
  @InjectMocks
    FactureService factureService;
  @Test
    void  calculertotal(){
      List<Facture> factures = List.of(
              new Facture(100.0),
              new Facture(200.0)
      );
      Mockito.when(testDAO.getFacturesByPrestataire(1)).thenReturn(factures);
      assertEquals(300,factureService.calculertotal(1));
  }
}