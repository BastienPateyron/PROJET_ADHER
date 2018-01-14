package team_adher.adher.classes;

/**
 * Created by pezed on 20/12/17.
 */

public class Intervention {
    private int id;
    private Secteur secteur;
    private Activite activite;
    private Adherent adherent;
    private Client client;
    private String date_debut;
    private String date_fin;

    public Intervention (){

    }

    public Intervention(int id, Secteur secteur, Activite activite, Adherent adherent, Client client, String date_debut, String date_fin) {
        this.id = id;
        this.secteur = secteur;
        this.activite = activite;
        this.adherent = adherent;
        this.client = client;
        this.date_debut = date_debut;
        this.date_fin = date_fin;
    }
    public String toString(){
        String str = adherent.getRaison_sociale() + " : " + client.getNom() + " " + client.getPrenom() + " - " + secteur.getNumero() +" - " + activite.getNom() + " \n " + date_debut + " - " + date_fin;
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

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }

    public Adherent getAdherent() {
        return adherent;
    }

    public void setAdherent(Adherent adherent) {
        this.adherent = adherent;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
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


}
