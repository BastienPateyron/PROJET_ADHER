package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.ContactsContract;
import android.provider.SyncStateContract;

import java.util.ArrayList;

import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.ContratService;


/**
 * Created by basti on 12/3/2017.
 */
    /*  Mettre a jour avec la nouvelle base */

public class AdherentDAO extends SQLiteDBHelper {
    private static final String TABLE_ADHERENT = "ADHERENT";
    private static final String COL_ID = "ID_ADHERENT";
    private static final String COL_RAISON_SOCIALE = "RAISON_SOCIALE_ADHERENT";
    private static final String COL_NUM_RUE = "NUM_RUE_ADHERENT";
    private static final String COL_NOM_RUE = "NOM_RUE_ADHERENT";
    private static final String COL_CP = "CP_ADHERENT";
    private static final String COL_VILLE = "VILLE_ADHERENT";
    private static final String COL_NOM_RESPONSABLE = "NOM_RESPONSABLE_ADHERENT";
    private static final String COL_NUM_TELEPHONE = "NUM_TELEPHONE";

    public AdherentDAO(Context context) {
        super(context);
    }

    /* Creer les méthodes de requete à partir de AdherentDAO */

    /* Insert */
    public boolean insertAdherent(Adherent adherent){
        ContentValues values = new ContentValues();

        values.put(COL_RAISON_SOCIALE, adherent.getRaison_sociale());
        values.put(COL_NUM_RUE, adherent.getNum_rue());
        values.put(COL_NOM_RUE, adherent.getNom_rue());
        values.put(COL_CP, adherent.getCp());
        values.put(COL_VILLE,adherent.getVille());
        values.put(COL_NOM_RESPONSABLE, adherent.getNom_responsable());
        values.put(COL_NUM_TELEPHONE, adherent.getTelephone());

        SQLiteDatabase db = this.getWritableDatabase();

        /* Requete */
        boolean insertSuccessful = db.insert(TABLE_ADHERENT, null, values) > 0;
        db.close();

        return insertSuccessful;
    }

    public Adherent retrieveAdherent(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Adherent adherent = new Adherent();
        /* Requete */
        Cursor cursor = db.query(TABLE_ADHERENT, // Nom table
                new String[] { COL_ID, COL_RAISON_SOCIALE, COL_NUM_RUE, COL_NOM_RUE, COL_CP, COL_VILLE, COL_NOM_RESPONSABLE, COL_NUM_TELEPHONE }, // Liste des colonnes
                COL_ID + "=?",  // Colonne cible du WHERE
                new String[] { String.valueOf(id) }, // Valeure cible du WHERE
                null, null, null, null); // Options
        if (cursor != null){
            cursor.moveToFirst();
//            System.out.println("retrieve_adherent(): Cursor = " + cursor.getPosition());
            /* On récupère chaque élément dans l'ordre de la table (Haut en bas) */
            adherent.setId(cursor.getInt(0));
            adherent.setRaison_sociale(cursor.getString(1));
            adherent.setNum_rue(cursor.getInt(2));
            adherent.setNom_rue(cursor.getString(3));
            adherent.setCp(cursor.getInt(4));
            adherent.setVille(cursor.getString(5));
            adherent.setNom_responsable(cursor.getString(6));
            adherent.setTelephone(cursor.getInt(7));
        }else

        db.close();
        return adherent;
    }

    public ArrayList<Adherent> getAllAdherent(){
        SQLiteDatabase db = this.getReadableDatabase();

        ArrayList<Adherent> listeAdherents = new ArrayList<Adherent>();
        String query = "SELECT * FROM ADHERENT;";
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){ /* Si le curseur est pas null, on le place au début de la liste */
            do{
                Adherent adherent = new Adherent(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getInt(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getInt(7)
                );

                listeAdherents.add(adherent);
            }while(cursor.moveToNext()); /* Tant que l'élément suivant existe */
        }
        db.close();
        return listeAdherents;
    }

    public void updateAdherent(Adherent adherent){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_RAISON_SOCIALE,adherent.getRaison_sociale());
        values.put(COL_NUM_RUE,adherent.getNum_rue());
        values.put(COL_NOM_RUE,adherent.getNom_rue());
        values.put(COL_CP,adherent.getCp());
        values.put(COL_VILLE,adherent.getVille());
        values.put(COL_NOM_RESPONSABLE,adherent.getNom_responsable());
        values.put(COL_NUM_TELEPHONE,adherent.getTelephone());

//        System.out.println(adherent.getId());
        db.update(TABLE_ADHERENT, values, COL_ID + "="+ adherent.getId(), null);
        db.close();
    }

    public void deleteAdherent(Context context, int id_adherent)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        // Supprimer les ContratsService avec cet ID adhérent
        ContratServiceDAO contratServiceDAO = new ContratServiceDAO(context);
        ArrayList<ContratService> list_contratServices = contratServiceDAO.getAllContratServiceOfAdherent(context, id_adherent);

        for(ContratService cs: list_contratServices) contratServiceDAO.deleteContratService(context, cs.getId());


        // Supprimer les ContratsIntervention avec cet ID adhérent
        // TODO Décommenter et faire la méthode getAllInterventionOfAdherent + deleteIntervention
//        InterventionDAO interventionDAO = new InterventionDAO(context);
//        ArrayList<Intervention> interventionArrayList = interventionDAO.getAllInterventionOfAdherent(context, id_adherent);
//        for(Intervention intervention : interventionArrayList) interventionDAO.deleteIntervention(context, intervention.getId());


        // Supprimer l'adhérent
        db.delete(TABLE_ADHERENT, COL_ID + "=" + id_adherent, null);
        System.out.println("Adhérent supprimé");
        db.close();
    }
    /*
    public void addAdherent(Adherent adherent)
    {
        SQLiteDatabase db = this.getWritableDatabase();
    ContentValues valuesA = new ContentValues();
    valuesA.put(AdherentDAO.COL_RAISON_SOCIALE, "SARL");
    valuesA.put(AdherentDAO.COL_NOM_RESPONSABLE, "Bob Bricole");
    valuesA.put(AdherentDAO.COL_NUM_RUE, 1);
    valuesA.put(AdherentDAO.COL_NOM_RUE,"Rue du Marteau");
    valuesA.put(AdherentDAO.COL_CP, 63000);
    valuesA.put(AdherentDAO.COL_VILLE, "Clermont Ferrand");

    db.insert(AdherentDAO.TABLE_ADHERENT,null,valuesA);


    }*/
    /* Curseur propre

     Cursor cursor = db.query(
                TABLE_ADHERENT, // Nom table
                new String[]{COL_ID, COL_RAISON_SOCIALE, COL_NUM_RUE, COL_NOM_RUE, COL_CP, COL_NOM_RESPONSABLE}, // Liste des colonnes
                // Colonne du WHERE
                // Valeur du WHERE
                // Options
        );


     */
}
