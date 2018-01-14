package team_adher.adher.classes;

/**
 * Created by basti on 12/16/2017.
 */

public class ContratService {
    private int id;
    private Secteur secteur;
    private Adherent adherent;
    private Concerner concerner;
    private String date_debut;
    private String date_fin;
    private double tarif_ht;

    public  ContratService(){

    }

    public ContratService(int id, Secteur secteur, Adherent adherent, String date_debut, String date_fin, double tarif_ht) {
        this.id = id;
        this.secteur = secteur;
        this.adherent = adherent;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
        this.tarif_ht = tarif_ht;
    }


    @Override
    public String toString(){
        String str = secteur.getNumero() + " - " + date_debut + " - " + date_fin;
        return str;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Secteur getSecteur() {
        return secteur;
    }

    public void setSecteur(Secteur secteur) {
        this.secteur = secteur;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
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

    public double getTarif_ht() {
        return tarif_ht;
    }

    public void setTarif_ht(double tarif_ht) {
        this.tarif_ht = tarif_ht;
    }
}
