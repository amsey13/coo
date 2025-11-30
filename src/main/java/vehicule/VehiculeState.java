package main.java.vehicule;

/**
 * Etat abstrait d'un véhicule.
 * <p>
 * Cette classe représente un état dans lequel peut se trouver un véhicule. Chaque état
 * connaît son véhicule associé et délègue les transitions d'un état à l'autre via
 * des appels à {@link AbstractVehicule#setEtat(VehiculeState)}. Les sous-classes doivent
 * implémenter les différentes opérations métier. Par défaut, un véhicule n'est pas
 * disponible tant qu'il ne se trouve pas dans l'état {@link Disponible}.
 */
public abstract class VehiculeState {

    /**
     * Référence vers le véhicule dont on gère l'état.
     */
    protected final AbstractVehicule vehicule;

    /**
     * Construit un état associé au véhicule donné.
     *
     * @param vehicule le véhicule concerné par cet état
     */
    protected VehiculeState(AbstractVehicule vehicule) {
        this.vehicule = vehicule;
    }

    /**
     * Action d'emprunter le véhicule. Pour un véhicule disponible, cette
     * opération déclenche le passage à l'état {@link Loue} et incrémente le
     * nombre de locations. Dans les autres états, cette méthode peut être vide.
     */
    public abstract void emprunter();

    /**
     * Action de réparer le véhicule. Dans l'état hors service, cette opération
     * remet le véhicule en {@link Disponible}. Dans les autres états, cette
     * méthode peut être vide ou déclencher un passage hors service.
     */
    public abstract void reparer();

    /**
     * Action de déclarer le véhicule volé. Cette action passe le véhicule
     * définitivement dans l'état {@link Vole}. Elle ne doit avoir aucun effet
     * si le véhicule est déjà volé.
     */
    public abstract void voler();

    /**
     * Action de mettre hors service le véhicule. Cette action passe le
     * véhicule dans l'état {@link HorsService}. Elle est utilisée par la
     * maintenance et lorsqu'un incident est détecté. En général, elle est
     * appelée depuis le contexte via {@link AbstractVehicule#devenirHorsService()}.
     */
    public abstract void mettreHorsService();

    /**
     * Indique si l'état courant correspond à un véhicule disponible à la location.
     * Par défaut, un état n'est pas disponible.
     *
     * @return vrai si le véhicule est disponible, faux sinon
     */
    public boolean estDisponible() {
        return false;
    }
}