package test.java.accessories;

import main.java.vehicule.Velo;
import main.java.accessories.Panier;
import main.java.vehicule.Vehicule;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PanierTest {

    @Test
    public void testDescriptionAvecPanier() {
        Vehicule v = new Velo("Vélo classique", 1.0);
        Vehicule vAvecPanier = new Panier(v);

        assertEquals("Vélo classique + Panier", vAvecPanier.getDescription());
    }

    @Test
    public void testCoutAvecPanier() {
        Vehicule v = new Velo("Vélo classique", 1.0);
        Vehicule vAvecPanier = new Panier(v);

        assertEquals(1.0 + 1.5, vAvecPanier.getCout(), 0.0001);
    }

    @Test
    public void testVehiculeOriginalNonModifie() {
        Velo v = new Velo("Vélo classique", 1.0);
        Vehicule vAvecPanier = new Panier(v);

        // Le décorateur modifie SEULEMENT la vue décorée
        assertEquals("Vélo classique", v.getDescription());
        assertEquals(1.0, v.getCout());
    }
}
