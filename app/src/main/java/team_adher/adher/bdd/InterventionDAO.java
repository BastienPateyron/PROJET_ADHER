package team_adher.adher.bdd;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team_adher.adher.classes.Activite;
import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.Client;
import team_adher.adher.classes.Intervention;
import team_adher.adher.classes.Secteur;

/**
 * Created by pezed on 20/12/17.
 */

public class InterventionDAO extends SQLiteDBHelper {
    public static final String TABLE_INTERVENTION = "CONTRAT_INTERVENTION";
    public static final String COL_ID = "ID_CONTRAT_INTERVENTION";
    public static final String COL_FK_SECTEUR = "ID_SECTEUR";
    public static final String COL_FK_ACTIVITE = "ID_ACTIVITE";
    public static final String COL_FK_ADHERENT = "ID_ADHERENT";
    public static final String COL_FK_CLIENT = "ID_CLIENT";
    public static final String COL_DATE_DEBUT = "DATE_DEBUT_CONTRAT_INTERVENTION";
    public static final String COL_DATE_FIN = "DATE_FIN_CONTRAT_INTERVENTION";





    public InterventionDAO(Context context) {
        super(context);
    }


    /* Retrieve ID last insert */
    public int retrieveLastInterventionID(Context context){
        SQLiteDatabase db = this.getReadableDatabase();

        /* Requete */
        String query = "SELECT " + COL_ID + " FROM " + TABLE_INTERVENTION + " ORDER BY " + COL_ID + " DESC LIMIT 1;";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) cursor.moveToFirst();
        db.close();

        return cursor.getInt(0);

    }

    /* Insert */
    public boolean insertIntervention(Intervention intervention){
        ContentValues values = new ContentValues();

        values.put(COL_FK_SECTEUR, intervention.getSecteur().getId());
        values.put(COL_FK_ACTIVITE, intervention.getActivite().getId());
        values.put(COL_FK_ADHERENT, intervention.getAdherent().getId());
        values.put(COL_FK_CLIENT, intervention.getClient().getId());
        values.put(COL_DATE_DEBUT, intervention.getDate_debut());
        values.put(COL_DATE_FIN, intervention.getDate_fin());

        SQLiteDatabase db = this.getWritableDatabase();

        /* Requete */
        boolean insertSuccessful = db.insert(TABLE_INTERVENTION, null, values) > 0;
        db.close();

        return insertSuccessful;
    }


    public Intervention retrieveIntervention(int id, Context context){
        SQLiteDatabase db = this.getReadableDatabase();

        /* Requete */
        Cursor cursor = db.query(TABLE_INTERVENTION, // Nom table
                new String[] { COL_ID,  COL_FK_SECTEUR, COL_FK_ACTIVITE, COL_FK_ADHERENT, COL_FK_CLIENT, COL_DATE_DEBUT, COL_DATE_FIN}, // Liste des colonnes
                COL_ID + "=?",  // Colonne cible du WHERE
                new String[] { String.valueOf(id) }, // Valeure cible du WHERE
                null, null, null, null); // Options
        if (cursor != null) cursor.moveToFirst();

        /* On récupère l'objet secteur à partir de l'ID présent dans la table Intervention */
        SecteurDAO secteurDAO = new SecteurDAO(context);
        Secteur secteur = secteurDAO.retrieveSecteur(cursor.getInt(1));

        /* On récupère l'objet activite à partir de l'ID présent dans la table Intervention */
        ActiviteDAO activiteDAO = new ActiviteDAO(context);
        Activite activite = activiteDAO.retrieveActivite(cursor.getInt(2));

        /* On récupère l'objet adherent à partir de l'ID présent dans la table Intervention */
        AdherentDAO adherentDAO = new AdherentDAO(context);
        Adherent adherent = adherentDAO.retrieveAdherent(cursor.getInt(3));
        if(adherent == null){ // Si l'adhérent n'existe plus, on lui met un nom #Suppr# pour l'afficher ainsi dans la liste
            System.out.println("L'Adhérent récupéré n'existe plus !");
            adherent = new Adherent();
            adherent.setRaison_sociale("#Suppr#");
        }
        /* On récupère l'objet client à partir de l'ID présent dans la table Intervention */
        ClientDAO clientDAO = new ClientDAO(context);
        Client client = clientDAO.retrieveClient(cursor.getInt(4));

        /* On récupère chaque élément dans l'ordre de la table (Haut en bas) */
        Intervention intervention = new Intervention(cursor.getInt(0), secteur, activite, adherent, client, cursor.getString(5), cursor.getString(6));
        db.close();
        return intervention;
    }


    public ArrayList<Intervention> getAllIntervention(Context context){
        SQLiteDatabase db = this.getReadableDatabase();
        SecteurDAO secteurDAO = new SecteurDAO(context);
        ActiviteDAO activiteDAO = new ActiviteDAO (context);
        AdherentDAO adherentDAO = new AdherentDAO(context);
        ClientDAO clientDAO = new ClientDAO (context);

        ArrayList<Intervention> listeIntervention = new ArrayList<>();
        String query = "SELECT * FROM INTERVENTION;";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){ /* Si le curseur est pas null, on le place au début de la liste */
            do{
                Intervention intervention = new Intervention(); /* Création d'une intervention vide pour le remplir */


                Secteur secteur = secteurDAO.retrieveSecteur(cursor.getInt(1));
                Activite activite = activiteDAO.retrieveActivite(cursor.getInt(2));
                Adherent adherent = adherentDAO.retrieveAdherent(cursor.getInt(3));
                Client client = clientDAO.retrieveClient (cursor.getInt(4));

                /* On peut mettre le cursor.getInt etc ... dans le constructeur directe */
                intervention.setId(cursor.getInt(0)); // Colonne numéro ??? (0 = id, 1 = secteur, 2 = activite)
                intervention.setSecteur(secteur);
                intervention.setActivite(activite);
                intervention.setAdherent(adherent);
                intervention.setClient(client);
                intervention.setDate_debut(cursor.getString(5));
                intervention.setDate_fin(cursor.getString(6));

                listeIntervention.add(intervention);
            }while(cursor.moveToNext()); /* Tant que l'élément suivant existe */
        }
        db.close();
        return listeIntervention;
    }

    public void updateIntervention(Intervention intervention){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FK_SECTEUR,intervention.getSecteur().getId());
        values.put(COL_FK_ACTIVITE,intervention.getActivite().getId());
        values.put(COL_FK_ADHERENT,intervention.getAdherent().getId());
        values.put(COL_FK_CLIENT,intervention.getClient().getId());
        values.put(COL_DATE_DEBUT,intervention.getDate_debut());
        values.put(COL_DATE_FIN,intervention.getDate_fin());

        db.update(TABLE_INTERVENTION, values, COL_ID + "="+ intervention.getId(), null);
        db.close();
    }

    public void deleteIntervention(int id_intervention)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_INTERVENTION, COL_ID + "=" + id_intervention, null);

        db.close();
    }
}
;