package test.java.vehicule;
import main.java.vehicule.*;

import static org.junit.Assert.*;

import org.junit.Test;



//Tester le **pattern State** Transitions entre états 





class VeloStateTest {

    @Test
    void testDisponibleToLoue() {
        Velo v = new Velo("Vélo classique", 1.0);
        assertTrue(v.estDisponible());

        // emprunter change l'état à Loue
        v.getEtat().emprunter();
        assertFalse(v.estDisponible());
        assertTrue(v.getEtat() instanceof Loue);
    }

    @Test
    void testDisponibleToVole() {
        Velo v = new Velo("Vélo classique", 1.0);
        v.getEtat().voler();
        assertFalse(v.estDisponible());
        assertTrue(v.getEtat() instanceof Vole);
    }

    @Test
    void testLoueToHorsService() {
        Velo v = new Velo("Vélo classique", 1.0);
        v.getEtat().emprunter(); // état Loue
        assertTrue(v.getEtat() instanceof Loue);

        v.getEtat().reparer(); // Loue -> HorsService
        assertTrue(v.getEtat() instanceof HorsService);
        assertFalse(v.estDisponible());
    }

    @Test
    void testHorsServiceToDisponible() {
        Velo v = new Velo("Vélo classique", 1.0);
        v.getEtat().reparer(); // HorsService -> Disponible
        assertTrue(v.estDisponible());
        assertTrue(v.getEtat() instanceof Disponible);
    }

    @Test
    void testVolImmutabilite() {
        Velo v = new Velo("Vélo classique", 1.0);
        v.getEtat().voler();
        assertTrue(v.getEtat() instanceof Vole);

        // voler ou réparer ne change pas l'état d'un vélo volé
        v.getEtat().reparer();
        assertTrue(v.getEtat() instanceof Vole);

        v.getEtat().emprunter();
        assertTrue(v.getEtat() instanceof Vole);
    }
}
