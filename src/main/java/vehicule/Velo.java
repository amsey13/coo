package main.java.vehicle;

import main.java.visitor.Visiteur;

public class Velo implements Vehicule {
    private String description;
    private double cout;
    private VehiculeState etat;

    public Velo(String description, double cout) {
        this.description = description;
        this.cout = cout;
        this.etat = new Disponible(this);
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getCout() {
        return cout;
    }

    @Override
    public boolean estDisponible() {
        return etat instanceof Disponible;
    }

    @Override
    public void signalerVol() {
        etat.voler();
    }

    @Override
    public void devenirHorsService() {
        etat.reparer();
    }

    @Override
    public void accept(Visiteur visiteur) {
        visiteur.visiterVehicule(this);
    }

    public void setEtat(VehiculeState e) {
        this.etat = e;
    }


    public VehiculeState getEtat() {
    return etat;
}

}
