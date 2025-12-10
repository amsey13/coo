package centreControle;

import station.Station;
import strategy.RandomRobinStrategy;
import strategy.RoundRobinStrategy;
import vehicule.Bike;

import org.junit.jupiter.api.Test;

import controlcenter.ControlCenter;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link ControlCenter}. Ensures that redistribution strategies
 * are correctly invoked and that vehicles are moved between stations.
 */
public class ControlCenterTest {

    @Test
    void testRedistributionWithRandomRobin() {
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);

        // Deposit several bikes in station s1 so that it has too many bikes
        s1.deposit(new Bike("V1", 10));
        s1.deposit(new Bike("V2", 10));
        s1.deposit(new Bike("V3", 10));
        s1.deposit(new Bike("V4", 10));

        ControlCenter centre = new ControlCenter(1);
        centre.addStation(s1);
        centre.addStation(s2);

        centre.setStrategy(new RandomRobinStrategy());

        // Before redistribution
        assertEquals(4, s1.getVehicleCount());
        assertEquals(0, s2.getVehicleCount());

        // Force redistribution
        centre.redistributeVehicles();

        // After redistribution, at least one bike must have moved
        assertTrue(s1.getVehicleCount() < 4);
        assertTrue(s2.getVehicleCount() > 0);
    }

    @Test
    void testRedistributionWithRoundRobin() {
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);

        s1.deposit(new Bike("V1", 10));
        s1.deposit(new Bike("V2", 10));
        s1.deposit(new Bike("V3", 10));
        s1.deposit(new Bike("V4", 10));

        ControlCenter centre = new ControlCenter(1);
        centre.addStation(s1);
        centre.addStation(s2);

        centre.setStrategy(new RoundRobinStrategy());

        // Before redistribution
        assertEquals(4, s1.getVehicleCount());
        assertEquals(0, s2.getVehicleCount());

        // Force redistribution
        centre.redistributeVehicles();

        // After redistribution
        assertTrue(s1.getVehicleCount() < 4);
        assertTrue(s2.getVehicleCount() > 0);
    }
}