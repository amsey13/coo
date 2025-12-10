package users;

import org.junit.jupiter.api.Test;

import vehicule.Bike;
import vehicule.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link users.User} class. Verifies that payments are
 * correctly handled and that the user cannot rent more than one vehicle at a time.
 */
public class UserTest {

    @Test
    public void testCanPay() {
        User u = new User(1, 100.0);
        assertTrue(u.canPay(50.0));
        assertFalse(u.canPay(150.0));
    }

    @Test
    public void testPay() {
        User u = new User(1, 100.0);
        u.pay(30.0);
        assertEquals(70.0, u.getBalance(), 0.0001);

        // try to pay more than the balance
        u.pay(100.0);
        // balance should not change because the user cannot pay
        assertEquals(70.0, u.getBalance(), 0.0001);
    }

    @Test
    public void testRentVehicle() {
        User u = new User(1, 100.0);
        Vehicle bike1 = new Bike("Classic bike", 2.0);

        // First rental
        u.rentVehicle(bike1, 50.0);
        assertEquals(50.0, u.getBalance(), 0.0001);
        assertTrue(u.hasVehicle());
        assertSame(bike1, u.getRentedVehicle());

        // Attempt to rent another vehicle while already renting one
        Vehicle bike2 = new Bike("Another bike", 3.0);
        u.rentVehicle(bike2, 30.0);

        // Balance should not change and the rented vehicle should stay the first one
        assertEquals(50.0, u.getBalance(), 0.0001);
        assertSame(bike1, u.getRentedVehicle());
        assertNotSame(bike2, u.getRentedVehicle());
    }

    @Test
    public void testReturnVehicle() {
        User u = new User(1, 100.0);
        Vehicle bike = new Bike("Classic bike", 2.0);

        u.rentVehicle(bike, 50.0);
        assertTrue(u.hasVehicle());
        assertSame(bike, u.getRentedVehicle());

        Vehicle returned = u.returnVehicle();

        // After returning, the user should no longer have a vehicle
        assertFalse(u.hasVehicle(), "The user should no longer have a rented vehicle");
        assertNull(u.getRentedVehicle(), "The rented vehicle reference should be cleared");
        assertSame(bike, returned, "The returned vehicle should be the same instance that was rented");
    }
}
