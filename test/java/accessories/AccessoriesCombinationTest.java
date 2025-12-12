package accessories;

import org.junit.jupiter.api.Test;
import vehicule.Bike;
import vehicule.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for combinations of vehicle decorators. Ensures that multiple
 * accessories accumulate their costs and update the description correctly.
 */
public class AccessoriesCombinationTest {

    /**
     * Decorates a bike with multiple accessories and verifies that the total
     * cost and description reflect all applied decorators. The order of
     * decoration should not matter for the cumulative cost.
     */
    @Test
    void testCumulativeAccessories() {
        Vehicle v = new Bike("City bike", 10.0);
        // Add a basket (adds 1.5) and then electrify (adds 2.5)
        v = new Basket(v);
        v = new Electrification(v);
        // Expected cost: 10 + 1.5 + 2.5 = 14.0
        assertEquals(14.0, v.getCost(), 0.0001,
                "The cost should accumulate across decorators");
        // The description should mention both accessories
        String desc = v.getDescription();
        assertTrue(desc.contains("Basket"), "Description must contain 'Basket'");
        assertTrue(desc.contains("Electrification"), "Description must contain 'Electrification'");
    }
}