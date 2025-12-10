package vehicule;

import visitor.Visitor;

/**
 * Concrete class representing a scooter. A scooter provides no extra
 * behaviour beyond {@link AbstractVehicle}; it serves to distinguish
 * scooters from bikes for the visitor pattern.
 */
public class Scooter extends AbstractVehicle {

    /**
     * Constructs a scooter with the given description and base cost.
     *
     * @param description description of the scooter
     * @param cost        base cost of the scooter
     */
    public Scooter(String description, double cost) {
        super(description, cost);
    }

    @Override
    public void accept(Visitor visitor) {
        // Double dispatch: call the visitor method specific to this scooter
        visitor.visit(this);
    }
}