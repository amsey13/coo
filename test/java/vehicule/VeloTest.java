package test.java.vehicule;
import main.java.vehicule.Velo;
import main.java.visitor.Visiteur;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


//Teste le comportement public du vélo, Disponibilité initiale, description, coût, vol, hors-service, visitor                      |


class VeloTest {

    @Test
    void testDisponibiliteInitiale() {
        Velo v = new Velo("Vélo classique", 1.0);
        assertTrue(v.estDisponible());
        assertEquals("Vélo classique", v.getDescription());
        assertEquals(1.0, v.getCout());
    }

    @Test
    void testVol() {
        Velo v = new Velo("Vélo classique", 1.0);
        v.signalerVol();
        assertFalse(v.estDisponible());
    }

    @Test
    void testHorsService() {
        Velo v = new Velo("Vélo classique", 1.0);
        v.devenirHorsService();
        assertFalse(v.estDisponible());
    }

    @Test
    void testVisitor() {
        Velo v = new Velo("Vélo classique", 1.0);
        class VisitorTest implements Visiteur {
            boolean visited = false;
            @Override
            public void visiterVehicule(Velo v) {
                visited = true;
            }
        }
        VisitorTest visitor = new VisitorTest();
        v.accept(visitor);
        assertTrue(visitor.visited);
    }
}
