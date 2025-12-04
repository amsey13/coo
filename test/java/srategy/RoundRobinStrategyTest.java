package test.java.srategy;

import main.java.station.Station;
import main.java.strategy.RoundRobinStrategy;
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
        // CORRECTION : On utilise une capacité de 10. Seuil = 7.5 -> 7.
        // Si on met 10 vélos :
        // 1. 10 > 7.5 -> Retrait. Reste 9.
        // 2. 9 > 7.5 -> Retrait. Reste 8.
        // 3. 8 > 7.5 -> Retrait. Reste 7.
        // Cela permet de tester plusieurs redistributions successives.
        Station s1 = new Station(1, 10); 
        Station s2 = new Station(2, 10);
        Station s3 = new Station(3, 10);

        // s1 pleine (10 vélos)
        for (int i = 0; i < 10; i++) {
            s1.deposer(new Velo("V" + i, 10));
        }

        // s2 vide, s3 vide
        List<Station> stations = new ArrayList<>();
        stations.add(s1);
        stations.add(s2);
        stations.add(s3);

        RoundRobinStrategy strategy = new RoundRobinStrategy();

        // Redistribuer plusieurs fois
        strategy.redistribuer(stations); // 1er vélo -> s2 (car s2 vide)
        strategy.redistribuer(stations); // 2e vélo -> s3 (car s3 vide)
        
        // Vérification
        assertEquals(1, s2.nbVehicules(), "s2 reçoit 1 vélo");
        assertEquals(1, s3.nbVehicules(), "s3 reçoit 1 vélo");
        assertEquals(8, s1.nbVehicules(), "s1 perd 2 vélos");
    }
}
