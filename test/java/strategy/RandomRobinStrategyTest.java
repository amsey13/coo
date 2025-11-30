package test.java.strategy;

import main.java.strategy.RandomRobinStrategy;
import main.java.station.Station;
import main.java.vehicule.Vehicule;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RandomRobinStrategyTest {

    private RandomRobinStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new RandomRobinStrategy();
    }

    @Test
    void testRedistribuerDeplacementReel() {
        // Création de vélos
        Vehicule v1 = new Vehicule("Vélo 1", 1.0);
        Vehicule v2 = new Vehicule("Vélo 2", 1.0);

        // Création de stations
        Station stationPleine = new Station("Station Pleine", 2);
        Station stationVide   = new Station("Station Vide", 2);

        // On remplit la stationPleine avec 2 vélos
        stationPleine.deposer(v1);
        stationPleine.deposer(v2);

        // stationVide reste vide

        List<Station> stations = new ArrayList<>();
        stations.add(stationPleine);
        stations.add(stationVide);

        strategy.redistribuer(stations);

        // Vérifier qu'au moins un vélo a été déplacé
        assertTrue(stationPleine.getVelos().size() < 2, "La station pleine a perdu un vélo");
        assertTrue(stationVide.getVelos().size() > 0, "La station vide a reçu un vélo");
    }

    @Test
    void testRedistribuerListeVide() {
        List<Station> stations = new ArrayList<>();
        assertDoesNotThrow(() -> strategy.redistribuer(stations));
    }

    @Test
    void testRedistribuerPasDeVelo() {
        Station stationPleine = new Station("Station Pleine", 2);
        Station stationVide   = new Station("Station Vide", 2);

        // Station pleine mais sans vélo
        List<Station> stations = List.of(stationPleine, stationVide);

        strategy.redistribuer(stations);

        // Vérifie que rien n'a été déplacé
        assertEquals(0, stationPleine.getVelos().size());
        assertEquals(0, stationVide.getVelos().size());
    }
}
