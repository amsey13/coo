package station;

import vehicule.Bike;
import vehicule.Vehicle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Station} class. Verifies deposit and rental operations,
 * capacity checks, and the logic for determining when a station needs more
 * vehicles or has too many.
 */
class StationTest {

    @Test
    void testDepositAndRentVehicle() {
        Station s = new Station(1, 2);
        Vehicle v1 = new Bike("Bike1", 100.0);

        assertTrue(s.isEmpty());
        s.deposit(v1);
        assertEquals(1, s.getVehicleCount());
        assertFalse(s.isEmpty());
        assertFalse(s.isFull());

        Vehicle removed = s.rent();
        assertEquals(v1, removed);
        assertTrue(s.isEmpty());
    }

    @Test
    void testIsFullAndIsEmpty() {
        Station s = new Station(1, 2);
        assertTrue(s.isEmpty());
        assertFalse(s.isFull());

        s.deposit(new Bike("V1", 100));
        s.deposit(new Bike("V2", 120));
        assertTrue(s.isFull());
        assertFalse(s.isEmpty());
    }

    @Test
    void testNeedsVehicles() {
        Station s = new Station(1, 4);

        // Empty station -> needs vehicles
        assertTrue(s.needsVehicles());

        // Deposit 1 bike -> getVehicleCount() = 1 -> no longer less than capacity/4
        s.deposit(new Bike("V1", 100));
        assertFalse(s.needsVehicles());
    }

    @Test
    void testHasTooManyVehicles() {
        Station s = new Station(1, 4);

        s.deposit(new Bike("V1", 100));
        s.deposit(new Bike("V2", 100));
        s.deposit(new Bike("V3", 100));
        assertFalse(s.hasTooManyVehicles()); // 3 bikes -> not too many yet

        s.deposit(new Bike("V4", 100));
        assertTrue(s.hasTooManyVehicles()); // 4 bikes -> too many
    }

    /**
     * Verifies that a vehicle left alone in a station for two consecutive time intervals
     * is correctly identified as stolen.
     * <p>
     * The test proceeds as follows:
     * 1. A station is created and a single bike is deposited (it is "alone").
     * 2. The first check for potential thefts is triggered (1st interval). The bike should remain available.
     * 3. The second check is triggered (2nd interval). The bike should now be marked as stolen.
     * 4. The station should be empty as the stolen bike is removed from the available pool.
     * </p>
     */
    @Test
    void testTheftAfterTwoTurnsAlone() {
        // Setup: A station with capacity 5 containing a single bike
        Station station = new Station(1, 5);
        Vehicle bike = new Bike("Lonely Bike", 100.0);
        station.deposit(bike);

        // Pre-condition: The bike is in the station
        assertEquals(1, station.getVehicleCount(), "Station should have 1 bike initially");

        // --- Turn 1 ---
        // The bike is alone, so the internal counter increments to 1.
        station.checkPotentialThefts();
        assertTrue(bike.isAvailable(), "The bike should still be available after 1 turn alone");

        // --- Turn 2 ---
        // The bike is still alone, counter reaches 2 -> Theft triggered.
        station.checkPotentialThefts();

        // Assertions
        assertFalse(bike.isAvailable(), "The bike should be marked as stolen after 2 turns alone");
        assertTrue(station.isEmpty(), "The stolen bike should be removed from the station's available slots");
    }

    
}