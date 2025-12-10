package accessories;

import vehicule.Vehicle;
import visitor.Visitor;

/**
 * Base decorator class for vehicles. Decorators wrap an existing vehicle
 * instance and delegate most operations to it, adding additional behaviour
 * such as modified cost or description. This class implements
 * {@link vehicule.Vehicle} so decorators can be nested.
 */
public class VehicleDecorator implements Vehicle {
    /** The wrapped vehicle instance. */
    protected Vehicle decoratedVehicle;

    /**
     * Constructs a decorator around the given vehicle.
     *
     * @param v the vehicle to decorate
     */
    public VehicleDecorator(Vehicle v) {
        this.decoratedVehicle = v;
    }

    @Override
    public String getDescription() {
        return decoratedVehicle.getDescription();
    }

    @Override
    public double getCost() {
        return decoratedVehicle.getCost();
    }

    @Override
    public boolean isAvailable() {
        return decoratedVehicle.isAvailable();
    }

    @Override
    public void reportStolen() {
        decoratedVehicle.reportStolen();
    }

    @Override
    public void setOutOfService() {
        decoratedVehicle.setOutOfService();
    }

    @Override
    public void accept(Visitor visitor) {
        decoratedVehicle.accept(visitor);
    }

    @Override
    public void recordRental() {
        decoratedVehicle.recordRental();
    }

    @Override
    public void maintenanceTick() {
        decoratedVehicle.maintenanceTick();
    }

    @Override
    public void startMaintenance(int numTicks) {
        decoratedVehicle.startMaintenance(numTicks);
    }
}