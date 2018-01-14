package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.util.ArrayList;

import team_adher.adher.classes.ContratService;
import team_adher.adher.classes.Intervention;
import team_adher.adher.classes.Secteur;

/**
 * Created by basti on 12/2/2017.
 */

public class SecteurDAO extends SQLiteDBHelper{

    private static final String TABLE_SECTEUR = "SECTEUR";
    private static final String COL_ID = "ID_SECTEUR";
    private static final String COL_NUM = "NUM_SECTEUR";
    private static final String COL_NOM = "NOM_SECTEUR";


    public SecteurDAO(Context context) {
        super(context);
    }

    public boolean insertSecteur(Secteur secteur){
        ContentValues values = new ContentValues();

        values.put(COL_NOM, secteur.getNom());
        values.put(COL_NUM, secteur.getNumero());
        // values.put(COL_IMAGE_PATH, categorie.getImage_path());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_SECTEUR, null, values) > 0;
        db.close();

        return createSuccessful;
    }

    public Secteur retrieveSecteur(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        /* Requete */
        Cursor cursor = db.query(TABLE_SECTEUR, // Nom table
                                new String[] { COL_ID, COL_NUM, COL_NOM }, // Liste des colonnes
                                COL_ID + "=?",  // Colonne cible du WHERE
                                new String[] { String.valueOf(id) }, // Valeure cible du WHERE
                                null, null, null, null); // Options
        if (cursor != null) cursor.moveToFirst();

        /* On récupère chaque élément dans l'ordre de la table (Haut en bas) */
        Secteur secteur = new Secteur(cursor.getInt(0),cursor.getInt(1), cursor.getString(2));
        db.close();
        return secteur;
    }

    public ArrayList<Secteur> getAllSecteur(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Secteur> listeSecteurs = new ArrayList<Secteur>();
        String query = "SELECT * FROM SECTEUR;";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){ /* Si le curseur est pas null, on le place au début de la liste */
            do{
                Secteur secteur = new Secteur(); /* Création d'un secteur vide pour le remplir */
                /* On peut mettre le cursor.getInt etc ... dans le constructeur directe */
                secteur.setId(cursor.getInt(0)); // Colonne numéro ??? (0 = id, 1 = numero, 2 = nom)
                secteur.setNumero(cursor.getInt(1));
                secteur.setNom(cursor.getString(2));
                listeSecteurs.add(secteur);
            }while(cursor.moveToNext()); /* Tant que l'élément suivant existe */
        }
        db.close();
        return listeSecteurs;
    }

    public void updateSecteur(Secteur secteur){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NUM,secteur.getNumero());
        values.put(COL_NOM,secteur.getNom());

        System.out.println(secteur.getId());
        db.update(TABLE_SECTEUR, values, COL_ID + "="+ secteur.getId(), null);
        db.close();
    }

    public void deleteSecteur(int id_secteur, Context context)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // Supprimer les ContratsService avec cet ID activité
        ContratServiceDAO contratServiceDAO = new ContratServiceDAO(context);
        ArrayList<ContratService> list_contratServices = contratServiceDAO.getAllContratServiceOfAdherent(context, id_secteur);

        for(ContratService cs: list_contratServices) contratServiceDAO.deleteContratService(context, cs.getId());
        // Supprimer les interventions  liées à  l'activité
        InterventionDAO interventionDAO = new InterventionDAO(context);

        // TODO getAllIntervention récupère TOUTES LES INTERVENTIONS
        ArrayList<Intervention> list_intervention = interventionDAO.getAllInterventionOf(context, "secteur", id_secteur);

        for(Intervention cs: list_intervention) interventionDAO.deleteIntervention(cs.getId());


        db.delete(TABLE_SECTEUR, COL_ID + "=" + id_secteur, null);

        db.close();
    }
}
