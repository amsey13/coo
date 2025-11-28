package test.java.utilisateurs;

import main.java.utilisateurs.Utilisateur;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UtilisateursTest {

    @Test
    public void testPeutPayer() {
        Utilisateur u = new Utilisateur(1, 100.0);
        assertTrue(u.peutPayer(50.0));
        assertFalse(u.peutPayer(150.0));
    }

    @Test
    public void testPayer() {
        Utilisateur u = new Utilisateur(1, 100.0);
        u.payer(30.0);
        assertEquals(70.0, u.getSolde());
        // essayer de payer plus que le solde
        u.payer(100.0);
        assertEquals(70.0, u.getSolde()); // solde ne doit pas changer
    }

    @Test
    public void testLouerVehicule() {
        Utilisateur u = new Utilisateur(1, 100.0);
        u.louerVehicule(5, 50.0);
        assertEquals(50.0, u.getSolde());
        assertEquals(5, u.getVehiculeLoueId());

        // essayer de louer un autre véhicule alors qu'il en a déjà un
        u.louerVehicule(6, 30.0);
        assertEquals(50.0, u.getSolde());  // solde ne doit pas changer
        assertEquals(5, u.getVehiculeLoueId());  // id reste le même
    }
    @Test
    public void testRendreVehicule() {
        Utilisateur u = new Utilisateur(1, 100);
        u.louerVehicule(5, 50);
        assertEquals(5, u.getVehiculeLoueId());
        
        u.rendreVehicule();
        assertNull(u.getVehiculeLoueId(), "L'utilisateur ne doit plus avoir de véhicule après l'avoir rendu");
    }

}
