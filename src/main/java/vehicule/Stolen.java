package vehicule;

/**
 * State indicating that the vehicle has been stolen. Once stolen, no
 * operation (rent, repair or put out of service) modifies the state:
 * the vehicle remains stolen permanently.
 */
public class Stolen extends VehicleState {

    /**
     * Constructs a stolen state for the given vehicle.
     *
     * @param vehicle the stolen vehicle
     */
    public Stolen(AbstractVehicle vehicle) {
        super(vehicle);
    }

    @Override
    public void rent() {
        // impossible to rent a stolen vehicle
    }

    @Override
    public void repair() {
        // a stolen vehicle cannot be repaired
    }

    @Override
    public void steal() {
        // already stolen: nothing to do
    }

    @Override
    public void putOutOfService() {
        // already stolen: nothing to do
    }
}