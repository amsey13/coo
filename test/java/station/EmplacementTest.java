package main.java.station;

import main.java.vehicule.Velo;
import main.java.vehicule.Vehicule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmplacementTest {

    @Test
    void testEstLibre() {
        Emplacement e = new Emplacement(1);
        assertTrue(e.estLibre());
    }

    @Test
    void testDeposerEtRetirerVehicule() {
        Emplacement e = new Emplacement(1);
        Vehicule v = new Velo("VeloTest", 100.0);

        e.deposer(v);
        assertFalse(e.estLibre());
        assertEquals(v, e.getVehicule());

        Vehicule retiré = e.retirer();
        assertEquals(v, retiré);
        assertTrue(e.estLibre());
    }

    @Test
    void testToursSeul() {
        Emplacement e = new Emplacement(1);
        assertEquals(0, e.getToursSeul());

        e.incrementerToursSeul();
        assertEquals(1, e.getToursSeul());

        e.resetToursSeul();
        assertEquals(0, e.getToursSeul());
    }
}
