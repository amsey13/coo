package station;

import observer.Observer;
import observer.Subject;
import vehicule.Vehicle;
import vehicule.Bike;
import vehicule.Scooter;

import station.Slot;

import java.util.ArrayList;
import java.util.List;

public class Station implements Subject {

    private int id;
    private int capacity;
    private List<Slot> slots;

    private List<Observer> observers = new ArrayList<>();
    private int consecutiveEmptyTurns = 0;
    private int consecutiveFullTurns = 0;

    public Station(int id, int capacity) {
        this.id = id;
        this.capacity = capacity;
        this.slots = new ArrayList<>();
        for (int i = 0; i < capacity; i++) {
            slots.add(new Slot(i));
        }
    }


    public int getId() {
        return id;
    }

    public boolean isFull() {
        return getVehicleCount() == capacity;
    }

    public boolean isEmpty() {
        return getVehicleCount() == 0;
    }

    /**
     * Indicates whether this station needs more vehicles. A station is
     * considered to need vehicles if fewer than a quarter of its slots are
     * occupied.
     */
    public boolean needsVehicles() {
        return getVehicleCount() < capacity / 4;
    }

    /**
     * Indicates whether this station has too many vehicles. A station has
     * too many vehicles if more than three quarters of its slots are
     * occupied.
     */
    public boolean hasTooManyVehicles() {
        return getVehicleCount() > capacity * 3 / 4;
    }

    /**
     * Returns the number of vehicles currently stored in the station.
     */
    public int getVehicleCount() {
        int count = 0;
        for (Slot s : slots) {
            if (!s.isFree()) count++;
        }
        return count;
    }

    // --- Sujet (Observer Pattern) ---
    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer o : observers) {
            o.update("Station " + id + " : " + event);
        }
    }

    // --- Déposer un véhicule ---
    /**
     * Deposits a vehicle into the first free slot. Notifies observers of the
     * event. If the station is full, a notification is also sent.
     *
     * @param v the vehicle to deposit
     */
    public void deposit(Vehicle v) {
        // Fail fast on a null vehicle to avoid corrupting the station state.
        if (v == null) {
            throw new IllegalArgumentException("Cannot deposit a null vehicle");
        }
        // Try to find a free slot for the vehicle. If one exists, deposit
        // the vehicle and notify observers of the successful deposit.
        for (Slot s : slots) {
            if (s.isFree()) {
                s.deposit(v);
                notifyObservers("Deposit of " + v.getDescription());
                return;
            }
        }
        // If no free slot exists the station is full. Notify observers of the
        // attempt but do not deposit the vehicle. We deliberately do not
        // throw an exception here to leave the caller responsible for
        // handling the full case.
        notifyObservers("attempted deposit but station full");
    }

    // --- Retirer un véhicule ---
    /**
     * Removes an available vehicle from this station and records the rental.
     * The slot itself handles the state change and rental recording.
     *
     * @return the rented vehicle, or {@code null} if none was available
     */
    public Vehicle rent() {
        for (Slot s : slots) {
            Vehicle v = s.removeAndRecordRental();
            if (v != null) {
                notifyObservers("vehicle_removed");
                return v;
            }
        }
        notifyObservers("attempted removal but station empty");
        return null;
    }

    /**
     * Removes an available vehicle from this station without recording a rental.
     * Used when redistributing vehicles between stations.
     *
     * @return the removed vehicle, or {@code null} if none was available
     */
    public Vehicle removeWithoutRecording() {
        for (Slot s : slots) {
            Vehicle v = s.removeWithoutRecording();
            if (v != null) {
                notifyObservers("vehicle_removed");
                return v;
            }
        }
        notifyObservers("attempted removal but station empty");
        return null;
    }

    public void updateOccupancyCounters() {
        if (isEmpty()) {
            consecutiveEmptyTurns++;
            consecutiveFullTurns = 0;
        } else if (isFull()) {
            consecutiveFullTurns++;
            consecutiveEmptyTurns = 0;
        } else {
            // neither empty nor full → reset both counters
            consecutiveEmptyTurns = 0;
            consecutiveFullTurns = 0;
        }
    }

    public int getConsecutiveEmptyTurns() {
        return consecutiveEmptyTurns;
    }

    public int getConsecutiveFullTurns() {
        return consecutiveFullTurns;
    }
    /**
     * Resets the alone-turn counter of all slots.
     */
    private void resetAllAloneTurnsCounters() {
        for (Slot s : slots) {
            s.resetAloneTurns();
        }
    }

    /**
     * Traite le cas où la station contient exactement un véhicule.
     * On incrémente le compteur de l'emplacement occupé, on remet à zéro
     * celui des emplacements libres, et on détecte un éventuel vol.
     */
    private void handleStationWithSingleVehicle() {
        for (Slot s : slots) {
            if (!s.isFree()) {
                handleOccupiedSlotAlone(s);
            } else {
                s.resetAloneTurns();
            }
        }
    }

    /**
     * Incrémente le compteur pour l'emplacement occupé et déclenche un vol
     * si le véhicule est resté seul trop longtemps.
     */
    private void handleOccupiedSlotAlone(Slot s) {
        // delegate the logic to the slot itself: it will track the number of
        // turns the vehicle has been alone and trigger a theft if necessary
        s.incrementAloneTurnsAndCheckTheft();
    }

    public void checkPotentialThefts() {
        // 1) Empty station: nothing to watch
        if (isEmpty()) {
            resetAllAloneTurnsCounters();
            return;
        }

        // 2) More than one vehicle: none is alone
        if (getVehicleCount() > 1) {
            resetAllAloneTurnsCounters();
            return;
        }

        // 3) Exactly one vehicle in the station
        handleStationWithSingleVehicle();
    }

    public void advanceMaintenanceForVehicles() {
        for (Slot s : slots) {
            s.applyMaintenance();
        }
    }
}