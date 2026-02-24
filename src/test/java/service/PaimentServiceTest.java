package service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PaimentServiceTest {
    PaimentService ps;
@BeforeEach
void  initialisation(){
     ps = new PaimentService();
}
    @Test
    void calculerCommistion1() {

        double reslt = ps.calculerCommistion(1000);
        assertEquals(20,reslt);
    }
    @Test
    void calculerCommistion2() {

        double reslt = ps.calculerCommistion(0);
        assertEquals(0,reslt);
    }
    @Test
    void calculerCommistion3() {

        double reslt = ps.calculerCommistion(1000000000);
        assertEquals(2.0E7,reslt);
    }


}