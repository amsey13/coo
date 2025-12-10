package visitor;

import factory.BikeFactory;
import vehicule.Vehicle;
import visitor.Repairer;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Repairer} visitor. Verifies that visiting a bike starts maintenance.
 */
class TestRepairer {

    @Test
    void testRepairerStartsMaintenance() {
        BikeFactory factory = new BikeFactory();
        Vehicle v = factory.createVehicle(2, "Bike", 250.0);
        Repairer r = new Repairer(2);

        // The repairer visits the bike
        v.accept(r);

        // Verify that the bike is not available during maintenance
        assertFalse(v.isAvailable(), "The bike should not be available during maintenance");

        // If AbstractVehicle had a method to retrieve the number of remaining maintenance ticks,
        // you could verify that here as well.
    }
}