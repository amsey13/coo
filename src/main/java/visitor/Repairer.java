package visitor;

import vehicule.Vehicle;
import vehicule.Bike;
import vehicule.Scooter;

/**
 * A visitor that inspects and repairs vehicles. Invoking this visitor
 * schedules a maintenance period for the visited vehicle. Messages are
 * printed to standard output to trace the inspection.
 */
public class Repairer implements Visitor {

    private final int id;

    public Repairer(int id) {
        this.id = id;
    }

    @Override
    public void visitVehicle(Vehicle v) {
        System.out.println("Repairer " + id + " inspects: " + v.getDescription());
        v.startMaintenance(1);
    }

    @Override
    public void visit(Bike v) {
        System.out.println("Repairer " + id + " inspects: " + v.getDescription());
        v.startMaintenance(1);
    }

    @Override
    public void visit(Scooter s) {
        System.out.println("Repairer " + id + " inspects: " + s.getDescription());
        s.startMaintenance(1);
    }
}