package main.java.strategy;

import main.java.station.*;
import main.java.vehicule.Vehicule;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRobinStrategy implements RedistributionStrategy {

    private Random random = new Random();

    @Override
    public void redistribuer(List<Station> stations) {
        if (stations.isEmpty()) {
            return;
        }

        List<Station> pleines = stationsPleines(stations);
        List<Station> vides   = stationsVides(stations);

        if (!peutRedistribuer(pleines, vides)) {
            return;
        }

        Station source      = choisirStationAleatoire(pleines);
        Station destination = choisirStationAleatoire(vides);

        deplacerUnVelo(source, destination);
    }

    private List<Station> stationsPleines(List<Station> stations) {
        List<Station> pleines = new ArrayList<>();
        for (Station s : stations) {
            if (s.aTropDeVelos()) {
                pleines.add(s);
            }
        }
        return pleines;
    }

    private List<Station> stationsVides(List<Station> stations) {
        List<Station> vides = new ArrayList<>();
        for (Station s : stations) {
            if (s.aBesoinDeVelo()) {
                vides.add(s);
            }
        }
        return vides;
    }

    private boolean peutRedistribuer(List<Station> pleines, List<Station> vides) {
        return !pleines.isEmpty() && !vides.isEmpty();
    }

    private Station choisirStationAleatoire(List<Station> candidates) {
        int index = random.nextInt(candidates.size());
        return candidates.get(index);
    }

    private void deplacerUnVelo(Station source, Station destination) {
        Vehicule vehicule = source.retirerSansEnregistrement(); 

        if (vehicule == null) {
            return;
        }

        destination.deposer(vehicule);
    }


}