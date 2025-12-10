package vehicule;

import visitor.Visitor;

/**
 * Base implementation for all vehicles. This class holds common state such
 * as the description, base cost, current state and rental counters. It
 * implements the {@link Vehicle} interface and delegates state specific
 * behaviour to the current {@link VehicleState} instance.
 */
public abstract class AbstractVehicle implements Vehicle {

    /**
     * Human‑readable description of the vehicle (e.g. "Classic bike" or
     * "Electric scooter").
     */
    protected String description;

    /**
     * Base rental cost of the vehicle before any accessories are added.
     */
    protected double cost;

    /**
     * Current state of the vehicle. All transitions between states should
     * occur via {@link #setState(VehicleState)}.
     */
    protected VehicleState state;

    /**
     * Number of times this vehicle has been rented. After a certain limit the
     * vehicle is automatically placed into maintenance.
     */
    protected int numRentals = 0;

    /**
     * Maximum number of rentals before triggering automatic maintenance.
     */
    protected static final int RENTAL_LIMIT = 5;

    /**
     * Remaining number of maintenance ticks while the vehicle is out of
     * service. When this counter reaches zero the vehicle will be repaired
     * and returned to the available state.
     */
    private int maintenanceTicksRemaining = 0;

    /**
     * Constructs a vehicle with the given description and base cost. The
     * vehicle starts in the available state.
     *
     * @param description description of the vehicle
     * @param cost        base rental cost
     */
    protected AbstractVehicle(String description, double cost) {
        this.description = description;
        this.cost = cost;
        // initial state: available
        this.state = new Available(this);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getCost() {
        return cost;
    }

    /**
     * Returns the current state of the vehicle. This method is mainly used
     * for testing purposes to inspect the internal state machine.
     *
     * @return the current state
     */
    public VehicleState getState() {
        return state;
    }

    /**
     * Updates the state of the vehicle. This method is called by state
     * implementations during transitions.
     *
     * @param newState the new state to apply
     */
    public void setState(VehicleState newState) {
        this.state = newState;
    }

    @Override
    public boolean isAvailable() {
        return state.isAvailable();
    }

    @Override
    public void reportStolen() {
        state.steal();
    }

    @Override
    public void setOutOfService() {
        state.putOutOfService();
    }

    @Override
    public void recordRental() {
        state.rent();
    }

    /**
     * Increments the rental counter and checks whether the maintenance limit
     * has been reached. If the limit is reached, a maintenance period of one
     * tick is automatically started.
     */
    public void incrementRentalsAndCheckMaintenance() {
        numRentals++;
        if (numRentals >= RENTAL_LIMIT) {
            System.out.println("The vehicle " + description + " becomes out of service after " + numRentals + " rentals.");
            // Start a maintenance period of one tick
            startMaintenance(1);
        }
    }

    @Override
    public void startMaintenance(int numTicks) {
        this.maintenanceTicksRemaining = numTicks;
        // during maintenance we are out of service
        state.putOutOfService();
    }

    @Override
    public void maintenanceTick() {
        if (maintenanceTicksRemaining > 0) {
            maintenanceTicksRemaining--;
            if (maintenanceTicksRemaining == 0) {
                // end of maintenance: repair and return to service
                state.repair();
            }
        }
    }

    @Override
    public void accept(Visitor visitor) {
        // Default behaviour: call the generic visit method on the visitor
        visitor.visitVehicle(this);
    }
}