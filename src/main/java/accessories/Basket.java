package accessories;

import vehicule.Vehicle;

/**
 * Decorator that adds a basket to a vehicle. A basket increases the
 * description and adds a small surcharge to the rental cost.
 */
public class Basket extends VehicleDecorator {

    /**
     * Creates a new basket decorator wrapping the given vehicle.
     *
     * @param v the vehicle to decorate
     */
    public Basket(Vehicle v) {
        super(v);
    }

    @Override
    public String getDescription() {
        return decoratedVehicle.getDescription() + " + Basket";
    }

    @Override
    public double getCost() {
        return decoratedVehicle.getCost() + 1.5;
    }
}