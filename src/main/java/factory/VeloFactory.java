package main.java.factory;

import main.java.vehicule.*;
public class VeloFactory extends VehiculeFactory {
    @Override
    public Vehicule creerVehicule(int id,String type, double prix){
        return new Velo("Velo " + id, prix);
    }
}
