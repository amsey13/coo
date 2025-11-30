package main.java.vehicule;

/**
 * Etat indiquant que le véhicule est hors service. Dans cet état, le véhicule
 * ne peut pas être loué. La seule opération ayant un effet est la réparation
 * qui remet le véhicule en {@link Disponible} une fois la maintenance
 * terminée.
 */
public class HorsService extends VehiculeState {

    /**
     * Construit un état hors service pour le véhicule donné.
     *
     * @param vehicule le véhicule hors service
     */
    public HorsService(AbstractVehicule vehicule) {
        super(vehicule);
    }

    @Override
    public void emprunter() {
        // Un véhicule hors service ne peut pas être loué
    }

    @Override
    public void reparer() {
        // Remettre le véhicule en service : retour à l'état disponible
        vehicule.setEtat(new Disponible(vehicule));
    }

    @Override
    public void voler() {
        // Même hors service, un vol passe en état volé
        vehicule.setEtat(new Vole(vehicule));
    }

    @Override
    public void mettreHorsService() {
        // Le véhicule est déjà hors service ; pas de changement
    }
}