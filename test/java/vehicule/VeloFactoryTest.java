package test.java.vehicule;

import main.java.factory.VeloFactory;
import main.java.vehicule.Vehicule;
import main.java.vehicule.Velo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestVeloFactory {

    @Test
    void testVeloNonNull() {
        VeloFactory factory = new VeloFactory();
        Vehicule v = factory.creerVehicule(1, "Velo", 200.0);
        assertNotNull(v, "La factory ne doit pas retourner null");
    }

    @Test
    void testVeloType() {
        VeloFactory factory = new VeloFactory();
        Vehicule v = factory.creerVehicule(1, "Velo", 200.0);
        assertTrue(v instanceof Velo, "La factory doit créer un objet de type Velo");
    }

    @Test
    void testVeloDescription() {
        VeloFactory factory = new VeloFactory();
        Vehicule v = factory.creerVehicule(1, "Velo", 200.0);
        assertEquals("Velo 1", v.getDescription(), "La description doit être correctement initialisée");
    }

    @Test
    void testVeloCout() {
        VeloFactory factory = new VeloFactory();
        Vehicule v = factory.creerVehicule(1, "Velo", 200.0);
        assertEquals(200.0, v.getCout(), "Le coût doit être correctement initialisé");
    }
}
