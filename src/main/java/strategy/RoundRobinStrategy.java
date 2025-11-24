package main.java.strategy;



import main.java.station.Station;
import java.util.List;

public class RoundRobinStrategy implements RedistributionStrategy {

    private int index = 0;

    @Override
    public void redistribuer(List<Station> stations) {
        if (stations.isEmpty()) return;

        Station cible = stations.get(index % stations.size());
        System.out.println("[RoundRobin] Redistribution vers station " + cible.getId());

        index++;
    }
}

