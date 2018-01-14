package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.Concerner;
import team_adher.adher.classes.ContratService;
import team_adher.adher.classes.Intervention;
import team_adher.adher.classes.Secteur;

import static android.content.ContentValues.TAG;


/**
 * Created by basti on 12/4/2017.
 */

public class ContratServiceDAO extends SQLiteDBHelper {
    public static final String TABLE_CONTRAT_SERVICE = "CONTRAT_SERVICE";
    public static final String COL_ID = "ID_CONTRAT_SERVICE";
    public static final String COL_FK_SECTEUR = "ID_SECTEUR";
    public static final String COL_FK_ADHERENT = "ID_ADHERENT";
    public static final String COL_DATE_DEBUT = "DATE_DEBUT_CONTRAT_SERVICE";
    public static final String COL_DATE_FIN = "DATE_FIN_CONTRAT_SERVICE";
    public static final String COL_TARIF_HT = "TARIF_HT";

    public ContratServiceDAO(Context context) {
        super(context);
    }

    /* Retrieve ID last insert */
    public int retrieveLastContratServiceID(Context context){
        SQLiteDatabase db = this.getReadableDatabase();

        /* Requete */
        String query = "SELECT " + COL_ID + " FROM " + TABLE_CONTRAT_SERVICE + " ORDER BY " + COL_ID + " DESC LIMIT 1;";
        Cursor cursor = db.rawQuery(query, null);
        if (cursor != null) cursor.moveToFirst();
        db.close();

        return cursor.getInt(0);

    }

    /* Insert */
    public boolean insertContratService(ContratService adherent){
        ContentValues values = new ContentValues();

        values.put(COL_FK_SECTEUR, adherent.getSecteur().getId());
        values.put(COL_FK_ADHERENT, adherent.getAdherent().getId());
        values.put(COL_DATE_DEBUT, adherent.getDate_debut());
        values.put(COL_DATE_FIN, adherent.getDate_fin());
        values.put(COL_TARIF_HT, adherent.getTarif_ht());


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
                new String[] { COL_ID,  COL_FK_SECTEUR, COL_FK_ADHERENT, COL_DATE_DEBUT, COL_DATE_FIN, COL_TARIF_HT}, // Liste des colonnes, ajout de la colonne tarif?
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
        ContratService contratService = new ContratService(cursor.getInt(0),secteur, adherent, cursor.getString(3), cursor.getString(4), cursor.getDouble(5));
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
                ContratService contratService = new ContratService(); /* Création d'un contratService vide pour le remplir */

                System.out.println("Retreive_secteur( " + cursor.getInt(1) + " )");
                Secteur secteur = secteurDAO.retrieveSecteur(cursor.getInt(1));


                System.out.println("Retrieve_adherent( " + cursor.getInt(2) + " )");
                Adherent adherent = adherentDAO.retrieveAdherent(cursor.getInt(2));


                /* On peut mettre le cursor.getInt etc ... dans le constructeur directe */
                contratService.setId(cursor.getInt(0)); // Colonne numéro ??? (0 = id, 1 = numero, 2 = nom)
                contratService.setSecteur(secteur);
                contratService.setAdherent(adherent);
                contratService.setDate_debut(cursor.getString(3));
                contratService.setDate_fin(cursor.getString(4));
                contratService.setTarif_ht(cursor.getDouble(5));

                listeContratServices.add(contratService);
            }while(cursor.moveToNext()); /* Tant que l'élément suivant existe */
        }
        db.close();
        return listeContratServices;
    }

    //  Lister les  ContratServiceIdAdherent
    public ArrayList<ContratService> getAllContratServiceOfAdherent(Context context, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        SecteurDAO secteurDAO = new SecteurDAO(context);
        AdherentDAO adherentDAO = new AdherentDAO(context);

        ArrayList<ContratService> listeContratServices = new ArrayList<ContratService>();
        String query = "SELECT * FROM CONTRAT_SERVICE WHERE ID_ADHERENT = " + id + ";";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){ /* Si le curseur est pas null, on le place au début de la liste */
            do{
                ContratService contratService = new ContratService(); /* Création d'un contratService vide pour le remplir */

                System.out.println("Retreive_secteur( " + cursor.getInt(1) + " )");
                Secteur secteur = secteurDAO.retrieveSecteur(cursor.getInt(1));


                System.out.println("Retrieve_adherent( " + id + " )");
                Adherent adherent = adherentDAO.retrieveAdherent(id);

                /* On peut mettre le cursor.getInt etc ... dans le constructeur directe */
                contratService.setId(cursor.getInt(0)); // Colonne numéro ??? (0 = id, 1 = numero, 2 = nom)
                contratService.setSecteur(secteur);
                contratService.setAdherent(adherent);
                contratService.setDate_debut(cursor.getString(3));
                contratService.setDate_fin(cursor.getString(4));
                contratService.setTarif_ht(cursor.getDouble(5));

                listeContratServices.add(contratService);
            }while(cursor.moveToNext()); /* Tant que l'élément suivant existe */
        }
        db.close();
        return listeContratServices;
    }

    //  Lister les  ContratService ID Secteur
    public ArrayList<ContratService> getAllContratServiceOfSecteur(Context context, int id){
        SQLiteDatabase db = this.getReadableDatabase();
        SecteurDAO secteurDAO = new SecteurDAO(context);
        AdherentDAO adherentDAO = new AdherentDAO(context);

        ArrayList<ContratService> listeContratServices = new ArrayList<ContratService>();
        String query = "SELECT * FROM CONTRAT_SERVICE WHERE ID_SECTEUR = " + id + ";";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){ /* Si le curseur est pas null, on le place au début de la liste */
            do{
                ContratService contratService = new ContratService(); /* Création d'un contratService vide pour le remplir */

                System.out.println("Retreive_secteur( " + cursor.getInt(1) + " )");
                Secteur secteur = secteurDAO.retrieveSecteur(cursor.getInt(1));


                System.out.println("Retrieve_adherent( " + id + " )");
                Adherent adherent = adherentDAO.retrieveAdherent(id);

                /* On peut mettre le cursor.getInt etc ... dans le constructeur directe */
                contratService.setId(cursor.getInt(0)); // Colonne numéro ??? (0 = id, 1 = numero, 2 = nom)
                contratService.setSecteur(secteur);
                contratService.setAdherent(adherent);
                contratService.setDate_debut(cursor.getString(3));
                contratService.setDate_fin(cursor.getString(4));
                contratService.setTarif_ht(cursor.getDouble(5));

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
        values.put(COL_TARIF_HT,contratService.getTarif_ht());

        db.update(TABLE_CONTRAT_SERVICE, values, COL_ID + "="+ contratService.getId(), null);
        db.close();
    }

    public void deleteContratService(Context context, int id_contrat_service)
    {
        System.out.println("Id Contrat à supprimer: " + id_contrat_service);
        SQLiteDatabase db = this.getWritableDatabase();

        // Récupérer les occurences de Concerner et les supprimer
        ConcernerDAO concernerDAO = new ConcernerDAO(context);
        Log.d(TAG, "deleteContratService: On supprime les occurences de la table CONCERNER");
        ArrayList<Concerner> concernerArrayList = concernerDAO.getAllConcernerOfContratService(context, id_contrat_service);

        for(Concerner concerner:concernerArrayList) concernerDAO.deleteConcerner(concerner.getContratService().getId(), concerner.getActivite().getId());


        db.delete(TABLE_CONTRAT_SERVICE, COL_ID + "=" + id_contrat_service, null);
        System.out.println("Contrat de Service Supprimé");
        db.close();
    }
}
