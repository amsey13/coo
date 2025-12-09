package test.java.simulation;

import main.java.station.Station;
import main.java.contolcenter.CentreControle;



import simulation.Simulation;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.reflect.Field;
import java.util.List;

public class SimulationTest {

    // ==============================================================
    //                 TEST 1 : INITIALISATION
    // ==============================================================

    @Test
    public void testInitialisationSimulation() throws Exception {

        Simulation sim = new Simulation();

        List<Station> stations = getStations(sim);
        CentreControle centre = getCentre(sim);

        // La simulation doit créer 3 stations
        assertEquals(3, stations.size(),
                "La simulation doit initialiser exactement 3 stations.");

        // Le centre doit être créé
        assertNotNull(centre, "Le centre de contrôle doit être initialisé.");

        // Le centre doit observer les mêmes 3 stations
        assertEquals(3, centre.getStations().size(),
                "Le centre doit avoir enregistré les 3 stations.");
    }


    // ==============================================================
    //             TEST 2 : UN TOUR DE SIMULATION
    // ==============================================================

    @Test
    public void testLancerUnTour() throws Exception {

        Simulation sim = new Simulation();

        int tempsAvant = getTemps(sim);

        sim.lancerTour();

        int tempsApres = getTemps(sim);

        assertEquals(tempsAvant + 1, tempsApres,
                "Après un tour, le temps doit avancer de +1.");
    }


    // ==============================================================
    //             TEST 3 : PLUSIEURS TOURS
    // ==============================================================

    @Test
    public void testLancerPlusieursTours() throws Exception {

        Simulation sim = new Simulation();

        sim.lancer(5); // lance 5 tours

        assertEquals(5, getTemps(sim),
                "Après 5 tours, le temps doit avoir avancé de 5.");
    }


    // ==============================================================
    //      TEST 4 : REDISTRIBUTION forcée (cas contrôlé sans mock)
    // ==============================================================

    @Test
public void testRedistributionDeclenchee() throws Exception {
    Simulation sim = new Simulation();
    Station s1 = sim.getStations().get(0);

    // Forcer la station à être vide pour simuler plusieurs tours consécutifs
    for (int i = 0; i < 3; i++) {
        if (!s1.estVide()) {
            s1.retirer();  // enlever un vélo pour que la station devienne vide
        }
        sim.lancerTour();  // avancer le temps
    }

    // Maintenant, la redistribution devrait avoir été déclenchée
    assertNotEquals("", sim.getCentre().getHistorique(), "La redistribution doit être déclenchée si une station pose problème.");
}



    // ====================================================================
    //              OUTILS : accès aux champs privés via réflexion
    // ====================================================================

    private int getTemps(Simulation sim) throws Exception {
        Field f = Simulation.class.getDeclaredField("tempsCourant");
        f.setAccessible(true);
        return f.getInt(sim);
    }

    private List<Station> getStations(Simulation sim) throws Exception {
        Field f = Simulation.class.getDeclaredField("stations");
        f.setAccessible(true);
        return (List<Station>) f.get(sim);
    }

    private CentreControle getCentre(Simulation sim) throws Exception {
        Field f = Simulation.class.getDeclaredField("centre");
        f.setAccessible(true);
        return (CentreControle) f.get(sim);
    }

    private void setField(Object target, String fieldName, Object value) throws Exception {
        Field f = target.getClass().getDeclaredField(fieldName);
        f.setAccessible(true);
        f.set(target, value);
    }
}
