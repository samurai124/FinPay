package com.example;

import org.junit.jupiter.api.*;// l'import de tous les annotations


import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
   @DisplayName("3 + 5 doit retourner 8")
    void testAdd() {
        assertEquals(8, calculator.add(3, 5));
    }

    @Test
   @DisplayName("10 - 6 doit retourner 4")
    void testSubtract() {
        assertEquals(4, calculator.subtract(10, 6));
    }

    @Test
    @DisplayName("Division par zéro doit lancer une exception")
    void testDivideByZero() {
        ArithmeticException ex = assertThrows(
                ArithmeticException.class,
                () -> calculator.divide(10, 0)
        );
        assertEquals("Division par zéro impossible", ex.getMessage());
    }

    @Test
    @DisplayName("10 / 2 doit retourner 5.0")
    void testDivide() {
        assertEquals(5.0, calculator.divide(10, 2));
    }
}