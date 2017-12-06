package team_adher.adher.classes;

/**
 * Created by basti on 12/4/2017.
 */

public class Faire {
    private int id_activite;
    private int id_adherent;

    public Faire(int id_activite, int id_adherent) {
        this.id_activite = id_activite;
        this.id_adherent = id_adherent;
    }

    public int getId_activite(){
        return this.id_activite;
    }

    public void setId_activite(int id_activite) {
        this.id_activite = id_activite;
    }

    public int getId_adherent() {
        return id_adherent;
    }

    public void setId_adherent(int id_adherent) {
        this.id_adherent = id_adherent;
    }
}
