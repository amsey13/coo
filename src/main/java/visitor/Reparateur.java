package main.java.visitor;
import main.java.vehicule.*;


public class Reparateur implements Visiteur {

    private int id;

    public Reparateur(int id) {
        this.id = id;
    }

    @Override
    public void visiterVehicule(Vehicule v) {
        System.out.println("Réparateur " + id + " inspecte : " + v.getDescription());
        v.devenirHorsService(); 
    }
}