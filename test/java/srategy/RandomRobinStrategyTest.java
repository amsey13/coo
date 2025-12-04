package test.java.srategy;

import main.java.station.Station;
import main.java.strategy.RandomRobinStrategy;
import main.java.vehicule.Velo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class RandomRobinStrategyTest {

    @Test
    void testRedistributionDeplaceUnVelo() {
        // --- Préparation des stations ---
        Station pleine = new Station(1, 4);
        Station vide   = new Station(2, 4);

        // Remplir la station pleine
        pleine.deposer(new Velo("V1", 10));
        pleine.deposer(new Velo("V2", 10));
        pleine.deposer(new Velo("V3", 10));
        pleine.deposer(new Velo("V4", 10)); // 4/4 => station pleine

        // Aucune dépose dans vide => 0/4

        List<Station> stations = new ArrayList<>();
        stations.add(pleine);
        stations.add(vide);

        RandomRobinStrategy strategy = new RandomRobinStrategy();

        // Avant redistribution
        assertEquals(4, pleine.nbVehicules());
        assertEquals(0, vide.nbVehicules());

        // Action : redistribuer un vélo
        strategy.redistribuer(stations);

        // Après redistribution : un vélo doit avoir bougé
        assertEquals(3, pleine.nbVehicules(), "La station pleine doit perdre un vélo");
        assertEquals(1, vide.nbVehicules(), "La station vide doit recevoir un vélo");
    }

    @Test
    void testAucuneRedistributionSiPasDeStationPleineOuVide() {
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);

        // Ces stations ne sont ni pleines ni vides => pas de redistribution
        s1.deposer(new Velo("V1", 10)); // 1 vélo
        s2.deposer(new Velo("V2", 10)); // 1 vélo

        List<Station> stations = List.of(s1, s2);

        RandomRobinStrategy strategy = new RandomRobinStrategy();

        strategy.redistribuer(stations);

        // Rien ne doit changer
        assertEquals(1, s1.nbVehicules());
        assertEquals(1, s2.nbVehicules());
    }

    @Test
    void testAucuneRedistributionSiListeStationsVide() {
        RandomRobinStrategy strategy = new RandomRobinStrategy();

        strategy.redistribuer(new ArrayList<>()); // ne doit pas planter
    }
}
