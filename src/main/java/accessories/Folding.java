package accessories;

import vehicule.Vehicle;

/**
 * Decorator that adds a folding mechanism to a vehicle. Folding adds
 * convenience for storage and a corresponding surcharge to the rental cost.
 */
public class Folding extends VehicleDecorator {

    /**
     * Creates a new folding decorator wrapping the given vehicle.
     *
     * @param v the vehicle to decorate
     */
    public Folding(Vehicle v) {
        super(v);
    }

    @Override
    public String getDescription() {
        return decoratedVehicle.getDescription() + " + Folding";
    }

    @Override
    public double getCost() {
        return decoratedVehicle.getCost() + 5.0;
    }
}