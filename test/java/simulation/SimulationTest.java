package simulation;

import station.Station;

import org.junit.jupiter.api.Test;

import controlcenter.ControlCenter;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for the {@link Simulation} class.
 * Verifies initialisation, time progression and basic wiring
 * with the control center.
 */
public class SimulationTest {

    // ==============================================================
    //                 TEST 1 : INITIALISATION
    // ==============================================================

    @Test
    public void testSimulationInitialisation() {
        Simulation sim = new Simulation();

        List<Station> stations = sim.getStations();
        ControlCenter center = sim.getControlCenter();

        // The simulation must create 3 stations
        assertEquals(3, stations.size(),
                "The simulation must initialise exactly 3 stations.");

        // The control center must be created
        assertNotNull(center, "The control center must be initialised.");

        // The control center must observe the same 3 stations
        assertEquals(3, center.getStations().size(),
                "The control center must have registered the 3 stations.");
    }

    // ==============================================================
    //             TEST 2 : ONE TURN OF SIMULATION
    // ==============================================================

    @Test
    public void testRunOneTurn() {
        Simulation sim = new Simulation();

        int timeBefore = sim.getCurrentTime();

        sim.runTurn();

        int timeAfter = sim.getCurrentTime();

        assertEquals(timeBefore + 1, timeAfter,
                "After one turn, time must advance by +1.");
    }

    // ==============================================================
    //             TEST 3 : SEVERAL TURNS
    // ==============================================================

    @Test
    public void testRunSeveralTurns() {
        Simulation sim = new Simulation();

        sim.run(5); // run 5 turns

        assertEquals(5, sim.getCurrentTime(),
                "After 5 turns, time must have advanced by 5.");
    }

    // ==============================================================
    //   TEST 4 : REDISTRIBUTION INDIRECTLY TRIGGERED (SMOKE TEST)
    // ==============================================================

    @Test
    public void testRedistributionTriggered() {
        Simulation sim = new Simulation();
        ControlCenter center = sim.getControlCenter();

        // We run several turns to give the control center a chance
        // to detect problematic stations and redistribute.
        sim.run(3);

        String history = center.getHistory();

        // We do not assert a specific message here, only that
        // some redistribution (or at least some action) has been recorded.
        assertNotNull(history, "History string must not be null.");
        assertFalse(history.isEmpty(),
                "The control center history should not be empty after several turns.");
    }
}
