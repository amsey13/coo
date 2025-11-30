package main.java.vehicule;

/**
 * Etat indiquant que le véhicule a été volé. Une fois volé, aucune
 * opération (emprunter, réparer ou mettre hors service) ne modifie
 * l'état : le véhicule reste volé de façon permanente.
 */
public class Vole extends VehiculeState {

    /**
     * Construit un état volé pour le véhicule donné.
     *
     * @param vehicule le véhicule volé
     */
    public Vole(AbstractVehicule vehicule) {
        super(vehicule);
    }

    @Override
    public void emprunter() {
        // impossible de louer un véhicule volé
    }

    @Override
    public void reparer() {
        // un véhicule volé ne peut pas être réparé
    }

    @Override
    public void voler() {
        // déjà volé : rien à faire
    }

    @Override
    public void mettreHorsService() {
        // déjà volé : rien à faire
    }
}