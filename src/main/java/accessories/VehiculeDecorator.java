package main.java.accessories;

import main.java.visitor.Visiteur;
import main.java.vehicule.*;
import main.java.vehicule.Vehicule;

public class VehiculeDecorator implements Vehicule {
     protected Vehicule decoratedVehicule;

    public VehiculeDecorator(Vehicule v) {
        this.decoratedVehicule = v;
    }

    @Override
    public String getDescription() {
        return decoratedVehicule.getDescription();
    }

    @Override
    public double getCout() {
        return decoratedVehicule.getCout();
    }

    @Override
    public boolean estDisponible() {
        return decoratedVehicule.estDisponible();
    }

    @Override
    public void signalerVol() {
        decoratedVehicule.signalerVol();
    }

    @Override
    public void devenirHorsService() {
        decoratedVehicule.devenirHorsService();
    }

    @Override
    public void accept(Visiteur visiteur) {
        decoratedVehicule.accept(visiteur);
    }
}
