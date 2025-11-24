package main.java.station;

import main.java.observer.Observer;
import main.java.observer.Subject;

import java.util.ArrayList;
import java.util.List;

public class Station implements Subject {

    private int id;
    private int capacite;
    private int nbVehicules;

    private List<Observer> observers = new ArrayList<>();

    public Station(int id, int capacite) {
        this.id = id;
        this.capacite = capacite;
        this.nbVehicules = 0;
    }

    public boolean estPleine() {
        return nbVehicules >= capacite;
    }

    public boolean estVide() {
        return nbVehicules == 0;
    }

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

    public void deposer() {
        if (!estPleine()) {
            nbVehicules++;
            notifyObservers("Dépot effectué");
        }
    }

    public void retirer() {
        if (!estVide()) {
            nbVehicules--;
            notifyObservers("Retrait effectué");
        }
    }

	public int getId() {
		// TODO Auto-generated method stub
		return this.id ;
	}
}