package utilisateurs;

/**
 * Represents a user of the bike sharing system. A user has an identifier,
 * a balance from which rental costs are deducted and at most one rented
 * vehicle tracked by its identifier. All method names are in English to
 * avoid mixing languages in the API.
 */
public class User {
    private final int id;
    private double balance;
    private Integer rentedVehicleId; // null if no vehicle rented

    /**
     * Constructs a user with the given identifier and starting balance.
     *
     * @param id      the unique identifier of the user
     * @param balance the initial balance of the user
     */
    public User(int id, double balance) {
        this.id = id;
        this.balance = balance;
        this.rentedVehicleId = null;
    }

    /**
     * Returns whether the user can pay the specified amount.
     *
     * @param amount the amount to check
     * @return {@code true} if the user has sufficient balance, otherwise {@code false}
     */
    public boolean canPay(double amount) {
        return balance >= amount;
    }

    /**
     * Deducts the specified amount from the user's balance if possible.
     *
     * @param amount the amount to deduct
     */
    public void pay(double amount) {
        if (canPay(amount)) {
            balance -= amount;
        }
    }

    /**
     * Returns whether the user currently has a rented vehicle.
     *
     * @return {@code true} if a vehicle is rented, otherwise {@code false}
     */
    public boolean hasVehicle() {
        return rentedVehicleId != null;
    }

    /**
     * Attempts to rent a vehicle for the user. If the user does not already
     * have a vehicle and has sufficient balance, the cost is deducted and
     * the rented vehicle identifier is recorded. Informational messages are
     * printed to standard output to trace the operation.
     *
     * @param vehicleId the identifier of the vehicle being rented
     * @param cost      the rental cost
     */
    public void rentVehicle(int vehicleId, double cost) {
        if (!hasVehicle() && canPay(cost)) {
            pay(cost);
            rentedVehicleId = vehicleId;
            System.out.println("User " + id + " has rented vehicle " + vehicleId);
        } else {
            System.out.println("Rental not possible for user " + id);
        }
    }

    /**
     * Returns the vehicle currently rented by the user. If no vehicle is
     * rented, an informational message is printed.
     */
    public void returnVehicle() {
        if (hasVehicle()) {
            System.out.println("User " + id + " has returned vehicle " + rentedVehicleId);
            rentedVehicleId = null;
        } else {
            System.out.println("No vehicle to return");
        }
    }

    /**
     * Returns the user's current balance.
     *
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * Returns the user's identifier.
     *
     * @return the identifier
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the identifier of the currently rented vehicle, or {@code null}
     * if none.
     *
     * @return the rented vehicle identifier or {@code null}
     */
    public Integer getRentedVehicleId() {
        return rentedVehicleId;
    }
}