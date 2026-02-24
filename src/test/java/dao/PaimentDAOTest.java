package dao;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.PaimentService;

import static org.junit.jupiter.api.Assertions.*;

class PaimentDAOTest {
    PaimentDAO p ;
@BeforeEach
void initialisation(){
    p = new PaimentDAO();
}
    @Test
    void updateFactureStatus1() {
     p.updateFactureStatus(1,true);
     assertEquals(500,);

    }
}