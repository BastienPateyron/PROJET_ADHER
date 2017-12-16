package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.ContratService;
import team_adher.adher.classes.Secteur;


/**
 * Created by basti on 12/4/2017.
 */

public class ContratServiceDAO extends SQLiteDBHelper {
    public static final String TABLE_CONTRAT_SERVICE = "CONTRAT_SERVICE";
    public static final String COL_ID = "ID_CONTRAT_SERVICE";
    public static final String COL_DATE_DEBUT = "DATE_DEBUT_CONTRAT_SERVICE";
    public static final String COL_DATE_FIN = "DATE_FIN_CONTRAT_SERVICE";
    public static final String COL_FK_SECTEUR = "ID_SECTEUR";
    public static final String COL_FK_ADHERENT = "ID_ADHERENT";

    public ContratServiceDAO(Context context) {
        super(context);
    }

    public boolean insertContrat(ContratService contratService){
        ContentValues values = new ContentValues();

        values.put(COL_FK_SECTEUR, contratService.getSecteur().getId());
        values.put(COL_FK_ADHERENT, contratService.getAdherent().getId());
        values.put(COL_DATE_DEBUT, contratService.getDate_debut());
        values.put(COL_DATE_FIN, contratService.getDate_fin());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_CONTRAT_SERVICE,null,values) > 0;
        db.close();

        return createSuccessful;
    }

    /* TODO retrieve ContratService */

    /* Insert */
    public boolean insertContratService(ContratService adherent){
        ContentValues values = new ContentValues();

        values.put(COL_FK_SECTEUR, adherent.getSecteur().getId());
        values.put(COL_FK_ADHERENT, adherent.getAdherent().getId());
        values.put(COL_DATE_DEBUT, adherent.getDate_debut());
        values.put(COL_DATE_FIN, adherent.getDate_fin());


        SQLiteDatabase db = this.getWritableDatabase();

        /* Requete */
        boolean insertSuccessful = db.insert(TABLE_CONTRAT_SERVICE, null, values) > 0;
        db.close();

        return insertSuccessful;
    }

    public ContratService retrieveContratService(int id, Context context){
        SQLiteDatabase db = this.getReadableDatabase();

        /* Requete */
        Cursor cursor = db.query(TABLE_CONTRAT_SERVICE, // Nom table
                new String[] { COL_ID,  COL_FK_SECTEUR, COL_FK_ADHERENT, COL_DATE_DEBUT, COL_DATE_FIN}, // Liste des colonnes
                COL_ID + "=?",  // Colonne cible du WHERE
                new String[] { String.valueOf(id) }, // Valeure cible du WHERE
                null, null, null, null); // Options
        if (cursor != null) cursor.moveToFirst();

        /* On récupère l'objet secteur à partir de l'ID présent dans la table contratService */
        SecteurDAO secteurDAO = new SecteurDAO(context);
        Secteur secteur = secteurDAO.retrieveSecteur(cursor.getInt(1));

        /* On récupère l'objet adherent à partir de l'ID présent dans la table contratService */
        AdherentDAO adherentDAO = new AdherentDAO(context);
        Adherent adherent = adherentDAO.retrieveAdherent(cursor.getInt(2));

        /* On récupère chaque élément dans l'ordre de la table (Haut en bas) */
        ContratService contratService = new ContratService(cursor.getInt(0),secteur, adherent, cursor.getString(3), cursor.getString(4));
        db.close();
        return contratService;
    }

    public ArrayList<ContratService> getAllContratService(Context context){
        SQLiteDatabase db = this.getReadableDatabase();
        SecteurDAO secteurDAO = new SecteurDAO(context);
        AdherentDAO adherentDAO = new AdherentDAO(context);

        ArrayList<ContratService> listeContratServices = new ArrayList<ContratService>();
        String query = "SELECT * FROM CONTRAT_SERVICE;";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){ /* Si le curseur est pas null, on le place au début de la liste */
            do{
                ContratService contratService = new ContratService(); /* Création d'un adherent vide pour le remplir */


                Secteur secteur = secteurDAO.retrieveSecteur(cursor.getInt(1));
                Adherent adherent = adherentDAO.retrieveAdherent(cursor.getInt(2));

                /* On peut mettre le cursor.getInt etc ... dans le constructeur directe */
                contratService.setId(cursor.getInt(0)); // Colonne numéro ??? (0 = id, 1 = numero, 2 = nom)
                contratService.setSecteur(secteur);
                contratService.setAdherent(adherent);
                contratService.setDate_debut(cursor.getString(3));
                contratService.setDate_fin(cursor.getString(4));

                listeContratServices.add(contratService);
            }while(cursor.moveToNext()); /* Tant que l'élément suivant existe */
        }
        db.close();
        return listeContratServices;
    }

    public void updateContratService(ContratService contratService){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_FK_SECTEUR,contratService.getSecteur().getId());
        values.put(COL_FK_ADHERENT,contratService.getAdherent().getId());
        values.put(COL_DATE_DEBUT,contratService.getDate_debut());
        values.put(COL_DATE_FIN,contratService.getDate_fin());


//        System.out.println(adherent.getId());
        db.update(TABLE_CONTRAT_SERVICE, values, COL_ID + "="+ contratService.getId(), null);
        db.close();
    }

    public void deleteContratService(int id_contrat_service)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CONTRAT_SERVICE, COL_ID + "=" + id_contrat_service, null);

        db.close();
    }
    /* TODO getAllContrat */

    /* TODO update ContratService */

}
