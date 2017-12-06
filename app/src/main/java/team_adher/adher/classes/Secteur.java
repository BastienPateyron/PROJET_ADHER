package team_adher.adher.classes;

/**
 * Created by basti on 12/2/2017.
 */

public class Secteur {

    private int id;
    private int numero;
    private String nom;

    public Secteur(){ // Secteur vide pour recevoir un Retrieve

    }

    public Secteur(int id, int numero, String nom) {
        this.id = id;
        this.numero = numero;
        this.nom = nom;
    }

    @Override
    public String toString(){
        return nom; /* Retourne uniquement le nom du secteur */
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}


