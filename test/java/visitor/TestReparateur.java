package main.java.visitor;

import main.java.factory.VeloFactory;
import main.java.vehicule.Vehicule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestReparateur {

    @Test
    void testReparateurDemarreMaintenance() {
        VeloFactory factory = new VeloFactory();
        Vehicule v = factory.creerVehicule(2, "Velo", 250.0);
        Reparateur r = new Reparateur(2);

        // Le réparateur visite le vélo
        v.accept(r);

        // Vérification que le vélo n'est pas disponible pendant la maintenance
        assertFalse(v.estDisponible(), "Le vélo ne doit pas être disponible pendant la maintenance");

        // Si AbstractVehicule a une méthode pour récupérer le nombre de tours de maintenance, tu peux vérifier ça aussi
        // assertEquals(1, ((AbstractVehicule)v).getMaintenanceTours());
    }
}
