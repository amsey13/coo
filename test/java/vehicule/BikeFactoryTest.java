package vehicule;

import factory.BikeFactory;
import vehicule.Vehicle;
import vehicule.Bike;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link BikeFactory}. Ensures that bikes are correctly created.
 */
class BikeFactoryTest {

    @Test
    void testBikeNotNull() {
        BikeFactory factory = new BikeFactory();
        Vehicle v = factory.createVehicle(1, "Bike", 200.0);
        assertNotNull(v, "The factory must not return null");
    }

    @Test
    void testBikeType() {
        BikeFactory factory = new BikeFactory();
        Vehicle v = factory.createVehicle(1, "Bike", 200.0);
        assertTrue(v instanceof Bike, "The factory must create a Bike instance");
    }

    @Test
    void testBikeDescription() {
        BikeFactory factory = new BikeFactory();
        Vehicle v = factory.createVehicle(1, "Bike", 200.0);
        assertEquals("Bike 1", v.getDescription(), "The description should be correctly initialised");
    }

    @Test
    void testBikeCost() {
        BikeFactory factory = new BikeFactory();
        Vehicle v = factory.createVehicle(1, "Bike", 200.0);
        assertEquals(200.0, v.getCost(), "The cost should be correctly initialised");
    }
}