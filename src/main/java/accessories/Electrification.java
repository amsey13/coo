package accessories;

import vehicule.Vehicle;

/**
 * Decorator that electrifies a vehicle. Electrification adds an electric
 * assist to the underlying vehicle, increasing the cost accordingly and
 * amending the description. This class relies on the English
 * {@link vehicule.Vehicle} API.
 */
public class Electrification extends VehicleDecorator {
    /**
     * Constructs an electrification decorator wrapping the given vehicle.
     *
     * @param v the vehicle to electrify
     */
    public Electrification(Vehicle v) {
        super(v);
    }

    @Override
    public String getDescription() {
        return decoratedVehicle.getDescription() + " + Electrification";
    }

    @Override
    public double getCost() {
        return decoratedVehicle.getCost() + 2.5;
    }
}
