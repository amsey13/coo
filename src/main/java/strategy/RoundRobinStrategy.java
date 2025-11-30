package main.java.strategy;



import main.java.station.Station;
import main.java.vehicule.Vehicule;
import java.util.ArrayList;
import java.util.List;

public class RoundRobinStrategy implements RedistributionStrategy {

    private int index = 0;

    @Override
    public void redistribuer(List<Station> stations) {
        if (stations.isEmpty()) {
            return;
        }

        List<Station> pleines = stationsPleines(stations);
        List<Station> vides   = stationsVides(stations);

        if (pleines.isEmpty() || vides.isEmpty()) {
            return;
        }

        Station source      = choisirStationSource(pleines);
        Station destination = choisirStationDestination(stations);

        deplacerUnVelo(source, destination);
    }

    /**
     * Renvoie les stations ayant trop de vélos.
     */
    private List<Station> stationsPleines(List<Station> stations) {
        List<Station> pleines = new ArrayList<>();
        for (Station s : stations) {
            if (s.aTropDeVelos()) {
                pleines.add(s);
            }
        }
        return pleines;
    }

    /**
     * Renvoie les stations ayant besoin d'un vélo.
     */
    private List<Station> stationsVides(List<Station> stations) {
        List<Station> vides = new ArrayList<>();
        for (Station s : stations) {
            if (s.aBesoinDeVelo()) {
                vides.add(s);
            }
        }
        return vides;
    }

    /**
     * Ici on peut choisir la source en RoundRobin parmi les pleines,
     * ou simplement prendre la première.
     */
    private Station choisirStationSource(List<Station> pleines) {
        int indexSource = index % pleines.size();
        return pleines.get(indexSource);
    }

    /**
     * Destination en RoundRobin dans *toutes* les stations.
     */
    private Station choisirStationDestination(List<Station> stations) {
        int indexDest = index % stations.size();
        index++;
        return stations.get(indexDest);
    }

    private void deplacerUnVelo(Station source, Station destination) {
        Vehicule velo = source.retirerSansEnregistrement(); // ou retirer()

        if (velo == null) {
            return;
        }

        destination.deposer(velo);
    }
}
