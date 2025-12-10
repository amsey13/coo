package contolcenter;

import observer.Observer;
import station.Station;
import strategy.RedistributionStrategy;

import java.util.ArrayList;
import java.util.List;

/**
 * Control centre that observes stations and decides when to redistribute
 * vehicles between them. The centre aggregates notifications from stations
 * and maintains a redistribution strategy that can be changed at runtime.
 */
public class ControlCenter implements Observer {

    private final int id;
    private String history;
    private RedistributionStrategy strategy;
    private final List<Station> stations = new ArrayList<>();

    /**
     * Constructs a control centre with the given identifier.
     *
     * @param id the identifier of the control centre
     */
    public ControlCenter(int id) {
        this.id = id;
        this.history = "";
    }

    // --- Station management ---

    /**
     * Adds a station to be managed by this control centre.
     *
     * @param s the station to add
     */
    public void addStation(Station s) {
        stations.add(s);
    }

    /**
     * Returns the list of stations managed by this control centre.
     *
     * @return the list of stations
     */
    public List<Station> getStations() {
        return stations;
    }

    // --- Observer: notifications from stations ---
    @Override
    public void update(String event) {
        history += "Center " + id + " notification : " + event + "\n";
    }

    // --- Strategy ---

    /**
     * Sets the redistribution strategy to use when redistributing vehicles.
     *
     * @param strategy the new strategy
     */
    public void setStrategy(RedistributionStrategy strategy) {
        this.strategy = strategy;
    }

    /**
     * Invokes the redistribution strategy on the managed stations.
     */
    public void redistributeVehicles() {
        if (strategy != null) {
            strategy.redistribute(stations);
        }
    }

    /**
     * Returns the accumulated history of notifications received by this centre.
     *
     * @return the history string
     */
    public String getHistory() {
        return history;
    }

    /**
     * Checks each station's occupancy counters and triggers a redistribution
     * if any station has been empty or full for more than two consecutive
     * turns.
     */
    public void checkStationsAndRedistributeIfNecessary() {
        boolean needRedistribution = false;

        // update counters for each station
        for (Station s : stations) {
            s.updateOccupancyCounters();
            if (s.getConsecutiveEmptyTurns() > 2 ||
                s.getConsecutiveFullTurns() > 2) {
                needRedistribution = true;
            }
        }
        // if at least one station poses a problem, redistribute
        if (needRedistribution) {
            redistributeVehicles();
        }
    }
}