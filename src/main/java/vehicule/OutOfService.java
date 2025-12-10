package vehicule;

/**
 * State indicating that the vehicle is out of service. In this state the
 * vehicle cannot be rented. The only operation that has an effect is
 * repairing, which returns the vehicle to the {@link Available} state once
 * maintenance has finished.
 */
public class OutOfService extends VehicleState {

    /**
     * Constructs an out‑of‑service state for the given vehicle.
     *
     * @param vehicle the vehicle that is out of service
     */
    public OutOfService(AbstractVehicle vehicle) {
        super(vehicle);
    }

    @Override
    public void rent() {
        // An out‑of‑service vehicle cannot be rented
    }

    @Override
    public void repair() {
        // Returning the vehicle to service: go back to available
        vehicle.setState(new Available(vehicle));
    }

    @Override
    public void steal() {
        // Even out of service, a theft moves to stolen state
        vehicle.setState(new Stolen(vehicle));
    }

    @Override
    public void putOutOfService() {
        // The vehicle is already out of service; no change
    }
}