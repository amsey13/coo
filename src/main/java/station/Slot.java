package station;

import vehicule.Vehicle;

/**
 * Represents a single physical slot in a station. A slot can either be empty
 * or contain exactly one vehicle. All operations on the stored vehicle
 * (rental, maintenance, theft detection) are centralised here to respect
 * the Law of Demeter: the station interacts with the slot rather than
 * directly with the vehicle it contains.
 */
public class Slot {

    private final int id;
    private Vehicle vehicle;
    private int aloneTurns = 0;

    /**
     * Constructs a slot with the given identifier.
     *
     * @param id the identifier of the slot
     */
    public Slot(int id) {
        this.id = id;
    }

    /**
     * Returns {@code true} if the slot currently has no vehicle.
     */
    public boolean isFree() {
        return vehicle == null;
    }

    /**
     * Deposits a vehicle into this slot if it is free.
     *
     * @param v the vehicle to deposit
     */
    public void deposit(Vehicle v) {
        if (isFree()) {
            this.vehicle = v;
            this.aloneTurns = 0;
        }
    }

    /**
     * Removes the vehicle from this slot without changing its state or
     * recording a rental. Used for redistribution or when a stolen vehicle
     * is removed.
     *
     * @return the vehicle that was removed, or {@code null} if the slot was empty
     */
    public Vehicle remove() {
        if (vehicle != null) {
            Vehicle v = vehicle;
            vehicle = null;
            aloneTurns = 0;
            return v;
        }
        return null;
    }

    /**
     * Returns the vehicle currently stored in this slot, or {@code null} if none.
     */
    public Vehicle getVehicle() {
        return vehicle;
    }

    /**
     * Returns the identifier of this slot.
     */
    public int getId() {
        return id;
    }

    /**
     * Returns {@code true} if the stored vehicle is available for rental.
     */
    public boolean containsAvailableVehicle() {
        return vehicle != null && vehicle.isAvailable();
    }

    /**
     * Removes the vehicle from this slot and records a rental by calling
     * {@link Vehicle#recordRental()} on it. Returns the vehicle if it was
     * available, otherwise returns {@code null}.
     */
    public Vehicle removeAndRecordRental() {
        if (containsAvailableVehicle()) {
            vehicle.recordRental();
            Vehicle v = vehicle;
            vehicle = null;
            aloneTurns = 0;
            return v;
        }
        return null;
    }

    /**
     * Removes the vehicle from this slot without registering a rental. Used for
     * redistribution (the vehicle remains in the same state).
     *
     * @return the vehicle removed, or {@code null} if no available vehicle
     */
    public Vehicle removeWithoutRecording() {
        if (vehicle != null && vehicle.isAvailable()) {
            Vehicle v = vehicle;
            vehicle = null;
            aloneTurns = 0;
            return v;
        }
        return null;
    }

    /**
     * Applies one maintenance tick to the stored vehicle, if any.
     */
    public void applyMaintenance() {
        if (vehicle != null) {
            vehicle.maintenanceTick();
        }
    }

    /**
     * Increments the counter used to detect when a vehicle has been alone in
     * the station for too long. If the counter reaches 2, the vehicle is
     * considered stolen and is removed from the slot.
     */
    public void incrementAloneTurnsAndCheckTheft() {
        if (vehicle != null) {
            aloneTurns++;
            if (aloneTurns >= 2) {
                vehicle.reportStolen();
                vehicle = null;
                aloneTurns = 0;
            }
        }
    }

    /**
     * Resets the counter tracking how many turns a vehicle has been alone.
     */
    public void resetAloneTurns() {
        aloneTurns = 0;
    }

    /**
     * Returns the number of consecutive turns the current vehicle has been alone.
     */
    public int getAloneTurns() {
        return aloneTurns;
    }
}