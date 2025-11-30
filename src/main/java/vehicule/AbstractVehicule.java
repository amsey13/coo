
package main.java.vehicule;

import main.java.visitor.Reparateur;
import main.java.visitor.Visiteur;

public abstract class AbstractVehicule implements Vehicule {

    /**
     * Description du véhicule (par exemple "Vélo classique" ou "Trottinette électrique").
     */
    protected String description;

    /**
     * Coût de base du véhicule (hors accessoires). Utilisé pour la facturation.
     */
    protected double cout;

    /**
     * Etat courant du véhicule (pattern State). Toutes les transitions entre
     * états doivent passer par ce champ via {@link #setEtat(VehiculeState)}.
     */
    protected VehiculeState etat;

    /**
     * Compteur de locations effectuées. Après un certain nombre de locations,
     * le véhicule est mis hors service pour maintenance.
     */
    protected int nbLocations = 0;

    /**
     * Limite de locations avant de passer automatiquement en maintenance.
     */
    protected static final int LIMITE_LOCATIONS = 20;

    /**
     * Nombre de tours restants pendant lesquels le véhicule est bloqué en maintenance.
     */
    private int toursHorsServiceRestants = 0;

    /**
     * Construit un véhicule abstrait avec la description et le coût donnés. Le
     * véhicule commence dans l'état disponible.
     *
     * @param description description du véhicule
     * @param cout        coût de base du véhicule
     */
    protected AbstractVehicule(String description, double cout) {
        this.description = description;
        this.cout = cout;
        // Etat initial : le véhicule est disponible à la location
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

    /**
     * Retourne l'état courant du véhicule. Méthode utilitaire utilisée
     * principalement dans les tests pour inspecter l'état interne.
     *
     * @return l'état courant
     */
    public VehiculeState getEtat() {
        return etat;
    }

    /**
     * Met à jour l'état du véhicule. Cette méthode est appelée par les
     * différentes implémentations d'état lors des transitions.
     *
     * @param nouvelEtat le nouvel état à appliquer au véhicule
     */
    public void setEtat(VehiculeState nouvelEtat) {
        this.etat = nouvelEtat;
    }

    @Override
    public boolean estDisponible() {
        return etat.estDisponible();
    }

    @Override
    public void signalerVol() {
        etat.voler();
    }

    @Override
    public void devenirHorsService() {
        etat.mettreHorsService();
    }

    @Override
    public void enregistrerLocation() {
        // L'état gère l'incrémentation et le passage en Loue
        etat.emprunter();
    }

    /**
     * Incrémente le nombre de locations et vérifie si le véhicule atteint la
     * limite de locations. Si la limite est atteinte, la maintenance est
     * déclenchée automatiquement pour un intervalle de temps.
     */
    public void incrementerLocationsEtVerifier() {
        nbLocations++;
        if (nbLocations >= LIMITE_LOCATIONS) {
            System.out.println("Le véhicule " + description + " devient hors service après " + nbLocations + " locations.");
            // On démarre une maintenance de 1 tour
            Reparateur rep = new Reparateur(1); 
            accept(rep);
        }
    }

    @Override
    public void demarrerMaintenance(int nbTours) {
        this.toursHorsServiceRestants = nbTours;
        // Pendant la maintenance on est hors service
        etat.mettreHorsService();
    }

    @Override
    public void tourDeMaintenance() {
        if (toursHorsServiceRestants > 0) {
            toursHorsServiceRestants--;
            if (toursHorsServiceRestants == 0) {
                // Fin de maintenance : on répare et revient en service
                etat.reparer();
            }
        }
    }

    @Override
    public void accept(Visiteur visiteur) {
        visiteur.visiterVehicule(this);
    }
}
