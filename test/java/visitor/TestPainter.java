package visitor;

import factory.BikeFactory;
import vehicule.Vehicle;
import visitor.Painter;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Painter} visitor. Verifies that visiting a bike puts it out of service.
 */
class TestPainter {

    @Test
    void testPainterPutsBikeOutOfService() {
        BikeFactory factory = new BikeFactory();
        Vehicle v = factory.createVehicle(1, "Bike", 200.0);
        Painter p = new Painter(1);

        // The painter visits the bike
        v.accept(p);

        // Verify that the bike is out of service
        assertFalse(v.isAvailable(), "The bike should be out of service after painting");
    }
}