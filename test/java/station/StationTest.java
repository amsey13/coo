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
}