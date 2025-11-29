package main.java.strategy;

import main.java.station.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomRobinStrategy implements RedistributionStrategy {

    private Random random = new Random();

    @Override
    public void redistribuer(List<Station> stations) {

        if (stations.isEmpty()) return;

        // Choisir une station pleine au hasard
        List<Station> pleines = new ArrayList<>();
        for (Station s : stations) if (s.aTropDeVelos()) pleines.add(s);

        List<Station> vides = new ArrayList<>();
        for (Station s : stations) if (s.aBesoinDeVelo()) vides.add(s);

        if (pleines.isEmpty() || vides.isEmpty()) {
            System.out.println("[Random] Aucun déplacement possible.");
            return;
        }

        Station source = pleines.get(random.nextInt(pleines.size()));
        Station destination = vides.get(random.nextInt(vides.size()));

        var velo = source.retirer();
        if (velo == null) return;

        destination.deposer(velo);

        System.out.println("[Random] Déplacement d’un vélo de Station "
            + source.getId() + " vers Station " + destination.getId());
    }
}