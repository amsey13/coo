package strategy;

import station.Station;
import strategy.RoundRobinStrategy;
import vehicule.Bike;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Tests for the {@link RoundRobinStrategy}. This strategy redistributes one vehicle at a time
 * in a round-robin fashion among the stations, balancing occupancy levels.
 */
public class RoundRobinStrategyTest {

    @Test
    void testRedistributeMovesOneBike() {
        // --- Prepare stations ---
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);

        // Station s1 too full
        s1.deposit(new Bike("V1", 10));
        s1.deposit(new Bike("V2", 10));
        s1.deposit(new Bike("V3", 10));
        s1.deposit(new Bike("V4", 10)); // 4/4

        // s2 empty

        List<Station> stations = new ArrayList<>();
        stations.add(s1);
        stations.add(s2);

        RoundRobinStrategy strategy = new RoundRobinStrategy();

        // Before redistribution
        assertEquals(4, s1.getVehicleCount());
        assertEquals(0, s2.getVehicleCount());

        // Action: redistribute one bike
        strategy.redistribute(stations);

        // After redistribution
        assertEquals(3, s1.getVehicleCount(), "Station s1 should lose one bike");
        assertEquals(1, s2.getVehicleCount(), "Station s2 should gain one bike");
    }

    @Test
    void testNoRedistributionIfNoFullOrEmptyStation() {
        Station s1 = new Station(1, 4);
        Station s2 = new Station(2, 4);

        s1.deposit(new Bike("V1", 10));
        s2.deposit(new Bike("V2", 10));

        List<Station> stations = List.of(s1, s2);

        RoundRobinStrategy strategy = new RoundRobinStrategy();

        strategy.redistribute(stations);

        // Nothing should change
        assertEquals(1, s1.getVehicleCount());
        assertEquals(1, s2.getVehicleCount());
    }

    @Test
    void testNoRedistributionIfListEmpty() {
        RoundRobinStrategy strategy = new RoundRobinStrategy();

        strategy.redistribute(new ArrayList<>()); // should not throw
    }

    @Test
    void testRoundRobinMultipleRedistribution() {
        // Using a capacity of 10. Threshold = 7.5 -> 7.
        // If we put 10 bikes:
        // 1. 10 > 7.5 -> Remove. Remains 9.
        // 2. 9 > 7.5  -> Remove. Remains 8.
        // 3. 8 > 7.5  -> Remove. Remains 7.
        // This tests multiple successive redistributions.
        Station s1 = new Station(1, 10); 
        Station s2 = new Station(2, 10);
        Station s3 = new Station(3, 10);

        // s1 full (10 bikes)
        for (int i = 0; i < 10; i++) {
            s1.deposit(new Bike("V" + i, 10));
        }

        // s2 empty, s3 empty
        List<Station> stations = new ArrayList<>();
        stations.add(s1);
        stations.add(s2);
        stations.add(s3);

        RoundRobinStrategy strategy = new RoundRobinStrategy();

        // Redistribute multiple times
        strategy.redistribute(stations); // 1st bike -> s2 (because s2 empty)
        strategy.redistribute(stations); // 2nd bike -> s3 (because s3 empty)
        
        // Verification
        assertEquals(1, s2.getVehicleCount(), "s2 receives 1 bike");
        assertEquals(1, s3.getVehicleCount(), "s3 receives 1 bike");
        assertEquals(8, s1.getVehicleCount(), "s1 loses 2 bikes");
    }
}