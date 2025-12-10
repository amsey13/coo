package vehicule;

/**
 * Abstract state of a vehicle. Each concrete state knows its associated
 * vehicle and implements domain operations such as renting, repairing,
 * reporting stolen or putting out of service. By default a vehicle
 * is considered unavailable unless it is in the {@link Available} state.
 */
public abstract class VehicleState {

    /**
     * Reference to the vehicle whose state is being managed.
     */
    protected final AbstractVehicle vehicle;

    /**
     * Constructs a state associated with the given vehicle.
     *
     * @param vehicle the vehicle governed by this state
     */
    protected VehicleState(AbstractVehicle vehicle) {
        this.vehicle = vehicle;
    }

    /**
     * Operation to rent the vehicle. For an available vehicle this
     * method should transition to the {@link Rented} state and
     * increment the rental count. In other states this method may be a
     * no‑op.
     */
    public abstract void rent();

    /**
     * Operation to repair the vehicle. In the {@link OutOfService} state this
     * should put the vehicle back into {@link Available}. In other states
     * it may do nothing or transition to {@link OutOfService} to simulate
     * a breakdown during use.
     */
    public abstract void repair();

    /**
     * Declares that the vehicle has been stolen. This permanently places
     * the vehicle into the {@link Stolen} state. If the vehicle is already
     * stolen, this method must have no effect.
     */
    public abstract void steal();

    /**
     * Puts the vehicle out of service. This transitions the vehicle into
     * the {@link OutOfService} state. It is used by maintenance routines or
     * when an incident is detected.
     */
    public abstract void putOutOfService();

    /**
     * Indicates whether the current state corresponds to a vehicle that is
     * available for rental. By default states are not available.
     *
     * @return {@code true} if the vehicle can be rented, otherwise {@code false}
     */
    public boolean isAvailable() {
        return false;
    }
}