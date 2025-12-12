package station;

import org.junit.jupiter.api.Test;
import vehicule.Bike;
import vehicule.Scooter;
import vehicule.Vehicle;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Additional tests for the {@link Station} class covering edge cases such as
 * depositing into a full station, renting from an empty station, depositing
 * a null vehicle and verifying that a station can accept multiple subtypes
 * of {@link Vehicle} through polymorphism.
 */
public class StationEdgeCasesTest {

    /**
     * Verifies that depositing into a full station does not increase the
     * number of stored vehicles. The implementation simply ignores the
     * deposit when the station is full and does not throw an exception.
     */
    @Test
    void testDepositWhenStationFull() {
        Station s = new Station(1, 1);
        Vehicle v1 = new Bike("B1", 10.0);
        s.deposit(v1);
        // Station is now full
        Vehicle v2 = new Bike("B2", 20.0);
        s.deposit(v2);
        // The second deposit should not increase the vehicle count
        assertEquals(1, s.getVehicleCount(),
                "A full station must not accept additional vehicles");
    }

    /**
     * Verifies that renting from an empty station returns {@code null} and
     * does not throw any exception. The station remains empty after the call.
     */
    @Test
    void testRentFromEmptyStation() {
        Station s = new Station(2, 2);
        assertTrue(s.isEmpty());
        Vehicle rented = s.rent();
        assertNull(rented, "Renting from an empty station should return null");
        assertTrue(s.isEmpty(), "The station should remain empty after a failed rent");
    }

    /**
     * Verifies that depositing a null vehicle results in an
     * {@link IllegalArgumentException} being thrown. This protects the
     * station against invalid inputs.
     */
    @Test
    void testDepositNullVehicle() {
        Station s = new Station(3, 1);
        assertThrows(IllegalArgumentException.class, () -> s.deposit(null),
                "Depositing a null vehicle must throw an exception");
    }

    /**
     * Checks that a station accepts different subtypes of {@link Vehicle} via
     * polymorphism. Both bikes and scooters should be stored without issue.
     */
    @Test
    void testPolymorphismStation() {
        Station s = new Station(4, 5);
        Bike b = new Bike("Velo", 10);
        Scooter sc = new Scooter("Trottinette", 5);
        s.deposit(b);
        s.deposit(sc);
        assertEquals(2, s.getVehicleCount(),
                "The station should store both bikes and scooters");
        // Renting should return a non-null vehicle of type Vehicle
        Vehicle rented = s.rent();
        assertNotNull(rented, "Renting should return a vehicle instance");
        assertTrue(rented instanceof Vehicle);
    }
}