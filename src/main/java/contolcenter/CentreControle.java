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

    public void verifierStationsEtRedistribuerSiNecessaire() {
        boolean besoinRedistribution = false;

        //  mise à jour des compteurs pour chaque station
        for (Station s : stations) {
            s.mettreAJourCompteursOccupation();

            if (s.getToursVideConsecutifs() > 2 ||
                s.getToursPleinesConsecutifs() > 2) {
                besoinRedistribution = true;
            }
        }

        // Si au moins une station pose problème → on redistribue
        if (besoinRedistribution) {
            redistribuerVehicules();
            // historique += "\nRedistribution après stations vides/pleines > 2 intervalles";
        }
    }
}