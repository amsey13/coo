package vehicule;

import visitor.Visitor;

/**
 * Concrete class representing a bike. A bike does not define any additional
 * behaviour compared to the base vehicle; it simply provides a constructor
 * to initialise its description and cost. All features such as state
 * management and maintenance are handled by {@link AbstractVehicle}.
 */
public class Bike extends AbstractVehicle {

    /**
     * Constructs a bike with the given description and base cost.
     *
     * @param description human‑readable description of the bike
     * @param cost        base cost of the bike
     */
    public Bike(String description, double cost) {
        super(description, cost);
    }

    /**
     * The {@code accept} method is inherited from {@link AbstractVehicle} and
     * suffices for bikes. We override the signature here to retain public
     * visibility and allow direct access without a downcast.
     */
    @Override
    public void accept(Visitor visitor) {
        // Double dispatch: call the visitor method specific to this bike
        visitor.visit(this);
    }
}