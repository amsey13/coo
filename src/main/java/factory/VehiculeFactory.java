package main.java.factory;

import main.java.vehicule.*;
public abstract class VehiculeFactory {
    public abstract Vehicule creerVehicule(int id, String Type,double prix);
}
