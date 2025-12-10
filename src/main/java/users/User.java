package users;

import vehicule.Vehicle;

/**
 * Represents a user of the bike sharing system. A user has an identifier,
 * a balance from which rental costs are deducted and at most one rented
 * vehicle tracked by its reference. All method names are in English to
 * avoid mixing languages in the API.
 */
public class User {
    private final int id;
    private double balance;
    /**
     * The vehicle currently rented by this user, or {@code null} if none.
     */
    private Vehicle rentedVehicle; // null if no vehicle rented

    /**
     * Constructs a user with the given identifier and starting balance.
     *
     * @param id      the unique identifier of the user
     * @param balance the initial balance of the user
     */
    public User(int id, double balance) {
        this.id = id;
        this.balance = balance;
        this.rentedVehicle = null;
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
        return rentedVehicle != null;
    }

    /**
     * Attempts to rent a vehicle for the user. If the user does not already
     * have a vehicle and has sufficient balance, the cost is deducted and
     * the rented vehicle reference is recorded. The vehicle's rental counter
     * is also updated.
     *
     * @param vehicle the vehicle being rented (must not be {@code null})
     * @param cost    the rental cost
     */
    public void rentVehicle(Vehicle vehicle, double cost) {
        if (vehicle == null) {
            throw new IllegalArgumentException("vehicle must not be null");
        }

        if (!hasVehicle() && canPay(cost)) {
            pay(cost);
            this.rentedVehicle = vehicle;

            // Important: update the vehicle's rental count / state
            vehicle.recordRental();

            System.out.println("User " + id + " has rented " + vehicle.getDescription());
        } else {
            System.out.println("Rental not possible for user " + id);
        }
    }

    /**
     * Returns the vehicle currently rented by the user and clears the rental.
     * If no vehicle is rented, an informational message is printed and
     * {@code null} is returned.
     *
     * @return the rented vehicle, or {@code null} if none
     */
    public Vehicle returnVehicle() {
        if (!hasVehicle()) {
            System.out.println("No vehicle to return");
            return null;
        }

        Vehicle v = this.rentedVehicle;
        this.rentedVehicle = null;
        System.out.println("User " + id + " has returned vehicle " + v.getDescription());
        return v;
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
     * Returns the vehicle currently rented, or {@code null} if none.
     * This replaces the previous integer identifier.
     *
     * @return the rented vehicle or {@code null}
     */
    public Vehicle getRentedVehicle() {
        return rentedVehicle;
    }
}
