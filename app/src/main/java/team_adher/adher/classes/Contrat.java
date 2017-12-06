package team_adher.adher.classes;

/**
 * Created by basti on 12/4/2017.
 */

public class Contrat{

    private int id;
    private String date_debut;
    private String date_fin;
    private int fk_secteur;

    public Contrat(){

    }

    public Contrat(int id, String date_debut, String date_fin, int fk_secteur) {
        this.id = id;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.fk_secteur = fk_secteur;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDate_debut() {
        return date_debut;
    }

    public void setDate_debut(String date_debut) {
        this.date_debut = date_debut;
    }

    public String getDate_fin() {
        return date_fin;
    }

    public void setDate_fin(String date_fin) {
        this.date_fin = date_fin;
    }

    public int getFk_secteur() {
        return fk_secteur;
    }

    public void setFk_secteur(int fk_secteur) {
        this.fk_secteur = fk_secteur;
    }
}
