package main.java.strategy;

import main.java.station.*;
import java.util.List;
import java.util.Random;

public class RandomRobinStrategy implements RedistributionStrategy {

    private Random random = new Random();

    @Override
    public void redistribuer(List<Station> stations) {
        if (stations.isEmpty()) return;

        int index = random.nextInt(stations.size());
        Station cible = stations.get(index);

        System.out.println("[Random] Redistribution aléatoire vers station " + cible.getId());
    }
}