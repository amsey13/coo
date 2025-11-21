package main.java.vehicle;

public class Vole implements VehiculeState {
    
    private Velo velo;

    public Vole(Velo v) {
        this.velo = v;
    }

    @Override
    public void emprunter() {}

    @Override
    public void reparer() {}

    @Override
    public void voler() {}
}
