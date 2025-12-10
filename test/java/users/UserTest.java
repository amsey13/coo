package users;

import org.junit.jupiter.api.Test;

import users.User;

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
        assertEquals(70.0, u.getBalance());
        // try to pay more than the balance
        u.pay(100.0);
        assertEquals(70.0, u.getBalance()); // balance should not change
    }

    @Test
    public void testRentVehicle() {
        User u = new User(1, 100.0);
        u.rentVehicle(5, 50.0);
        assertEquals(50.0, u.getBalance());
        assertEquals(5, u.getRentedVehicleId());

        // attempt to rent another vehicle while already renting one
        u.rentVehicle(6, 30.0);
        assertEquals(50.0, u.getBalance());  // balance should not change
        assertEquals(5, u.getRentedVehicleId());  // id remains the same
    }

    @Test
    public void testReturnVehicle() {
        User u = new User(1, 100);
        u.rentVehicle(5, 50);
        assertEquals(5, u.getRentedVehicleId());

        u.returnVehicle();
        assertNull(u.getRentedVehicleId(), "The user should no longer have a rented vehicle after returning it");
    }
}