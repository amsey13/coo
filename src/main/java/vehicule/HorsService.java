package main.java.vehicule;

public class HorsService implements VehiculeState {
    private Velo velo;

    public HorsService(Velo v) {
        this.velo = v;
    }

    @Override
    public void emprunter() {}

    @Override
    public void reparer() {
        velo.setEtat(new Disponible(velo));
    }

    @Override
    public void voler() {
        velo.setEtat(new Vole(velo));
    }

    @Override
    public void mettreHorsService() {
        // déjà hors-service, rien à faire
    }

}
