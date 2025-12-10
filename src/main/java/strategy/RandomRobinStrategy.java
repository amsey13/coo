package strategy;

import station.Station;
import vehicule.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A redistribution strategy that chooses random source and destination stations
 * among those that have too many vehicles and those that need vehicles. At
 * most one vehicle is moved per invocation.
 */
public class RandomRobinStrategy implements RedistributionStrategy {

    private final Random random = new Random();

    @Override
    public void redistribute(List<Station> stations) {
        if (stations.isEmpty()) {
            return;
        }

        List<Station> full = fullStations(stations);
        List<Station> needy = needyStations(stations);

        if (!canRedistribute(full, needy)) {
            return;
        }

        Station source = chooseRandomStation(full);
        Station destination = chooseRandomStation(needy);

        moveOneVehicle(source, destination);
    }

    private List<Station> fullStations(List<Station> stations) {
        List<Station> full = new ArrayList<>();
        for (Station s : stations) {
            if (s.hasTooManyVehicles()) {
                full.add(s);
            }
        }
        return full;
    }

    private List<Station> needyStations(List<Station> stations) {
        List<Station> needy = new ArrayList<>();
        for (Station s : stations) {
            if (s.needsVehicles()) {
                needy.add(s);
            }
        }
        return needy;
    }

    private boolean canRedistribute(List<Station> full, List<Station> needy) {
        return !full.isEmpty() && !needy.isEmpty();
    }

    private Station chooseRandomStation(List<Station> candidates) {
        int index = random.nextInt(candidates.size());
        return candidates.get(index);
    }

    private void moveOneVehicle(Station source, Station destination) {
        Vehicle vehicle = source.removeWithoutRecording();
        if (vehicle == null) {
            return;
        }
        destination.deposit(vehicle);
    }
}