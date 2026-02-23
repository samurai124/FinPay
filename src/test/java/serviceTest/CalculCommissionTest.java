package serviceTest;

import org.junit.jupiter.api.Test;
import service.ComisionFinPayService;

import static org.junit.jupiter.api.Assertions.*;

public class CalculCommissionTest {
    ComisionFinPayService comisionFinPayService = new ComisionFinPayService();
    @Test
    void CalculCommissionTest() {

        double resultat = comisionFinPayService.calculerCommission(0);

        assertEquals(0, resultat);
    }
}
