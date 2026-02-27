package dao;

import model.Facture;
import model.Paiement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.openxmlformats.schemas.drawingml.x2006.picture.PicDocument;
import service.PaimentService;

import java.util.List;

import static dao.FactureDAO.getFactureById;
import static dao.FactureDAO.getFacturesByStatut;
import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class PaimentDAOTest {
    PaimentService p ;
    Facture facture;
    Paiement paiement = null;

@BeforeEach
void initialisation(){
    p = new PaimentService();
}
    @Test
    @DisplayName("paiement total")
    void updateFactureStatus1() {
      facture = getFactureById(1);
        paiement = new Paiement(1000, facture);
     boolean test = p.updateFactureStatus(1, paiement);
   assertTrue(test);
    }

    @Test
    @DisplayName("paiement partiel")
    void updateFactureStatus2() {
        facture = getFactureById(2);
        paiement = new Paiement(100, facture);
        boolean test = p.updateFactureStatus(2,paiement);
        assertFalse(test);
    }

    @Test
    @DisplayName("aucun paiement")
    void updateFactureStatus3() {
        facture = getFactureById(3);
        boolean test = p.updateFactureStatus(3,paiement);
        assertFalse(test);
    }

    }


