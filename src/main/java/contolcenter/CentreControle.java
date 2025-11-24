package main.java.contolcenter;

import main.java.observer.Observer;
import main.java.station.Station;
import main.java.strategy.RedistributionStrategy;

import java.util.ArrayList;
import java.util.List;

public class CentreControle implements Observer {

    private int id;
    private String historique;
    private RedistributionStrategy strategy;

    private List<Station> stations = new ArrayList<>();

    public CentreControle(int id) {
        this.id = id;
        this.historique = "";
    }

    // --- Gestion des stations ---
    public void ajouterStation(Station s) {
        stations.add(s);
    }

    public List<Station> getStations() {
        return stations;
    }

    // --- Observer : notifications venant des stations ---
    @Override
    public void update(String event) {
        historique += "Centre " + id + " notification : " + event + "\n";

        // Exemple simple : si un événement contient "vide" ou "pleine" → redistribution
        if ((event.contains("vide") || event.contains("pleine")) && strategy != null) {
            strategy.redistribuer(stations);
        }
    }

    // --- Strategy ---
    public void setStrategy(RedistributionStrategy strategy) {
        this.strategy = strategy;
    }

    public void redistribuerVehicules() {
        if (strategy != null) {
            strategy.redistribuer(stations);
        }
    }

    public String getHistorique() {
        return historique;
    }
}