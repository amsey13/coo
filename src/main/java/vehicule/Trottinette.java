package main.java.vehicule;

import main.java.visitor.Visiteur;

/**
 * Classe concrète représentant une trottinette. Comme pour les vélos, cette
 * classe n'ajoute pas de comportements supplémentaires au-delà des
 * fonctionnalités fournies par {@link AbstractVehicule}.
 */
public class Trottinette extends AbstractVehicule {

    /**
     * Construit une trottinette avec la description et le coût donnés.
     *
     * @param description description de la trottinette
     * @param cout        coût de base de la trottinette
     */
    public Trottinette(String description, double cout) {
        super(description, cout);
    }

    @Override
    public void accept(Visiteur visiteur) {
        super.accept(visiteur);
    }
}