package main.java.accessories;

import main.java.vehicule.Vehicule;

public class Pliage extends VehiculeDecorator {
        public Pliage(Vehicule v) {
        super(v);
    }

    @Override
    public String getDescription() {
        return decoratedVehicule.getDescription() + " + Pliage";
    }

    @Override
    public double getCout() {
        return decoratedVehicule.getCout() + 5;
    }
}
