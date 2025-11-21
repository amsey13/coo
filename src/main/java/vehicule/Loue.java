package main.java.vehicle;

public class Loue implements VehiculeState{
    
    private Velo velo;

    public Loue(Velo v) {
        this.velo = v;
    }

    @Override
    public void emprunter() {}

    @Override
    public void reparer() {
        velo.setEtat(new HorsService(velo));
    }

    @Override
    public void voler() {
        velo.setEtat(new Vole(velo));
    }
}
