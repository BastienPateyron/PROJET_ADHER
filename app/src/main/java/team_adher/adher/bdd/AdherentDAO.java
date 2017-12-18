package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.Adherent;


/**
 * Created by basti on 12/3/2017.
 */
    /* TODO Mettre a jour avec la nouvelle base */

public class AdherentDAO extends SQLiteDBHelper {
    private static final String TABLE_ADHERENT = "ADHERENT";
    private static final String COL_ID = "ID_ADHERENT";
    private static final String COL_RAISON_SOCIALE = "RAISON_SOCIALE_ADHERENT";
    private static final String COL_NUM_RUE = "NUM_RUE_ADHERENT";
    private static final String COL_NOM_RUE = "NOM_RUE_ADHERENT";
    private static final String COL_CP = "CP_ADHERENT";
    private  static final String COL_VILLE = "VILLE_ADHERENT";
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

        /* Requete */
        Cursor cursor = db.query(TABLE_ADHERENT, // Nom table
                new String[] { COL_ID, COL_RAISON_SOCIALE, COL_NUM_RUE, COL_NOM_RUE, COL_CP, COL_VILLE, COL_NOM_RESPONSABLE, COL_NUM_TELEPHONE }, // Liste des colonnes
                COL_ID + "=?",  // Colonne cible du WHERE
                new String[] { String.valueOf(id) }, // Valeure cible du WHERE
                null, null, null, null); // Options
        if (cursor != null) cursor.moveToFirst();

        /* On récupère chaque élément dans l'ordre de la table (Haut en bas) */
        Adherent adherent = new Adherent(cursor.getInt(0),cursor.getString(1), cursor.getInt(2), cursor.getString(3), cursor.getInt(4), cursor.getString(5), cursor.getString(6), cursor.getInt(7));
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
                Adherent adherent = new Adherent(); /* Création d'un adherent vide pour le remplir */
                /* On peut mettre le cursor.getInt etc ... dans le constructeur directe */
                adherent.setId(cursor.getInt(0)); // Colonne numéro ??? (0 = id, 1 = numero, 2 = nom)
                adherent.setRaison_sociale(cursor.getString(1));
                adherent.setNum_rue(cursor.getInt(2));
                adherent.setNom_rue(cursor.getString(3));
                adherent.setCp(cursor.getInt(4));
                adherent.setVille(cursor.getString(5));
                adherent.setNom_responsable(cursor.getString(6));
                adherent.setTelephone(cursor.getInt(7));

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

    public void deleteAdherent(int id_adherent)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_ADHERENT, COL_ID + "=" + id_adherent, null);

        db.close();
    }

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
