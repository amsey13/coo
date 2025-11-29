package main.java.visitor;
import main.java.vehicule.Vehicule;

public class Peintre implements Visiteur {

    private int id;

    public Peintre(int id) {
        this.id = id;
    }

    @Override
    public void visiterVehicule(Vehicule v) {
        System.out.println("Peintre " + id + " repeint : " + v.getDescription());
        v.devenirHorsService(); 
    }
}