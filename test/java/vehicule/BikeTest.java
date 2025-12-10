package vehicule;

import vehicule.Bike;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Basic tests for the {@link Bike} class. Checks initial availability and
 * behaviour when the bike is reported stolen or set out of service.
 */
public class BikeTest {

    @Test
    public void testInitialAvailability() {
        Bike v = new Bike("Classic bike", 1.0);
        assertTrue(v.isAvailable(), "The bike should be available upon creation");
        assertEquals("Classic bike", v.getDescription());
        assertEquals(1.0, v.getCost());
    }

    @Test
    public void testTheft() {
        Bike v = new Bike("Classic bike", 1.0);
        v.reportStolen();
        assertFalse(v.isAvailable(), "A stolen bike should no longer be available");
    }

    @Test
    public void testOutOfService() {
        Bike v = new Bike("Classic bike", 1.0);
        v.setOutOfService();
        assertFalse(v.isAvailable(), "A bike marked out of service should not be available");
    }
}