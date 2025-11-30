package main.java.accessories;

import main.java.vehicule.Vehicule;

public class Electrification extends VehiculeDecorator {
        public Electrification(Vehicule v) {
        super(v);
    }

    @Override
    public String getDescription() {
        return decoratedVehicule.getDescription() + " + Electrification";
    }

    @Override
    public double getCout() {
        return decoratedVehicule.getCout() + 2.5;
    }
}
