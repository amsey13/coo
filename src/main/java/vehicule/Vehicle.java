package vehicule;

import visitor.Visitor;

/**
 * Interface representing a rentable vehicle. Concrete implementations such as
 * {@link Bike} or {@link Scooter} must provide a description and base cost
 * and expose operations for state transitions such as reporting a theft or
 * initiating maintenance. All method names are in English to avoid mixing
 * languages in the public API.
 */
public interface Vehicle {

    /**
     * Returns a human‑readable description of this vehicle. The description
     * should succinctly identify the type of vehicle and any accessories
     * applied.
     *
     * @return the description of this vehicle
     */
    String getDescription();

    /**
     * Returns the total rental cost of this vehicle, including any
     * accessories that may have been added via decorators.
     *
     * @return the cost to rent this vehicle
     */
    double getCost();

    /**
     * Indicates whether this vehicle is currently available for rental. A
     * vehicle may be unavailable if it is rented, out of service or stolen.
     *
     * @return {@code true} if the vehicle can be rented, otherwise {@code false}
     */
    boolean isAvailable();

    /**
     * Declares that the vehicle has been stolen. This operation transitions
     * the vehicle into the {@code Stolen} state from which it cannot be
     * rented again.
     */
    void reportStolen();

    /**
     * Records that a rental has occurred. Implementations should delegate
     * to the current state which will enforce the correct state transitions.
     */
    void recordRental();

    /**
     * Forces the vehicle out of service. This may be used by the control
     * centre to proactively remove a vehicle from service when a fault is
     * detected.
     */
    void setOutOfService();

    /**
     * Accepts a visitor performing an operation on this vehicle. See the
     * {@code visitor} package for more details on the Visitor pattern.
     *
     * @param visitor the visitor to accept
     */
    void accept(Visitor visitor);

    /**
     * Performs one tick of maintenance. If the vehicle is currently
     * undergoing maintenance (see {@link #startMaintenance(int)}), this call
     * decrements the remaining number of maintenance ticks and will repair the
     * vehicle when the count reaches zero.
     */
    void maintenanceTick();

    /**
     * Starts a maintenance period during which the vehicle cannot be rented.
     * The maintenance lasts for the specified number of simulation ticks and
     * will repair the vehicle when the delay has expired.
     *
     * @param numTicks number of time intervals for which the vehicle remains
     *                 out of service
     */
    void startMaintenance(int numTicks);
}