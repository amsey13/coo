package main.java.station;

import main.java.observer.Observer;
import main.java.observer.Subject;
import main.java.vehicule.*;

import java.util.ArrayList;
import java.util.List;

public class Station implements Subject {

    private int id;
    private int capacite;
    private List<Emplacement> emplacements;

    private List<Observer> observers = new ArrayList<>();

    public Station(int id, int capacite) {
        this.id = id;
        this.capacite = capacite;
        this.emplacements = new ArrayList<>();

        for (int i = 0; i < capacite; i++) {
            emplacements.add(new Emplacement(i));
        }
    }

    public int getId() {
        return id;
    }

    public boolean estPleine() {
        return nbVehicules() == capacite;
    }

    public boolean estVide() {
        return nbVehicules() == 0;
    }

    public int nbVehicules() {
        int count = 0;
        for (Emplacement e : emplacements) {
            if (!e.estLibre()) count++;
        }
        return count;
    }

    // --- Sujet (Observer Pattern) ---
    @Override
    public void addObserver(Observer obs) {
        observers.add(obs);
    }

    @Override
    public void removeObserver(Observer obs) {
        observers.remove(obs);
    }

    @Override
    public void notifyObservers(String event) {
        for (Observer o : observers) {
            o.update("Station " + id + " : " + event);
        }
    }

    // --- Déposer un véhicule ---
    public void deposer(Vehicule v) {
        for (Emplacement e : emplacements) {
            if (e.estLibre()) {
                e.deposer(v);
                notifyObservers("Dépôt de " + v.getDescription());
                return;
            }
        }
        notifyObservers("tentative de dépôt mais station pleine");
    }

    // --- Retirer un véhicule ---
    public Vehicule retirer() {
        for (Emplacement e : emplacements) {
            if (!e.estLibre()) {
                Vehicule v = e.retirer();
                notifyObservers("Retrait de " + v.getDescription());
                return v;
            }
        }
        notifyObservers("tentative de retrait mais station vide");
        return null;
    }
}