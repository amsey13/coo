package test.java.centreControle;

import main.java.contolcenter.CentreControle;
import main.java.station.Station;
import main.java.vehicule.Velo;
import main.java.strategy.RoundRobinStrategy;
import main.java.strategy.RandomRobinStrategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class CentreControleTest {

    @Test
    void testRedistributionAvecRandomRobin() {
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);

        // Déposer plusieurs vélos dans la station s1 pour qu'elle ait trop de vélos
        s1.deposer(new Velo("V1", 10));
        s1.deposer(new Velo("V2", 10));
        s1.deposer(new Velo("V3", 10));
        s1.deposer(new Velo("V4", 10));

        CentreControle centre = new CentreControle(1);
        centre.ajouterStation(s1);
        centre.ajouterStation(s2);

        centre.setStrategy(new RandomRobinStrategy());

        // Avant redistribution
        assertEquals(4, s1.nbVehicules());
        assertEquals(0, s2.nbVehicules());

        // Forcer la redistribution
        centre.redistribuerVehicules();

        // Après redistribution, au moins 1 vélo doit être déplacé
        assertTrue(s1.nbVehicules() < 4);
        assertTrue(s2.nbVehicules() > 0);
    }

    @Test
    void testRedistributionAvecRoundRobin() {
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);

        s1.deposer(new Velo("V1", 10));
        s1.deposer(new Velo("V2", 10));
        s1.deposer(new Velo("V3", 10));
        s1.deposer(new Velo("V4", 10));

        CentreControle centre = new CentreControle(1);
        centre.ajouterStation(s1);
        centre.ajouterStation(s2);

        centre.setStrategy(new RoundRobinStrategy());

        // Avant redistribution
        assertEquals(4, s1.nbVehicules());
        assertEquals(0, s2.nbVehicules());

        // Forcer la redistribution
        centre.redistribuerVehicules();

        // Après redistribution
        assertTrue(s1.nbVehicules() < 4);
        assertTrue(s2.nbVehicules() > 0);
    }
}
