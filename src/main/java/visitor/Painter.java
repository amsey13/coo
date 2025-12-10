package visitor;

import vehicule.Vehicle;
import vehicule.Bike;
import vehicule.Scooter;

/**
 * A visitor that repaints vehicles. Repainting takes a vehicle out of
 * service for maintenance. This visitor prints messages to standard output
 * indicating which vehicle has been repainted.
 */
public class Painter implements Visitor {

    private final int id;

    public Painter(int id) {
        this.id = id;
    }

    @Override
    public void visitVehicle(Vehicle v) {
        System.out.println("Painter " + id + " repaints: " + v.getDescription());
        v.setOutOfService();
    }

    @Override
    public void visit(Bike v) {
        System.out.println("Painter " + id + " repaints: " + v.getDescription());
        v.setOutOfService();
    }

    @Override
    public void visit(Scooter s) {
        System.out.println("Painter " + id + " repaints: " + s.getDescription());
        s.setOutOfService();
    }
}