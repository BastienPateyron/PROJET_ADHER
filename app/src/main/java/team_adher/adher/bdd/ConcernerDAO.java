package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import team_adher.adher.classes.Activite;
import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.Concerner;
import team_adher.adher.classes.ContratService;
import team_adher.adher.classes.Secteur;

/**
 * Created by R on 16/12/2017.
 */

public class ConcernerDAO extends SQLiteDBHelper {

    private static final String TABLE_CONCERNER = "CONCERNER";
    private static final String COL_ID_CONTRAT = "ID_CONTRAT";
    private static final String COL_ID_ACTIVITE = "ID_ACTIVITE";

    public ConcernerDAO(Context context) {
        super(context);
    }

    /* INSERT CONCERNER */
    public boolean insertConcerner(Concerner concerner){
        ContentValues values = new ContentValues();

        values.put(COL_ID_CONTRAT,concerner.getContratService().getId());
        values.put(COL_ID_ACTIVITE,concerner.getActivite().getId());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_CONCERNER,null,values) > 0;

        db.close();
        return createSuccessful;
    }

    /*RETRIEVE CONCERNER*/
    public Concerner retrieveConcernerByContrat(int id, Context context){
        SQLiteDatabase db = this.getReadableDatabase();
// TODO COmprendre comme ça marche ici

        Cursor cursor = db.query(TABLE_CONCERNER,
                new String[] { COL_ID_ACTIVITE,  COL_ID_CONTRAT},
                COL_ID_CONTRAT + "=?",
                new String[] { String.valueOf(id) },
                null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        ActiviteDAO activiteDAO = new ActiviteDAO(context);
        ContratServiceDAO contratServiceDAO = new ContratServiceDAO(context);

        ContratService contratService = contratServiceDAO.retrieveContratService(cursor.getInt(0), context);
        Activite activite = activiteDAO.retrieveActivite(cursor.getInt(1));

        Concerner concerner = new Concerner(contratService, activite);

        db.close();
        return concerner;
    }


    /* GET ALL CONCERNER */
    public ArrayList<Concerner> getAllConcerner(Context context){
        SQLiteDatabase db = this.getReadableDatabase();
        ActiviteDAO activiteDAO = new ActiviteDAO(context);
        ContratServiceDAO contratServiceDAO = new ContratServiceDAO(context);

        ArrayList<Concerner> listeConcerner= new ArrayList<>();
        String query = "SELECT * FROM CONCERNER;";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                Concerner concerner = new Concerner();
                ContratService contratService = contratServiceDAO.retrieveContratService(cursor.getInt(0), context);
                Activite activite = activiteDAO.retrieveActivite(cursor.getInt(1));

                concerner.setContratService(contratService);
                concerner.setActivite(activite);

                listeConcerner.add(concerner);
            } while(cursor.moveToNext());
        }
        db.close();
        return listeConcerner;
    }

    public ArrayList<Concerner> getAllConcernerOfContratService(Context context, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ActiviteDAO activiteDAO = new ActiviteDAO(context);
        ContratServiceDAO contratServiceDAO = new ContratServiceDAO(context);

        ArrayList<Concerner> listeConcerner= new ArrayList<>();
        String query = "SELECT * FROM CONCERNER WHERE ID_CONTRAT = " + id + ";";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                Concerner concerner = new Concerner();
                ContratService contratService = contratServiceDAO.retrieveContratService(cursor.getInt(0), context);
                Activite activite = activiteDAO.retrieveActivite(cursor.getInt(1));

                concerner.setContratService(contratService);
                concerner.setActivite(activite);

                listeConcerner.add(concerner);
            } while(cursor.moveToNext());
        }
        db.close();
        return listeConcerner;
    }

    public ArrayList<Concerner> getAllConcernerOfActivite(Context context, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        ActiviteDAO activiteDAO = new ActiviteDAO(context);
        ContratServiceDAO contratServiceDAO = new ContratServiceDAO(context);

        ArrayList<Concerner> listeConcerner= new ArrayList<>();
        String query = "SELECT * FROM CONCERNER WHERE ID_ACTIVITE = " + id + ";";
        Cursor cursor = db.rawQuery(query,null);

        if (cursor.moveToFirst()){
            do {
                Concerner concerner = new Concerner();
                ContratService contratService = contratServiceDAO.retrieveContratService(cursor.getInt(0), context);
                Activite activite = activiteDAO.retrieveActivite(cursor.getInt(1));

                concerner.setContratService(contratService);
                concerner.setActivite(activite);

                listeConcerner.add(concerner);
            } while(cursor.moveToNext());
        }
        db.close();
        return listeConcerner;
    }

    public void updateConcerner(Concerner concerner){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_ID_CONTRAT,concerner.getContratService().getId());
        values.put(COL_ID_ACTIVITE,concerner.getActivite().getId());

        db.update(TABLE_CONCERNER, values, COL_ID_CONTRAT + " = ? and " + COL_ID_ACTIVITE +" = ?",
                new String[] {
                        Integer.toString(concerner.getContratService().getId()),
                        Integer.toString(concerner.getActivite().getId())
        });

        db.close();
    }

    public void deleteConcerner(int id_contrat, int id_activite)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CONCERNER, COL_ID_CONTRAT + " = ? AND " + COL_ID_ACTIVITE + "= ?", new String[]{
                Integer.toString(id_contrat),
                Integer.toString(id_activite)
        });
        db.close();
        System.out.println("Concerner supprimé");
    }

}
