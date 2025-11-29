package main.java.strategy;



import main.java.station.Station;
import java.util.List;

public class RoundRobinStrategy implements RedistributionStrategy {

    private int index = 0;

@Override
    public void redistribuer(List<Station> stations) {

        if (stations.isEmpty()) return;

        // Trouver une station pleine
        Station source = null;
        for (Station s : stations) {
            if (s.aTropDeVelos()) {
                source = s;
                break;
            }
        }

        // Trouver une station vide
        Station destination = null;
        for (Station s : stations) {
            if (s.aBesoinDeVelo()) {
                destination = s;
                break;
            }
        }

        if (source == null || destination == null) {
            System.out.println("[RoundRobin] Aucun mouvement possible.");
            return;
        }

        // Récupérer un vélo de la station source
        var velo = source.retirer();
        if (velo == null) {
            System.out.println("[RoundRobin] Aucun vélo à retirer dans station pleine.");
            return;
        }

        // Rediriger en RoundRobin → choisir une destination
        int indexDest = index % stations.size();
        destination = stations.get(indexDest);
        index++;

        // Déposer dans la destination
        destination.deposer(velo);

        System.out.println("[RoundRobin] Déplacement d’un vélo de Station "
            + source.getId() + " vers Station " + destination.getId());
    }
}

