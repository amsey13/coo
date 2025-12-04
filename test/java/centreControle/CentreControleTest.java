package main.java.contolcenter;

import main.java.station.Station;
import main.java.vehicule.Velo;
import main.java.strategy.RedistributionStrategy;
import main.java.strategy.RoundRobinStrategy;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class CentreControleTest {

    @Test
    void testRedistributionAvecStrategy() {
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);

        // Déposer plusieurs vélos dans la station s1 pour qu'elle ait trop de vélos
        s1.deposer(new Velo("Velo1", 10));
        s1.deposer(new Velo("Velo2", 10));
        s1.deposer(new Velo("Velo3", 10));
        s1.deposer(new Velo("Velo4", 10));
        
        // S2 sera vide et a besoin de vélos
        CentreControle centre = new CentreControle(1);
        centre.ajouterStation(s1);
        centre.ajouterStation(s2);

        // On attribue une stratégie RoundRobin
        RedistributionStrategy strategy = new RoundRobinStrategy();
        centre.setStrategy(strategy);

        // Avant redistribution
        assertEquals(3, s1.nbVehicules());
        assertEquals(0, s2.nbVehicules());

        // Forcer la redistribution
        centre.redistribuerVehicules();

        // Après redistribution, au moins 1 vélo doit être déplacé
        assertTrue(s1.nbVehicules() < 3);
        assertTrue(s2.nbVehicules() > 0);
    }
}
