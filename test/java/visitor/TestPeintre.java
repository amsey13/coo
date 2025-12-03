package main.java.visitor;

import main.java.factory.VeloFactory;
import main.java.vehicule.Vehicule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestPeintre {

    @Test
    void testPeintreMetVeloHorsService() {
        VeloFactory factory = new VeloFactory();
        Vehicule v = factory.creerVehicule(1, "Velo", 200.0);
        Peintre p = new Peintre(1);

        // Le peintre visite le vélo
        v.accept(p);

        // Vérification que le vélo est hors service
        assertFalse(v.estDisponible(), "Le vélo doit être hors service après peinture");
    }
}
