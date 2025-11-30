package main.java.vehicule;

import main.java.visitor.Visiteur;

public interface Vehicule {
    String getDescription();
    double getCout();
    boolean estDisponible();
    void signalerVol();
    void enregistrerLocation();
    void devenirHorsService();
    void accept(Visiteur visiteur);
    void tourDeMaintenance();

    /**
     * Démarre une période de maintenance durant laquelle le véhicule ne peut
     * plus être loué. La maintenance dure un nombre de tours indiqué et
     * déclenche l'appel à {@link VehiculeState#reparer()} lorsque le délai
     * est expiré.
     *
     * @param nbTours nombre d'intervalles de temps pendant lesquels le
     *                véhicule reste hors service
     */
    void demarrerMaintenance(int nbTours);
}




