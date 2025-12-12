package users;

import org.junit.jupiter.api.Test;
import vehicule.Bike;
import vehicule.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional tests for the {@link User} class focusing on edge cases. These
 * include attempting to rent a vehicle with insufficient funds, passing a
 * negative cost and returning a vehicle when none is currently rented.
 */
public class UserEdgeCasesTest {

    /**
     * Attempts to rent a vehicle when the user does not have enough money.
     * The rental should fail and the user should not have a vehicle.
     */
    @Test
    void testRentVehicleInsufficientBalance() {
        User u = new User(1, 5.0);
        Vehicle v = new Bike("Cheap bike", 10.0);
        // The cost passed to rentVehicle is greater than the user's balance
        u.rentVehicle(v, 10.0);
        assertFalse(u.hasVehicle(),
                "The user should not have a vehicle when they cannot afford the cost");
        assertEquals(5.0, u.getBalance(), 0.0001,
                "The user's balance must remain unchanged when the rental fails");
    }

    /**
     * Ensures that passing a negative rental cost causes an exception to be
     * thrown. Negative prices are invalid and should be rejected.
     */
    @Test
    void testRentVehicleNegativeCost() {
        User u = new User(2, 20.0);
        Vehicle v = new Bike("Bike", 5.0);
        assertThrows(IllegalArgumentException.class, () -> u.rentVehicle(v, -1.0),
                "Renting with a negative cost must throw an exception");
        assertFalse(u.hasVehicle(), "The user should still have no vehicle after the exception");
        assertEquals(20.0, u.getBalance(), 0.0001,
                "The user's balance must not change when an exception occurs");
    }

    /**
     * Tests returning a vehicle when the user has none. The method should
     * return {@code null} and leave the user's state unchanged.
     */
    @Test
    void testReturnVehicleWithoutHavingOne() {
        User u = new User(3, 15.0);
        assertFalse(u.hasVehicle(), "The user should start without a vehicle");
        Vehicle returned = u.returnVehicle();
        assertNull(returned, "Returning a vehicle when none is rented should return null");
        assertFalse(u.hasVehicle(), "The user should still have no vehicle after returning null");
    }
}