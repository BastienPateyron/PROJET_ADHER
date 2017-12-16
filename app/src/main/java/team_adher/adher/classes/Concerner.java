package team_adher.adher.classes;

/**
 * Created by R on 16/12/2017.
 */

public class Concerner {

    private ContratService contratService;
    private Activite activite;


    public  Concerner() {

    }
    public Concerner(ContratService contratService, Activite activite) {
        this.contratService = contratService;
        this.activite = activite;
    }


    public ContratService getContratService() {
        return contratService;
    }

    public void setContratService(ContratService contratService) {
        this.contratService = contratService;
    }

    public Activite getActivite() {
        return activite;
    }

    public void setActivite(Activite activite) {
        this.activite = activite;
    }
}
