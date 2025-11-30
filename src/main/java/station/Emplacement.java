package main.java.station;

import main.java.vehicule.*;
public class Emplacement {

    private int id;
    private Vehicule vehicule;
    private int toursSeul = 0;

    public Emplacement(int id) {
        this.id = id;
    }

    public boolean estLibre() {
        return vehicule == null;
    }

    public void deposer(Vehicule v) {
        if (estLibre()) {
            this.vehicule = v;
        }
    }
    private void liberer() {
        this.vehicule = null;
        this.toursSeul = 0;
    }

    public Vehicule retirer() {
        if (!estLibre()) {
            Vehicule v = vehicule;
             liberer();
            return v;
        }
        return null;
    }

    public Vehicule getVehicule() {
        return vehicule;
    }

    public int getId() {
        return id;
    }

    public void incrementerToursSeul() {
        toursSeul++;
    }

    public void resetToursSeul() {
        toursSeul = 0;
    }

    public int getToursSeul() {
        return toursSeul;
    }
}