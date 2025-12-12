package visitor;

import org.junit.jupiter.api.Test;
import vehicule.Scooter;
import vehicule.Bike;
import vehicule.Vehicle;
import vehicule.OutOfService;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests the double dispatch mechanism of the visitor pattern. In particular
 * we verify that the {@link Painter} correctly invokes the specific visit
 * method for both bikes and scooters and that the visited vehicle is put
 * out of service.
 */
public class VisitorDoubleDispatchTest {

    @Test
    void testPainterVisitsScooter() {
        Vehicle scooter = new Scooter("Trottinette", 5.0);
        Painter p = new Painter(1);
        // The painter visits the scooter
        scooter.accept(p);
        // The scooter should now be out of service
        assertFalse(scooter.isAvailable(), "A painted scooter should be out of service");
        assertTrue(((vehicule.AbstractVehicle) scooter).getState() instanceof OutOfService,
                "The scooter's state must be OutOfService after painting");
    }

    @Test
    void testPainterVisitsBike() {
        Vehicle bike = new Bike("Velo", 8.0);
        Painter p = new Painter(2);
        bike.accept(p);
        assertFalse(bike.isAvailable(), "A painted bike should be out of service");
        assertTrue(((vehicule.AbstractVehicle) bike).getState() instanceof OutOfService,
                "The bike's state must be OutOfService after painting");
    }
}