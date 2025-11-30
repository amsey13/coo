
package main.java.vehicule;

import main.java.visitor.Visiteur;

/**
 * Classe concrète représentant un vélo. Un vélo ne définit pas de
 * comportement supplémentaire par rapport au véhicule de base, il se
 * contente de fournir un constructeur pour initialiser sa description et
 * son coût. Toutes les fonctionnalités (état, maintenance, etc.) sont
 * gérées par {@link AbstractVehicule}.
 */
public class Velo extends AbstractVehicule {

    /**
     * Construit un vélo avec la description et le coût donnés.
     *
     * @param description description du vélo
     * @param cout        coût de base du vélo
     */
    public Velo(String description, double cout) {
        super(description, cout);
    }

    /**
     * La méthode accept provient d'AbstractVehicule et suffit pour les vélos.
     * On redéfinit simplement la signature pour conserver la visibilité
     * publique et permettre l'accès direct sans downcast.
     */
    @Override
    public void accept(Visiteur visiteur) {
        super.accept(visiteur);
    }
}