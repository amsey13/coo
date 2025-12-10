package srategy;

import station.Station;
import strategy.RandomRobinStrategy;
import vehicule.Bike;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the {@link RandomRobinStrategy}. This strategy randomly selects
 * a source station with too many vehicles and a destination station that
 * needs vehicles, moving exactly one vehicle between them.
 */
public class RandomRobinStrategyTest {

    @Test
    void testRedistributeMovesOneBike() {
        Station full = new Station(1, 4);
        Station empty = new Station(2, 4);

        // Fill the full station
        full.deposit(new Bike("V1", 10));
        full.deposit(new Bike("V2", 10));
        full.deposit(new Bike("V3", 10));
        full.deposit(new Bike("V4", 10)); // 4/4 => station full

        List<Station> stations = new ArrayList<>();
        stations.add(full);
        stations.add(empty);

        RandomRobinStrategy strategy = new RandomRobinStrategy();

        // Before redistribution
        assertEquals(4, full.getVehicleCount());
        assertEquals(0, empty.getVehicleCount());

        // Action: redistribute one bike
        strategy.redistribute(stations);

        // After redistribution: one bike must have moved
        assertEquals(3, full.getVehicleCount(), "The full station should lose one bike");
        assertEquals(1, empty.getVehicleCount(), "The empty station should receive one bike");
    }

    @Test
    void testNoRedistributionIfNoFullOrEmptyStation() {
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);

        // These stations are neither full nor empty => no redistribution
        s1.deposit(new Bike("V1", 10)); // 1 bike
        s2.deposit(new Bike("V2", 10)); // 1 bike

        List<Station> stations = List.of(s1, s2);

        RandomRobinStrategy strategy = new RandomRobinStrategy();

        strategy.redistribute(stations);

        // Nothing should change
        assertEquals(1, s1.getVehicleCount());
        assertEquals(1, s2.getVehicleCount());
    }

    @Test
    void testNoRedistributionIfStationListEmpty() {
        RandomRobinStrategy strategy = new RandomRobinStrategy();

        strategy.redistribute(new ArrayList<>()); // should not throw
    }
}