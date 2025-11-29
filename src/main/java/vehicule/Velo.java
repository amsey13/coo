package main.java.vehicule;

import main.java.visitor.Visiteur;


public class Velo extends AbstractVehicule {
    private String description;
    private double cout;
    private VehiculeState etat;

    public Velo(String description, double cout) {
        super(description, cout);
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

   /* @Override
    public void devenirHorsService() {
        etat.reparer();
    }*/

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

    @Override
    public void devenirHorsService() {
        etat.mettreHorsService(); 
    }


}
