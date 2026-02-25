package service;

import dao.FactureDAO;
import model.Facture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static dao.FactureDAO.getFactureById;
import static org.junit.jupiter.api.Assertions.*;

class FacturePDFTest {
    Facture facture;
@DisplayName("facture_123.pdf: ")
    @Test
    void genererNomFacture2() {
        facture = getFactureById(1);
         assertEquals("facture_1.pdf",FacturePDF.genererNomFacture(facture.getId()));
    }
}