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

    public Vehicule retirerSansEnregistrement() {
        for (Emplacement e : emplacements) {
            Vehicule v = e.getVehicule();
            if (v != null && v.estDisponible()) {
                // ici on ne fait PAS v.enregistrerLocation()
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
    /**
     * Remet à zéro le compteur toursSeul de tous les emplacements.
     */
    private void reinitialiserTousLesCompteursToursSeul() {
        for (Emplacement e : emplacements) {
            e.resetToursSeul();
        }
    }

    /**
     * Traite le cas où la station contient exactement un véhicule.
     * On incrémente le compteur de l'emplacement occupé, on remet à zéro
     * celui des emplacements libres, et on détecte un éventuel vol.
     */
    private void traiterStationAvecUnSeulVehicule() {
        for (Emplacement e : emplacements) {
            if (!e.estLibre()) {
                gererEmplacementOccupeSeul(e);
            } else {
                e.resetToursSeul();
            }
        }
    }

    /**
     * Incrémente le compteur pour l'emplacement occupé et déclenche un vol
     * si le véhicule est resté seul trop longtemps.
     */
    private void gererEmplacementOccupeSeul(Emplacement e) {
        e.incrementerToursSeul();

        if (e.getToursSeul() >= 2) {
            Vehicule v = e.getVehicule();
            if (v != null) {
                v.signalerVol();
            }
            // On enlève définitivement le véhicule volé
            e.retirer();
        }
    }

    public void verifierVolsPotentiels() {

        // 1) Cas station vide : aucun vélo à surveiller
        if (estVide()) {
            reinitialiserTousLesCompteursToursSeul();
            return;
        }

        // 2) Cas avec plusieurs vélos : aucun n'est "seul"
        if (nbVehicules() > 1) {
            reinitialiserTousLesCompteursToursSeul();
            return;
        }

        // 3) Cas avec exactement un vélo dans la station
        traiterStationAvecUnSeulVehicule();
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