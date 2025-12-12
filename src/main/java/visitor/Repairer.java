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
        // Start a maintenance period lasting two turns. During this time the
        // vehicle will remain unavailable until two maintenance ticks have been
        // applied.
        v.startMaintenance(2);
    }

    @Override
    public void visit(Bike v) {
        System.out.println("Repairer " + id + " inspects: " + v.getDescription());
        // Start a two‑turn maintenance period for bikes as well
        v.startMaintenance(2);
    }

    @Override
    public void visit(Scooter s) {
        System.out.println("Repairer " + id + " inspects: " + s.getDescription());
        // Start a two‑turn maintenance period for scooters
        s.startMaintenance(2);
    }
}