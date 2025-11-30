package main.java.vehicule;

/**
 * Etat indiquant que le véhicule est actuellement loué par un utilisateur.
 *
 * Dans cet état, il n'est pas possible de le relouer ou de le déclarer
 * disponible. Une réparation pendant la location envoie le véhicule hors
 * service (cas d'une panne pendant l'utilisation). A la restitution, la
 * transition vers l'état {@link Disponible} est gérée par l'appel
 * {@link AbstractVehicule#retourDeLocation()}, mais dans cette implémentation
 * simplifiée les stations appellent directement {@link AbstractVehicule#setEtat(VehiculeState)}
 * lorsqu'un retour intervient. Cet état se contente donc de gérer les
 * transitions initiées par le réparateur.
 */
public class Loue extends VehiculeState {

    /**
     * Construit un état Loué pour le véhicule donné.
     *
     * @param vehicule le véhicule en location
     */
    public Loue(AbstractVehicule vehicule) {
        super(vehicule);
    }

    @Override
    public void emprunter() {
        // Déjà loué : on ignore l'opération
    }

    @Override
    public void reparer() {
        // Si une réparation est demandée alors que le véhicule est loué,
        // on considère qu'une panne est survenue et on passe hors service
        vehicule.setEtat(new HorsService(vehicule));
    }

    @Override
    public void voler() {
        vehicule.setEtat(new Vole(vehicule));
    }

    @Override
    public void mettreHorsService() {
        vehicule.setEtat(new HorsService(vehicule));
    }
}