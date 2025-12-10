package strategy;

import java.util.List;
import station.Station;

/**
 * Strategy interface for redistributing vehicles between stations. Different
 * implementations may use random or round‑robin algorithms. All method
 * names are in English to ensure a consistent API.
 */
public interface RedistributionStrategy {
    /**
     * Performs a redistribution step between the provided stations. The
     * strategy chooses one or more vehicles to move from stations with
     * surplus vehicles to stations that need vehicles.
     *
     * @param stations the list of stations to consider
     */
    void redistribute(List<Station> stations);
}