package main.java.strategy;

import main.java.station.Station;
import main.java.vehicule.Velo;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

public class RoundRobinStrategyTest {

    @Test
    void testRedistributionDeplaceUnVelo() {
        // --- Préparation des stations ---
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);

        // Station s1 trop pleine
        s1.deposer(new Velo("V1", 10));
        s1.deposer(new Velo("V2", 10));
        s1.deposer(new Velo("V3", 10));
        s1.deposer(new Velo("V4", 10)); // 4/4

        // s2 vide
        // 0 vélos

        List<Station> stations = new ArrayList<>();
        stations.add(s1);
        stations.add(s2);

        RoundRobinStrategy strategy = new RoundRobinStrategy();

        // Avant redistribution
        assertEquals(4, s1.nbVehicules());
        assertEquals(0, s2.nbVehicules());

        // Action : redistribuer un vélo
        strategy.redistribuer(stations);

        // Après redistribution
        assertEquals(3, s1.nbVehicules(), "La station s1 doit perdre un vélo");
        assertEquals(1, s2.nbVehicules(), "La station s2 doit recevoir un vélo");
    }

    @Test
    void testAucuneRedistributionSiPasDeStationPleineOuVide() {
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);

        s1.deposer(new Velo("V1", 10));
        s2.deposer(new Velo("V2", 10));

        List<Station> stations = List.of(s1, s2);

        RoundRobinStrategy strategy = new RoundRobinStrategy();

        strategy.redistribuer(stations);

        // Rien ne change
        assertEquals(1, s1.nbVehicules());
        assertEquals(1, s2.nbVehicules());
    }

    @Test
    void testAucuneRedistributionSiListeVide() {
        RoundRobinStrategy strategy = new RoundRobinStrategy();

        strategy.redistribuer(new ArrayList<>()); // ne doit pas planter
    }

    @Test
    void testRoundRobinMultipleRedistribution() {
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);
        Station s3 = new Station(3, 4);

        // s1 pleine
        s1.deposer(new Velo("V1", 10));
        s1.deposer(new Velo("V2", 10));
        s1.deposer(new Velo("V3", 10));
        s1.deposer(new Velo("V4", 10));

        // s2 vide
        // s3 vide
        List<Station> stations = new ArrayList<>();
        stations.add(s1);
        stations.add(s2);
        stations.add(s3);

        RoundRobinStrategy strategy = new RoundRobinStrategy();

        // Redistribuer plusieurs fois pour tester le Round Robin
        strategy.redistribuer(stations); // 1er vélo -> s2
        strategy.redistribuer(stations); // 2e vélo -> s3
        strategy.redistribuer(stations); // 3e vélo -> s1 ?

        // Vérification simple
        assertEquals(1, s2.nbVehicules(), "s2 reçoit 1 vélo");
        assertEquals(1, s3.nbVehicules(), "s3 reçoit 1 vélo");
        assertEquals(2, s1.nbVehicules(), "s1 perd 2 vélos"); // 4 - 2 = 2
    }
}
