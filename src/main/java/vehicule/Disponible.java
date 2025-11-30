package main.java.vehicule;

/**
 * Etat indiquant que le véhicule est disponible pour une nouvelle location.
 *
 * Lorsque l'on appelle {@link #emprunter()}, le véhicule change d'état pour
 * devenir loué. Cette opération incrémente aussi le nombre total de locations
 * via {@link AbstractVehicule#incrementerLocationsEtVerifier()} afin de
 * déclencher une mise hors service automatique après un certain nombre de
 * locations.
 */
public class Disponible extends VehiculeState {

    /**
     * Construit un état disponible pour le véhicule donné.
     *
     * @param vehicule le véhicule concerné
     */
    public Disponible(AbstractVehicule vehicule) {
        super(vehicule);
    }

    @Override
    public void emprunter() {
        // Lorsqu'une location commence, on passe d'abord à l'état Loue
        vehicule.setEtat(new Loue(vehicule));
        // Puis on incrémente le compteur. Si la limite est atteinte,
        // incrementerLocationsEtVerifier déclenchera la maintenance et remettra
        // l'état en HorsService, ce qui prévaut sur l'état Loue.
        vehicule.incrementerLocationsEtVerifier();
    }

    @Override
    public void reparer() {
        // Un véhicule disponible n'a pas besoin de réparation
    }

    @Override
    public void voler() {
        // Une fois volé, le véhicule n'est plus disponible
        vehicule.setEtat(new Vole(vehicule));
    }

    @Override
    public void mettreHorsService() {
        vehicule.setEtat(new HorsService(vehicule));
    }

    @Override
    public boolean estDisponible() {
        return true;
    }
}