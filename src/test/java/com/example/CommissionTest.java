package com.example;
import org.junit.jupiter.api.*;
import service.PaimentService;

import static org.junit.jupiter.api.Assertions.*;



public class CommissionTest {
    private PaimentService paimentservice;
    @BeforeEach
    void setUp(){
        paimentservice = new PaimentService();

    }
    @Test
    @DisplayName("Test 1: montant normal")
    void MontantNormal(){
          double res= paimentservice.calculerCommistion(1000);
          assertEquals(20,res);


    }
    @Test
    @DisplayName("Test 2:montant égale à zéro")
        void MontantZero() {
            double res = paimentservice.calculerCommistion(0.0);
            assertEquals(0.0, res);
        }




    @Test
    @DisplayName("Test 3:Montant élevé")
    void MontantEleve(){
        double res=paimentservice.calculerCommistion( 1000000);
        assertEquals(20000.0,res);

    }



    }






