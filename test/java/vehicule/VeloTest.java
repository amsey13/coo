package test.java.vehicule;
import main.java.vehicule.Vehicule;
import main.java.vehicule.Velo;
import main.java.visitor.Visiteur;
import org.junit.jupiter.api.Test;                     
import static org.junit.jupiter.api.Assertions.*;

public class VeloTest {  

    @Test
    public void testDisponibiliteInitiale() {  
        Velo v = new Velo("Vélo classique", 1.0);
        assertTrue(v.estDisponible(), "Le vélo doit être disponible à la création");
        assertEquals("Vélo classique", v.getDescription());
        assertEquals(1.0, v.getCout());
    }

    @Test
    public void testVol() {
        Velo v = new Velo("Vélo classique", 1.0);
        v.signalerVol();
        assertFalse(v.estDisponible(), "Le vélo volé ne doit plus être disponible");
    }

    @Test
    public void testHorsService() {
        Velo v = new Velo("Vélo classique", 1.0);
        v.devenirHorsService();
        assertFalse(v.estDisponible(), "Le vélo hors-service ne doit plus être disponible");
    }

    @Test
    public void testVisitor() {
        Velo v = new Velo("Vélo classique", 1.0);

        class VisitorTest implements Visiteur {
            boolean visited = false;

            @Override
            public void visiterVehicule(Vehicule vehicule) {
                visited = true;
            }
        }

        VisitorTest visitor = new VisitorTest();
        v.accept(visitor);

        assertTrue(visitor.visited, "Le visiteur doit avoir visité le vélo");
    }

    


}
