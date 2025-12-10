package station;

import vehicule.Bike;
import vehicule.Vehicle;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Slot} class. Verifies basic functionality such as
 * depositing and removing vehicles, and tracking the number of turns a
 * vehicle has been alone.
 */
class SlotTest {

    @Test
    void testIsFree() {
        Slot e = new Slot(1);
        assertTrue(e.isFree());
    }

    @Test
    void testDepositAndRemoveVehicle() {
        Slot e = new Slot(1);
        Vehicle v = new Bike("BikeTest", 100.0);

        e.deposit(v);
        assertFalse(e.isFree());
        assertEquals(v, e.getVehicle());

        Vehicle removed = e.remove();
        assertEquals(v, removed);
        assertTrue(e.isFree());
    }

    @Test
    void testAloneTurns() {
        Slot e = new Slot(1);
        assertEquals(0, e.getAloneTurns());

        // increment the counter using the updated method
        e.incrementAloneTurnsAndCheckTheft();
        assertEquals(1, e.getAloneTurns());

        e.resetAloneTurns();
        assertEquals(0, e.getAloneTurns());
    }
}