package service;

import dao.TestDAO;
import model.Facture;
import model.Paiement;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertTrue;
@ExtendWith(MockitoExtension.class)
public class FactureStatutTest {
Paiement paiement = null;
    @Mock
    TestDAO testDAO;

    @InjectMocks
    PaimentService paimentService;

    @Test
    void testpdateStatus() {

        Facture facture1 = new Facture(1, 200.00);
        paiement = new Paiement(200, facture1);
        boolean t = paimentService.testupdateFactureStatus(1, paiement);
        assertTrue(t);
    }
}
