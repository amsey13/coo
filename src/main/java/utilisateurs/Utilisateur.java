package main.java.utilisateurs;

public class Utilisateur {
    private int id ;
    private double solde;
    private Integer vehiculeLoueId; // si aucun vélo loué

    public Utilisateur ( int id, double solde){
        this.id = id ;
        this.solde = solde;
        this.vehiculeLoueId = null;
    }
    public boolean peutPayer(double montant){
        return solde >= montant;
    }
    public void payer(double montant){
        if (peutPayer(montant)){
            solde -= montant;
        }
    }
    public boolean aUnVehicule(){
        return vehiculeLoueId != null;
    }
    public void louerVehicule (int vehiculeId, double cout){
        if (!aUnVehicule() && peutPayer(cout)){
            payer(cout);
            vehiculeLoueId = vehiculeId;
            System.out.println("Utilisateur" + id + "a loué le vehicule" + vehiculeId);
        }else{
            System.out.println("location impossible pour l'utiilisateur " + id);
        }
    }
    public void rendreVehicule (){
        if(aUnVehicule()){
            System.out.println("Utilisateur " + id +" a rendu le vehicule " + vehiculeLoueId);
             vehiculeLoueId = null;
        }else{
            System.out.println( "Aucun vehicule a rendre");
        }
    }
    public double getSolde(){
        return solde;
    }
    public int getId(){
        return id;
    }
    public int getVehiculeLoueId(){
        return vehiculeLoueId;
    }
}
