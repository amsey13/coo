package main.java.simulation;

import main.java.contolcenter.*;
import main.java.station.Station;
import main.java.utilisateurs.Utilisateur;
import main.java.strategy.RoundRobinStrategy;
import main.java.factory.VeloFactory;
import main.java.vehicule.Vehicule;


import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Simulation {

    private int tempsCourant = 0;
    private int intervalleTemps = 1; // 1 unité par tour

    private List<Station> stations = new ArrayList<>();
    private List<Utilisateur> utilisateurs = new ArrayList<>();
    private VeloFactory factory = new VeloFactory();

    private CentreControle centre;

    private Random random = new Random();

    public Simulation() {
        initialiser();
    }

    public void initialiser() {

        // --- Centre de contrôle ---
        centre = new CentreControle(999);
        centre.setStrategy(new RoundRobinStrategy());

        // --- Création des stations ---
        Station s1 = new Station(1, 10);
        Station s2 = new Station(2, 12);
        Station s3 = new Station(3, 15);

        stations.add(s1);
        stations.add(s2);
        stations.add(s3);

        // --- Le centre observe les stations ---
        for (Station s : stations) {
            s.addObserver(centre);
            centre.ajouterStation(s);
        }

        // --- Création de quelques utilisateurs ---
        utilisateurs.add(new Utilisateur(1, 10.0));
        utilisateurs.add(new Utilisateur(2, 5.0));
        utilisateurs.add(new Utilisateur(3, 3.0));

        System.out.println("=== Simulation initialisée ===");
    }

    // --- Action aléatoire : dépôt ou retrait ---
    private void genererActionsAleatoires() {
        for (Station s : stations) {

            int action = random.nextInt(3);  // 0 rien, 1 dépôt, 2 retrait

            if (action == 1) {
                Vehicule v = factory.creerVehicule(random.nextInt(1000), "classique", 2.0);
                s.deposer(v);
                System.out.println("Station " + s.getId() + " : dépôt auto");
            }
            else if (action == 2) {
                s.retirer();
                System.out.println("Station " + s.getId() + " : retrait auto");
            }
        }
    }

    // --- Un tour de simulation ---
    public void lancerTour() {

        System.out.println("\n--- Temps = " + tempsCourant + " ---");

        genererActionsAleatoires();
        // Vérification des vols potentiels
        for (Station s : stations) {
            s.verifierVolsPotentiels();
        }
        // Redistribution manuelle simple
        centre.verifierStationsEtRedistribuerSiNecessaire();

        tempsCourant += intervalleTemps;
    }

    // --- Lancement de toute la simulation ---
    public void lancer(int nbTours) {
        for (int i = 0; i < nbTours; i++) {
            lancerTour();
        }

        System.out.println("\n=== Fin de simulation ===");
        System.out.println(centre.getHistorique());
    }

    // --- MAIN pour tester ---
    public static void main(String[] args) {
        Simulation sim = new Simulation();
        sim.lancer(5);  // exécute 5 tours
    }
}

