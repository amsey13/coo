package factory;

import vehicule.Bike;
import vehicule.Vehicle;

/**
 * Factory implementation for creating bikes. Other vehicle types can be
 * created by extending {@link VehicleFactory} in a similar manner.
 */
public class BikeFactory extends VehicleFactory {

    @Override
    public Vehicle createVehicle(int id, String type, double price) {
        return new Bike("Bike " + id, price);
    }
}