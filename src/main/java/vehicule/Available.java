package vehicule;

/**
 * State indicating that the vehicle is currently available for a new rental.
 * When {@link #rent()} is invoked, the vehicle transitions to the
 * {@link Rented} state to indicate it is rented. This operation also
 * increments the total number of rentals to trigger automatic
 * maintenance when a threshold is reached.
 */
public class Available extends VehicleState {

    /**
     * Constructs an available state for the given vehicle.
     *
     * @param vehicle the vehicle that will be available
     */
    public Available(AbstractVehicle vehicle) {
        super(vehicle);
    }

    @Override
    public void rent() {
        // When a rental starts, first transition to rented
        vehicle.setState(new Rented(vehicle));
        // Then increment the counter; if the limit is reached this will
        // trigger maintenance and override the state to OutOfService.
        vehicle.incrementRentalsAndCheckMaintenance();
    }

    @Override
    public void repair() {
        // An available vehicle does not need repairing
    }

    @Override
    public void steal() {
        vehicle.setState(new Stolen(vehicle));
    }

    @Override
    public void putOutOfService() {
        vehicle.setState(new OutOfService(vehicle));
    }

    @Override
    public boolean isAvailable() {
        return true;
    }
}