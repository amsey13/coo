package main.java.accessories;

import main.java.vehicule.Vehicule;

public class Panier extends VehiculeDecorator {
        public Panier(Vehicule v) {
        super(v);
    }

    @Override
    public String getDescription() {
        return decoratedVehicule.getDescription() + " + Panier";
    }

    @Override
    public double getCout() {
        return decoratedVehicule.getCout() + 1.5;
    }
}
