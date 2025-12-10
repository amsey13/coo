package vehicule;

/**
 * State indicating that the vehicle is currently rented by a user. In this
 * state it is not possible to rent the vehicle again or declare it
 * available. A repair during the rental sends the vehicle out of service
 * (to simulate a breakdown during use). Returning the vehicle is handled
 * elsewhere by directly setting the state to {@link Available} when the
 * slot processes the return.
 */
public class Rented extends VehicleState {

    /**
     * Constructs a rented state for the given vehicle.
     *
     * @param vehicle the vehicle being rented
     */
    public Rented(AbstractVehicle vehicle) {
        super(vehicle);
    }

    @Override
    public void rent() {
        // Already rented: ignore operation
    }

    @Override
    public void repair() {
        // If a repair is requested while the vehicle is rented, we consider
        // that a fault occurred and move to out of service
        vehicle.setState(new OutOfService(vehicle));
    }

    @Override
    public void steal() {
        vehicle.setState(new Stolen(vehicle));
    }

    @Override
    public void putOutOfService() {
        vehicle.setState(new OutOfService(vehicle));
    }
}