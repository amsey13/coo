package test.java.accessories;

import main.java.vehicule.Velo;
import main.java.vehicule.Vehicule;
import main.java.accessories.VehiculeDecorator;
import main.java.visitor.Visiteur;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class VehiculeDecoratorTest {

    // Petit décorateur factice pour tester uniquement la délégation
    static class FakeDecorator extends VehiculeDecorator {
        public FakeDecorator(Vehicule v) {
            super(v);
        }
    }

    @Test
    public void testDelegationDescription() {
        Vehicule v = new Velo("Vélo classique", 1.0);
        Vehicule d = new FakeDecorator(v);

        assertEquals(v.getDescription(), d.getDescription());
    }

    @Test
    public void testDelegationCout() {
        Vehicule v = new Velo("Vélo classique", 1.0);
        Vehicule d = new FakeDecorator(v);

        assertEquals(v.getCout(), d.getCout());
    }

    @Test
    public void testDelegationDisponibilite() {
        Vehicule v = new Velo("Vélo classique", 1.0);
        Vehicule d = new FakeDecorator(v);

        assertEquals(v.estDisponible(), d.estDisponible());
    }

    @Test
    public void testDelegationSignalerVol() {
        Vehicule v = new Velo("Vélo classique", 1.0);
        Vehicule d = new FakeDecorator(v);

        d.signalerVol();  
        assertFalse(v.estDisponible()); // c’est bien l’objet original qui change
    }

    @Test
    public void testDelegationHorsService() {
        Vehicule v = new Velo("Vélo classique", 1.0);
        Vehicule d = new FakeDecorator(v);

        d.devenirHorsService();
        assertFalse(v.estDisponible());
    }

    @Test
    public void testDelegationVisitor() {
        Vehicule v = new Velo("Vélo classique", 1.0);

        class TestVisitor implements Visiteur {
            boolean visited = false;
            public void visiterVehicule(Vehicule vehicule) {
                visited = true;
            }
        }

        TestVisitor visitor = new TestVisitor();
        Vehicule d = new FakeDecorator(v);

        d.accept(visitor);

        assertTrue(visitor.visited);
    }
}
