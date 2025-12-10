package vehicule;

import vehicule.Bike;
import vehicule.Available;
import vehicule.OutOfService;
import vehicule.Rented;
import vehicule.Stolen;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the state transitions of the {@link Bike} class. Ensures that
 * each state change correctly updates the state instance and the bike's
 * availability.
 */
class BikeStateTest {

    @Test
    void testAvailableToRented() {
        Bike v = new Bike("Classic bike", 1.0);
        assertTrue(v.isAvailable());

        // rent changes the state to Rented
        v.getState().rent();
        assertFalse(v.isAvailable());
        assertTrue(v.getState() instanceof Rented);
    }

    @Test
    void testAvailableToStolen() {
        Bike v = new Bike("Classic bike", 1.0);
        v.getState().steal();
        assertFalse(v.isAvailable());
        assertTrue(v.getState() instanceof Stolen);
    }

    @Test
    void testRentedToOutOfService() {
        Bike v = new Bike("Classic bike", 1.0);
        v.getState().rent(); // state Rented
        assertTrue(v.getState() instanceof Rented);

        v.getState().repair(); // Rented -> OutOfService
        assertTrue(v.getState() instanceof OutOfService);
        assertFalse(v.isAvailable());
    }

    @Test
    void testOutOfServiceToAvailable() {
        Bike v = new Bike("Classic bike", 1.0);
        // put out of service via state
        v.getState().putOutOfService();

        // Verify that the bike is out of service
        assertTrue(v.getState() instanceof OutOfService);

        // Test repair
        v.getState().repair(); // OutOfService -> Available
        assertTrue(v.isAvailable());
        assertTrue(v.getState() instanceof Available);
    }

    @Test
    void testStolenImmutability() {
        Bike v = new Bike("Classic bike", 1.0);
        v.getState().steal();
        assertTrue(v.getState() instanceof Stolen);

        // stealing or repairing does not change the state of a stolen bike
        v.getState().repair();
        assertTrue(v.getState() instanceof Stolen);

        v.getState().rent();
        assertTrue(v.getState() instanceof Stolen);
    }
}