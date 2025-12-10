package accessories;

import vehicule.Bike;
import vehicule.Vehicle;
import accessories.Basket;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Basket} decorator.
 * Ensures that the basket properly augments the description and cost
 * of the decorated vehicle without modifying the original vehicle.
 */
public class BasketTest {

    @Test
    public void testDescriptionWithBasket() {
        Vehicle v = new Bike("Classic bike", 1.0);
        Vehicle vWithBasket = new Basket(v);

        assertEquals("Classic bike + Basket", vWithBasket.getDescription());
    }

    @Test
    public void testCostWithBasket() {
        Vehicle v = new Bike("Classic bike", 1.0);
        Vehicle vWithBasket = new Basket(v);

        assertEquals(1.0 + 1.5, vWithBasket.getCost(), 0.0001);
    }

    @Test
    public void testOriginalVehicleUnmodified() {
        Bike v = new Bike("Classic bike", 1.0);
        Vehicle vWithBasket = new Basket(v);

        // The decorator should not modify the wrapped vehicle
        assertEquals("Classic bike", v.getDescription());
        assertEquals(1.0, v.getCost());
    }
}