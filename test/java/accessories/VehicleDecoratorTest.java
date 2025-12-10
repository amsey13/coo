package accessories;

import vehicule.Bike;
import vehicule.Scooter;
import vehicule.Vehicle;
import accessories.VehicleDecorator;
import visitor.Visitor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link VehicleDecorator}. Ensures that all methods delegate
 * correctly to the decorated vehicle and that the visitor pattern is respected.
 */
public class VehicleDecoratorTest {

    /**
     * A simple decorator that does not modify the behaviour of the wrapped
     * vehicle. Used only for testing delegation of methods.
     */
    static class FakeDecorator extends VehicleDecorator {
        public FakeDecorator(Vehicle v) {
            super(v);
        }
    }

    @Test
    public void testDelegationOfDescription() {
        Vehicle v = new Bike("Classic bike", 1.0);
        Vehicle d = new FakeDecorator(v);

        assertEquals(v.getDescription(), d.getDescription());
    }

    @Test
    public void testDelegationOfCost() {
        Vehicle v = new Bike("Classic bike", 1.0);
        Vehicle d = new FakeDecorator(v);

        assertEquals(v.getCost(), d.getCost());
    }

    @Test
    public void testDelegationOfAvailability() {
        Vehicle v = new Bike("Classic bike", 1.0);
        Vehicle d = new FakeDecorator(v);

        assertEquals(v.isAvailable(), d.isAvailable());
    }

    @Test
    public void testDelegationOfTheftReporting() {
        Vehicle v = new Bike("Classic bike", 1.0);
        Vehicle d = new FakeDecorator(v);

        d.reportStolen();
        // the wrapped vehicle should now be unavailable
        assertFalse(v.isAvailable());
    }

    @Test
    public void testDelegationOfOutOfService() {
        Vehicle v = new Bike("Classic bike", 1.0);
        Vehicle d = new FakeDecorator(v);

        d.setOutOfService();
        assertFalse(v.isAvailable());
    }

    @Test
    public void testDelegationOfVisitor() {
        Vehicle v = new Bike("Classic bike", 1.0);

        class TestVisitor implements Visitor {
            boolean visited = false;
            @Override
            public void visitVehicle(Vehicle vehicule) {
                visited = true;
            }

            @Override
            public void visit(Bike b) {
                visited = true;
            }

            @Override
            public void visit(Scooter s) {
                visited = true;
            }
        }

        TestVisitor visitor = new TestVisitor();
        Vehicle d = new FakeDecorator(v);

        d.accept(visitor);

        assertTrue(visitor.visited);
    }
}