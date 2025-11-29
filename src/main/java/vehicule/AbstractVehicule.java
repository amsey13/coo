
package main.java.vehicule;

import main.java.visitor.Visiteur;

public abstract class AbstractVehicule implements Vehicule {

    protected String description;
    protected double cout;

    // Compteur commun
    protected int nbLocations = 0;
    protected static final int LIMITE_LOCATIONS = 20;
    private int toursHorsServiceRestants = 0;

    protected AbstractVehicule(String description, double cout) {
        this.description = description;
        this.cout = cout;
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
    public void enregistrerLocation() {
        nbLocations++;
        if (nbLocations >= LIMITE_LOCATIONS) {
            System.out.println("Le véhicule " + description
                    + " devient hors service après " + nbLocations + " locations.");
            devenirHorsService(); 
        }
    }
    @Override
    public void tourDeMaintenance() {
        if (toursHorsServiceRestants > 0) {
            toursHorsServiceRestants--;
            if (toursHorsServiceRestants == 0) {
                estDisponible();
            }
        }
    }
    @Override
    public void accept(Visiteur visiteur) {
        visiteur.visiterVehicule(this);
    }
}
