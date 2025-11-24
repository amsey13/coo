package main.java.vehicule;

public class Disponible implements VehiculeState {
        private Velo velo;

    public Disponible(Velo v) {
        this.velo = v;
    }

    @Override
    public void emprunter() {
        velo.setEtat(new Loue(velo));
    }

    @Override
    public void reparer() {}

    @Override
    public void voler() {
        velo.setEtat(new Vole(velo));
    }

    @Override
    public void mettreHorsService() {
        velo.setEtat(new HorsService(velo));
    }


}
