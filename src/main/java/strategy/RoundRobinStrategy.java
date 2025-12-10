package strategy;

import station.Station;
import vehicule.Vehicle;
import java.util.ArrayList;
import java.util.List;

public class RoundRobinStrategy implements RedistributionStrategy {

    private int index = 0;

    @Override
    public void redistribute(List<Station> stations) {
        if (stations.isEmpty()) return;

        List<Station> full = fullStations(stations);
        List<Station> needy = needyStations(stations);

        if (full.isEmpty() || needy.isEmpty()) return;

        Station source = chooseSourceStation(full);
        Station destination = chooseDestinationStation(needy);

        moveOneVehicle(source, destination);
    }

    private List<Station> fullStations(List<Station> stations) {
        List<Station> full = new ArrayList<>();
        for (Station s : stations) {
            if (s.hasTooManyVehicles()) full.add(s);
        }
        return full;
    }

    private List<Station> needyStations(List<Station> stations) {
        List<Station> needy = new ArrayList<>();
        for (Station s : stations) {
            if (s.needsVehicles()) needy.add(s);
        }
        return needy;
    }

    private Station chooseSourceStation(List<Station> full) {
        int idx = index % full.size();
        return full.get(idx);
    }

    private Station chooseDestinationStation(List<Station> needy) {
        int idx = index % needy.size();
        index++;
        return needy.get(idx);
    }

    private void moveOneVehicle(Station source, Station destination) {
        Vehicle v = source.removeWithoutRecording();
        if (v != null) destination.deposit(v);
    }
}
