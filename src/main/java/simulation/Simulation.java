package simulation;

import accessories.Basket;
import controlcenter.ControlCenter;
import station.Station;
import strategy.RoundRobinStrategy;
import users.User;
import factory.BikeFactory;
import vehicule.Vehicle;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Simple simulation for the bike rental system.
 * It creates a few stations and users, and then simulates
 * random automatic actions and user actions, plus redistribution
 * by the control center.
 */
public class Simulation {

    private int currentTime = 0;
    private int timeInterval = 1; // 1 unit per turn

    private final List<Station> stations = new ArrayList<>();
    private final List<User> users = new ArrayList<>();
    private final BikeFactory factory = new BikeFactory();

    private ControlCenter controlCenter;

    private Random random = new Random();

    public Simulation() {
        initialize();
    }

    /**
     * Initialises the simulation: creates stations, users and the control center.
     */
    public void initialize() {

        // --- Control center ---
        // initialise the control center and its redistribution strategy
        controlCenter = new ControlCenter(999);
        controlCenter.setStrategy(new RoundRobinStrategy());

        // --- Create the stations ---
        Station s1 = new Station(1, 10);
        Station s2 = new Station(2, 12);
        Station s3 = new Station(3, 15);

        stations.add(s1);
        stations.add(s2);
        stations.add(s3);

        // --- The control center observes the stations ---
        for (Station s : stations) {
            s.addObserver(controlCenter);
            controlCenter.addStation(s);
        }

        // --- Create a few users ---
        users.add(new User(1, 10.0));
        users.add(new User(2, 5.0));
        users.add(new User(3, 3.0));

        System.out.println("=== Simulation initialised ===");
    }

    // ------------------------------------------------------------------
    //                   RANDOM STATION ACTIONS
    // ------------------------------------------------------------------

    /**
     * Random automatic actions on stations: deposit or rent.
     */
    private void generateRandomActions() {
        for (Station s : stations) {

            int action = random.nextInt(3);  // 0 nothing, 1 deposit, 2 rent

            if (action == 1) {
                Vehicle v = factory.createVehicle(random.nextInt(1000), "classic", 2.0);
                if (random.nextBoolean()) {
                    v = new Basket(v);
                }

                s.deposit(v);
                System.out.println("Station " + s.getId()
                    + " : automatic deposit -> " + v.getDescription()
                    + " (cost = " + v.getCost() + ")");
            } else if (action == 2) {
                s.rent();
                System.out.println("Station " + s.getId() + " : automatic rental");
            }
        }
    }

    // ------------------------------------------------------------------
    //                       USER ACTIONS
    // ------------------------------------------------------------------

    /**
     * User actions: rent or return a bike, depending on their current state.
     */
    private void generateUserActions() {
        for (User u : users) {

            // Case 1: the user already has a bike → chance to return it
            if (u.hasVehicle()) {

                // 50% chance to return
                if (random.nextBoolean()) {

                    // choose a random station
                    Station returnStation = stations.get(random.nextInt(stations.size()));

                    if (!returnStation.isFull()) {
                        // Get the real rented vehicle object from the user
                        Vehicle v = u.returnVehicle();

                        if (v != null) {
                            returnStation.deposit(v);
                            System.out.println("User " + u.getId()
                                + " returns a bike to station " + returnStation.getId());
                        }
                    } else {
                        System.out.println("User " + u.getId()
                            + " wants to return a bike but station "
                            + returnStation.getId() + " is full.");
                    }
                }

            // Case 2: the user has no bike → chance to rent
            } else {

                // 50% chance to attempt a rental
                if (random.nextBoolean()) {

                    // Find stations that are not empty
                    List<Station> candidates = new ArrayList<>();
                    for (Station s : stations) {
                        if (!s.isEmpty()) {
                            candidates.add(s);
                        }
                    }

                    if (candidates.isEmpty()) {
                        System.out.println("User " + u.getId()
                            + " wants to rent but all stations are empty.");
                        continue;
                    }

                    Station departureStation = candidates.get(
                            random.nextInt(candidates.size()));

                    // Withdrawal of a bike
                    Vehicle v = departureStation.rent();

                    if (v == null) {
                        System.out.println("User " + u.getId()
                            + " wants to rent but no bike is actually available.");
                        continue;
                    }

                    double cost = v.getCost();

                    if (u.canPay(cost)) {
                        // User now stores the vehicle, not just an ID
                        u.rentVehicle(v, cost);

                        System.out.println("User " + u.getId()
                            + " rents a bike at station " + departureStation.getId()
                            + " for " + cost + ". Remaining balance: " + u.getBalance());
                    } else {
                        // Not enough money → put the bike back in the station
                        System.out.println("User " + u.getId()
                            + " does not have enough money to rent this bike (cost = "
                            + cost + ", balance = " + u.getBalance() + ")");
                        departureStation.deposit(v);
                    }
                }
            }
        }
    }

    // ------------------------------------------------------------------
    //                     SIMULATION PROGRESSION
    // ------------------------------------------------------------------

    /**
     * Runs one turn of the simulation.
     */
    public void runTurn() {

        System.out.println("\n--- Time = " + currentTime + " ---");

        generateRandomActions();
        generateUserActions();

        // Check potential thefts and advance maintenance
        for (Station s : stations) {
            s.checkPotentialThefts();
            s.advanceMaintenanceForVehicles();
        }

        // Manual redistribution
        controlCenter.checkStationsAndRedistributeIfNecessary();
        currentTime += timeInterval;
    }

    /**
     * Runs the whole simulation for the given number of turns.
     *
     * @param turns number of turns to run
     */
    public void run(int turns) {
        for (int i = 0; i < turns; i++) {
            runTurn();
        }

        System.out.println("\n=== End of simulation ===");
        System.out.println(controlCenter.getHistory());
    }

    // ------------------------------------------------------------------
    //                        GETTERS FOR TESTS
    // ------------------------------------------------------------------

    public List<Station> getStations() {
        return stations;
    }

    public ControlCenter getControlCenter() {
        return controlCenter;
    }

    public int getCurrentTime() {
        return currentTime;
    }

    // ------------------------------------------------------------------
    //                           MAIN (MANUAL TEST)
    // ------------------------------------------------------------------

    public static void main(String[] args) {
        Simulation sim = new Simulation();
        sim.run(5);  // run 5 turns
    }
}
