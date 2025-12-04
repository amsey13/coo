package main.java.strategy;

import main.java.station.Station;
import main.java.vehicule.Vehicule;
import java.util.ArrayList;
import java.util.List;

public class RoundRobinStrategy implements RedistributionStrategy {

    private int index = 0;

    @Override
    public void redistribuer(List<Station> stations) {
        if (stations.isEmpty()) return;

        List<Station> pleines = stationsPleines(stations);
        List<Station> vides = stationsVides(stations);

        if (pleines.isEmpty() || vides.isEmpty()) return;

        Station source = choisirStationSource(pleines);
        Station destination = choisirStationDestination(vides);

        deplacerUnVelo(source, destination);
    }

    private List<Station> stationsPleines(List<Station> stations) {
        List<Station> pleines = new ArrayList<>();
        for (Station s : stations) {
            if (s.aTropDeVelos()) pleines.add(s);
        }
        return pleines;
    }

    private List<Station> stationsVides(List<Station> stations) {
        List<Station> vides = new ArrayList<>();
        for (Station s : stations) {
            if (s.aBesoinDeVelo()) vides.add(s);
        }
        return vides;
    }

    private Station choisirStationSource(List<Station> pleines) {
        int idx = index % pleines.size();
        return pleines.get(idx);
    }

    private Station choisirStationDestination(List<Station> vides) {
        int idx = index % vides.size();
        index++;
        return vides.get(idx);
    }

    private void deplacerUnVelo(Station source, Station destination) {
        Vehicule v = source.retirerSansEnregistrement();
        if (v != null) destination.deposer(v);
    }
}
