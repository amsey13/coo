package controlCenter;

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

    /**
     * Verifies that the Control Center triggers a redistribution only after a station
     * has remained empty (or full) for more than two consecutive time intervals.
     * <p>
     * The test scenario is:
     * 1. Setup an empty station and a full station.
     * 2. Simulate the passage of time by calling checkStationsAndRedistributeIfNecessary().
     * 3. Ensure no redistribution occurs during the first two turns (observation period).
     * 4. Ensure redistribution occurs on the third turn.
     * </p>
     */
    @Test
    void testRedistributionTriggeredAfterTwoTurns() {
        // Setup: One empty station and one full station (to provide bikes)
        Station emptyStation = new Station(1, 10);
        Station fullStation = new Station(2, 10);
        
        // Fill the second station
        for (int i = 0; i < 10; i++) {
            fullStation.deposit(new Bike("Bike-" + i, 10));
        }

        // Setup Control Center with RoundRobin strategy
        ControlCenter controlCenter = new ControlCenter(1);
        controlCenter.addStation(emptyStation);
        controlCenter.addStation(fullStation);
        controlCenter.setStrategy(new RoundRobinStrategy());

        // --- Turn 1 ---
        // Station is empty, but it's the first observation. No action.
        controlCenter.checkStationsAndRedistributeIfNecessary();
        assertTrue(emptyStation.isEmpty(), "No redistribution should occur after just 1 turn");

        // --- Turn 2 ---
        // Station is still empty. Second observation. No action yet.
        controlCenter.checkStationsAndRedistributeIfNecessary();
        assertTrue(emptyStation.isEmpty(), "No redistribution should occur after 2 turns");

        // --- Turn 3 ---
        // Station has been empty for more than 2 turns. Redistribution must happen.
        controlCenter.checkStationsAndRedistributeIfNecessary();

        // Assertion: The empty station should have received at least one bike
        assertFalse(emptyStation.isEmpty(), "Redistribution should have filled the empty station on the 3rd turn");
    }
}