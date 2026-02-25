package service;

import model.Facture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaimentServiceTest {
    PaimentService paimentService;
    @BeforeEach
    void  initialisation(){
        paimentService= new PaimentService();
    }
    @DisplayName("Montant normal")
    @Test
    void calculerCommistion1() {

        double reslt = paimentService.calculerCommistion(1000);
        assertEquals(20,reslt);
    }
    @DisplayName("Montant = 0")
    @Test
    void calculerCommistion2() {

        double reslt = paimentService.calculerCommistion(0);
        assertEquals(0,reslt);
    }
    @DisplayName("Montant élevé")
    @Test
    void calculerCommistion3() {

        double reslt = paimentService.calculerCommistion(1000000000);
        assertEquals(2.0E7,reslt);
    }

}