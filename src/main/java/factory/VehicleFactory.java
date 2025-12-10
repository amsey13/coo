package factory;

import vehicule.Vehicle;

/**
 * Abstract factory for creating vehicles. Concrete factories should
 * implement {@link #createVehicle(int, String, double)} to return a
 * suitably configured vehicle instance based on the provided parameters.
 */
public abstract class VehicleFactory {
    /**
     * Creates a vehicle with the given identifier, type and price. The
     * interpretation of the type parameter is up to the concrete factory.
     *
     * @param id    a numeric identifier for the vehicle
     * @param type  a string indicating the subtype of vehicle to create
     * @param price the base rental price of the vehicle
     * @return a newly created vehicle
     */
    public abstract Vehicle createVehicle(int id, String type, double price);
}