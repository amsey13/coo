package main.java.station;

import main.java.vehicule.Velo;
import main.java.vehicule.Vehicule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StationTest {

    @Test
    void testDepotEtRetraitVehicule() {
        Station s = new Station(1, 2);
        Vehicule v1 = new Velo("Velo1", 100.0);

        assertTrue(s.estVide());
        s.deposer(v1);
        assertEquals(1, s.nbVehicules());
        assertFalse(s.estVide());
        assertFalse(s.estPleine());

        Vehicule retiré = s.retirer();
        assertEquals(v1, retiré);
        assertTrue(s.estVide());
    }

    @Test
    void testEstPleineEtVide() {
        Station s = new Station(1, 2);
        assertTrue(s.estVide());
        assertFalse(s.estPleine());

        s.deposer(new Velo("V1", 100));
        s.deposer(new Velo("V2", 120));
        assertTrue(s.estPleine());
        assertFalse(s.estVide());
    }

    
    @Test
    void testABesoinDeVelo() {
        Station s = new Station(1, 4);
        
        // Station vide → doit avoir besoin de vélos
        assertTrue(s.aBesoinDeVelo());

        // Déposer 1 vélo → nbVehicules() = 1 → ne satisfait plus < 1
        s.deposer(new Velo("V1", 100));
        assertFalse(s.aBesoinDeVelo());
    }


    @Test
    void testATropDeVelos() {
        Station s = new Station(1, 4);

        s.deposer(new Velo("V1", 100));
        s.deposer(new Velo("V2", 100));
        s.deposer(new Velo("V3", 100));
        assertFalse(s.aTropDeVelos()); // 3 vélos → pas encore trop

        s.deposer(new Velo("V4", 100));
        assertTrue(s.aTropDeVelos()); // 4 vélos → trop de vélos
    }

}
