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
    private int toursVideConsecutifs = 0;
    private int toursPleinesConsecutifs = 0;

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

    public boolean aBesoinDeVelo() {
        return nbVehicules() < capacite / 4;
    }

    public boolean aTropDeVelos() {
        return nbVehicules() > capacite * 3 / 4;
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
            Vehicule v = e.getVehicule();
            if (v != null && v.estDisponible()) {
                v.enregistrerLocation();
                e.retirer();
                notifyObservers("vehicule_retire");
                return v;
            }
        }
        notifyObservers("tentative de retrait mais station vide");
        return null;
    }

    public void mettreAJourCompteursOccupation() {
        if (estVide()) {
            toursVideConsecutifs++;
            toursPleinesConsecutifs = 0;
        } else if (estPleine()) {
            toursPleinesConsecutifs++;
            toursVideConsecutifs = 0;
        } else {
            // Ni vide ni pleine → on reset les deux compteurs
            toursVideConsecutifs = 0;
            toursPleinesConsecutifs = 0;
        }
    }

    public int getToursVideConsecutifs() {
        return toursVideConsecutifs;
    }

    public int getToursPleinesConsecutifs() {
        return toursPleinesConsecutifs;
    }
    public void verifierVolsPotentiels() {

        // Si la station est vide → rien à voler
        if (estVide()) return;

        // Si plus d'un vélo → aucun vélo n'est "seul"
        if (nbVehicules() > 1) {
            // reset tous les compteurs
            for (Emplacement e : emplacements) {
                e.resetToursSeul();
            }
            return;
        }

        // Ici on sait : EXACTEMENT 1 vélo dans toute la station
        for (Emplacement e : emplacements) {
            if (!e.estLibre()) {

                // Incrementer le compteur
                e.incrementerToursSeul();

                // Le vélo est volé après 2 tours
                if (e.getToursSeul() >= 2) {
                    Vehicule v = e.getVehicule();
                    v.signalerVol();
                    System.out.println("Vélo volé dans station " + id + " : " + v.getDescription());

                    // On enlève définitivement le vélo
                    e.retirer();
                }
            } else {
                e.resetToursSeul();
            }
        }
    }
    public void avancerMaintenanceVehicules() {
        for (Emplacement e : emplacements) {
            Vehicule v = e.getVehicule();
            if (v != null) {
                v.tourDeMaintenance();
            }
        }
    }
}