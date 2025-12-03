package test.java.vehicule;
import main.java.vehicule.Vehicule;
import main.java.vehicule.Velo;
import main.java.visitor.Visiteur;
import org.junit.jupiter.api.Test;                     
import static org.junit.jupiter.api.Assertions.*;


 @Test
    public void testVisitor() {
        Velo v = new Velo("Vélo classique", 1.0);

        class VisitorTest implements Visiteur {
            boolean visited = false;

            @Override
            public void visiterVehicule(Vehicule vehicule) {
                visited = true;
            }
        }

        VisitorTest visitor = new VisitorTest();
        v.accept(visitor);

        assertTrue(visitor.visited, "Le visiteur doit avoir visité le vélo");
    }
