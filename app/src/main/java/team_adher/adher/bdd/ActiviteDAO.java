package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team_adher.adher.classes.Activite;
import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.ContratService;
import team_adher.adher.classes.Intervention;

/**
 * Created by basti on 12/5/2017.
 */

public class ActiviteDAO extends SQLiteDBHelper {
    private static final String TABLE_ACTIVITE = "ACTIVITE";
    private static final String COL_ID = "ID_ACTIVITE";
    private static final String COL_NOM = "NOM_ACTIVITE";

    public ActiviteDAO(Context context) {
        super(context);
    }

    /* insertActivite */
    public boolean insertActivite(Activite activite){
        ContentValues values = new ContentValues();

        values.put(COL_NOM,activite.getNom());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_ACTIVITE,null,values) > 0;

        return createSuccessful;
    }

    /* getAllActivite */
    public ArrayList<Activite> getAllActivite(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Activite> listeActivite= new ArrayList<>();
        String query = "SELECT * FROM ACTIVITE;";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){ /* Si le curseur est pas null, on le place au début de la liste */
            do {
                Activite activite = new Activite(); /* Création d'une Activité vide pour la remplir */
                /* Colonne numéro ??? (0 = id, 1 = numero, 2 = nom) */
                activite.setId(cursor.getInt(0));
                activite.setNom(cursor.getString(1));

                listeActivite.add(activite); /* Ajout de l'Activité valorisée dans la liste */
            } while(cursor.moveToNext()); /* Tant qu'il reste des éléments à traiter */
        }
        db.close();
        return listeActivite;
    }

    public ArrayList<Activite> getAllActiviteOfCS(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Activite> listeActivite= new ArrayList<>();
        String query =
                "SELECT * FROM ACTIVITE, CONCERNER" +
                " WHERE CONCERNER.ID_CONTRAT = " + id +
                " AND CONCERNER.ID_ACTIVITE = ACTIVITE.ID_ACTIVITE;";

        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){ /* Si le curseur est pas null, on le place au début de la liste */
            do {
                Activite activite = new Activite(); /* Création d'une Activité vide pour la remplir */
                /* Colonne numéro ??? (0 = id, 1 = numero, 2 = nom) */
                activite.setId(cursor.getInt(0));
                activite.setNom(cursor.getString(1));

                listeActivite.add(activite); /* Ajout de l'Activité valorisée dans la liste */
            } while(cursor.moveToNext()); /* Tant qu'il reste des éléments à traiter */
        }
        db.close();
        return listeActivite;
    }

    /* retrieveActivite */
    public Activite retrieveActivite(int id){

            SQLiteDatabase db = this.getReadableDatabase();

        /* Requete */
            Cursor cursor = db.query(TABLE_ACTIVITE, // Nom table
                    new String[] { COL_ID, COL_NOM}, // Liste des colonnes
                    COL_ID + "=?",  // Colonne cible du WHERE
                    new String[] { String.valueOf(id) }, // Valeure cible du WHERE
                    null, null, null, null); // Options
            if (cursor != null) cursor.moveToFirst();

        /* On récupère chaque élément dans l'ordre de la table (Haut en bas) */
            Activite activite = new Activite(cursor.getInt(0),cursor.getString(1));
            db.close();
            return activite;
        }


    public void updateActivite(Activite activite){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOM,activite.getNom());
//        System.out.println(adherent.getId());
        db.update(TABLE_ACTIVITE, values, COL_ID + "="+ activite.getId(), null);
        db.close();
    }

    public  void deleteActivite(Context context, int id_activite) {

        SQLiteDatabase db = this.getWritableDatabase();

        // Supprimer les ContratsService avec cet ID activité
        ContratServiceDAO contratServiceDAO = new ContratServiceDAO(context);
        ArrayList<ContratService> list_contratServices = contratServiceDAO.getAllContratServiceOfAdherent(context, id_activite);

        for(ContratService cs: list_contratServices) contratServiceDAO.deleteContratService(context, cs.getId());
        // Supprimer les interventions  liées à  l'activité
        InterventionDAO interventionDAO = new InterventionDAO(context);
        // TODO getAllIntervention récupère TOUTES LES INTERVENTIONS
        ArrayList<Intervention> list_intervention = interventionDAO.getAllInterventionOf(context, "activite", id_activite);

        for(Intervention cs: list_intervention) interventionDAO.deleteIntervention(cs.getId());

        // Supprimer l'activite
        db.delete(TABLE_ACTIVITE, COL_ID + "=" + id_activite, null);
        System.out.println("Activité Id " + id_activite + " supprimé");

        db.close();
    }

}
