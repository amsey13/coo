package simulation;

import main.java.accessories.Panier;
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
                if (random.nextBoolean()) {
                    v = new Panier(v);
                }

                s.deposer(v);
                System.out.println("Station " + s.getId()
                    + " : dépôt auto -> " + v.getDescription()
                    + " (coût = " + v.getCout() + "€)");
            }
            else if (action == 2) {
                s.retirer();
                System.out.println("Station " + s.getId() + " : retrait auto");
            }
        }
    }
    // Simulation.java

    // --- Actions des utilisateurs : louer / rendre ---
    private void genererActionsUtilisateurs() {
        for (Utilisateur u : utilisateurs) {

            // Cas 1 : l'utilisateur a déjà un vélo → il a une chance de le rendre
            if (u.getVehiculeLoueId() != null) {

                // 50% de chances de rendre
                if (random.nextBoolean()) {

                    // choisir une station aléatoire
                    Station stationRetour = stations.get(random.nextInt(stations.size()));

                    if (!stationRetour.estPleine()) {
                        // Simplification : on ne stocke pas l'objet Vélo, on remet un nouveau
                        Vehicule v = factory.creerVehicule(
                                random.nextInt(1000), "classique", 2.0);

                        if (random.nextBoolean()) {
                            v = new Panier(v);
                        }

                        stationRetour.deposer(v);
                        u.rendreVehicule();
                        System.out.println("Utilisateur " + u.getId()
                            + " rend un vélo à la station " + stationRetour.getId());
                    } else {
                        System.out.println("Utilisateur " + u.getId()
                            + " veut rendre un vélo mais la station "
                            + stationRetour.getId() + " est pleine.");
                    }
                }

            // Cas 2 : l'utilisateur n'a pas de vélo → il a une chance de louer
            } else {

                // 50% de chances d'essayer de louer
                if (random.nextBoolean()) {

                    // On cherche les stations non vides
                    List<Station> candidates = new ArrayList<>();
                    for (Station s : stations) {
                        if (!s.estVide()) {
                            candidates.add(s);
                        }
                    }

                    if (candidates.isEmpty()) {
                        System.out.println("Utilisateur " + u.getId()
                            + " veut louer mais toutes les stations sont vides.");
                        continue;
                    }

                    Station stationDepart = candidates.get(
                            random.nextInt(candidates.size()));

                    // Retrait d'un vélo → déclenche enregistrerLocation()
                    Vehicule v = stationDepart.retirer();

                    if (v == null) {
                        System.out.println("Utilisateur " + u.getId()
                            + " veut louer mais aucun vélo disponible en pratique.");
                        continue;
                    }

                    double cout = v.getCout();

                    if (u.peutPayer(cout)) {
                        // Id "virtuel" pour suivre la location
                        int idLocation = v.hashCode();
                        u.louerVehicule(idLocation, cout);

                        System.out.println("Utilisateur " + u.getId()
                            + " loue un vélo à la station " + stationDepart.getId()
                            + " pour " + cout + "€. Solde restant : " + u.getSolde());
                    } else {
                        // Pas assez d'argent → on remet le vélo dans la station
                        System.out.println("Utilisateur " + u.getId()
                            + " n'a pas assez d'argent pour louer ce vélo (coût = "
                            + cout + "€, solde = " + u.getSolde() + "€)");
                        stationDepart.deposer(v);
                    }
                }
            }
        }
    }

    // --- Un tour de simulation ---
    public void lancerTour() {

        System.out.println("\n--- Temps = " + tempsCourant + " ---");
        
        genererActionsAleatoires();

        genererActionsUtilisateurs();

        // Vérification des vols potentiels
        for (Station s : stations) {
            s.verifierVolsPotentiels();
            s.avancerMaintenanceVehicules();
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


    public List<Station> getStations() {
    return stations;
    }

    public CentreControle getCentre() {
        return centre;
    }

}

