package main.java.vehicle;

import main.java.visitor.Visiteur;

public interface Vehicule {
    String getDescription();
    double getCout();
    boolean estDisponible();
    void signalerVol();
    void devenirHorsService();
    void accept(Visiteur visiteur);
}
