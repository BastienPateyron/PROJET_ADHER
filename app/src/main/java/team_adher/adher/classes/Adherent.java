package team_adher.adher.classes;

/**
 * Created by basti on 12/3/2017.
 */

public class Adherent {
    private int id;
    private String raison_sociale;
    private int num_rue;
    private String nom_rue;
    private int cp;
    private String ville;
    private String nom_responsable;

    public Adherent(){

    }

    public Adherent(int id, String raison_sociale, int num_rue, String nom_rue, int cp, String ville, String nom_responsable) {
        this.id = id;
        this.raison_sociale = raison_sociale;
        this.num_rue = num_rue;
        this.nom_rue = nom_rue;
        this.cp = cp;
        this.ville = ville;
        this.nom_responsable = nom_responsable;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRaison_sociale() {
        return raison_sociale;
    }

    public void setRaison_sociale(String raison_sociale) {
        this.raison_sociale = raison_sociale;
    }

    public int getNum_rue() {
        return num_rue;
    }

    public void setNum_rue(int num_rue) {
        this.num_rue = num_rue;
    }

    public String getNom_rue() {
        return nom_rue;
    }

    public void setNom_rue(String nom_rue) {
        this.nom_rue = nom_rue;
    }

    public int getCp() {
        return cp;
    }

    public void setCp(int cp) {
        this.cp = cp;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getNom_responsable() {
        return nom_responsable;
    }

    public void setNom_responsable(String nom_responsable) {
        this.nom_responsable = nom_responsable;
    }
}
