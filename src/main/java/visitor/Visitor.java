package visitor;

import vehicule.Vehicle;
import vehicule.Bike;
import vehicule.Scooter;

/**
 * Visitor interface defining operations to be performed on different types of
 * vehicles. This interface uses overloaded {@code visit} methods to enable
 * double dispatch: the appropriate method is chosen based on the concrete
 * runtime type of the vehicle. Concrete visitors should implement these
 * methods to provide specific behaviour for each supported vehicle type. A
 * generic {@code visitVehicle} method is provided as a fallback for types
 * that do not have a dedicated method.
 */
public interface Visitor {
    /**
     * Fallback visit operation invoked when no more specific method is available.
     *
     * @param v the vehicle to visit
     */
    void visitVehicle(Vehicle v);

    /**
     * Visit operation for a {@link Bike} instance.
     *
     * @param v the bike to visit
     */
    void visit(Bike v);

    /**
     * Visit operation for a {@link Scooter} instance.
     *
     * @param s the scooter to visit
     */
    void visit(Scooter s);
}