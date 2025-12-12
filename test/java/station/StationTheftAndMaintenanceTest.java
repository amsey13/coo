package station;

import org.junit.jupiter.api.Test;
import vehicule.Bike;
import vehicule.Vehicle;
import vehicule.Stolen;
import visitor.Repairer;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests station behaviours involving theft detection and maintenance. The
 * first test ensures that a vehicle left alone in a station is stolen after
 * two consecutive checks. The second test verifies that a repairer puts
 * a vehicle out of service for two maintenance ticks before it becomes
 * available again.
 */
public class StationTheftAndMaintenanceTest {

    /**
     * Deposits a single bike in a station and calls {@link Station#checkPotentialThefts()}
     * twice. After the second call the bike should have been stolen and
     * removed from the station.
     */
    @Test
    void testTheftAfterTwoChecks() {
        Station s = new Station(1, 2);
        Bike b = new Bike("TestBike", 10.0);
        s.deposit(b);
        // First check: bike is alone -> counter increments but no theft
        s.checkPotentialThefts();
        assertEquals(1, s.getVehicleCount(), "The bike should remain after the first check");
        // Second check: bike is still alone -> should be stolen and removed
        s.checkPotentialThefts();
        assertEquals(0, s.getVehicleCount(), "The bike should be removed after being stolen");
        assertTrue(b.getState() instanceof Stolen, "The bike's state should be Stolen after theft");
    }

    /**
     * Uses a {@link Repairer} to start a maintenance period on a bike. After
     * one maintenance tick the bike should still be unavailable. After a
     * second tick the bike should be repaired and become available again.
     */
    @Test
    void testMaintenanceDuration() {
        Bike b = new Bike("MaintenanceBike", 5.0);
        Repairer r = new Repairer(1);
        // Start maintenance: repairer schedules two maintenance ticks
        b.accept(r);
        // Immediately after starting maintenance the bike must be out of service
        assertFalse(b.isAvailable(), "The bike should be out of service right after inspection");
        // First tick: maintenance is ongoing
        b.maintenanceTick();
        assertFalse(b.isAvailable(), "After one tick the bike should still be out of service");
        // Second tick: maintenance completes and the bike returns to available
        b.maintenanceTick();
        assertTrue(b.isAvailable(), "After maintenance completes the bike should be available");
    }
}