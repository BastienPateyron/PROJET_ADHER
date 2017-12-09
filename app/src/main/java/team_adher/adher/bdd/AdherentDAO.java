package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

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

    public AdherentDAO(Context context) {
        super(context);
    }

    /* Creer les méthodes de requete à partir de SecteurDAO */

    /* Insert */
    public boolean insertAdherent(Adherent adherent){
        ContentValues values = new ContentValues();

        values.put(COL_ID, adherent.getId());
        values.put(COL_RAISON_SOCIALE, adherent.getRaison_sociale());
        values.put(COL_NUM_RUE, adherent.getNum_rue());
        values.put(COL_NOM_RUE, adherent.getNom_rue());
        values.put(COL_CP, adherent.getCp());
        values.put(COL_VILLE,adherent.getVille());
        values.put(COL_NOM_RESPONSABLE, adherent.getNom_responsable());

        SQLiteDatabase db = this.getWritableDatabase();

        /* Requete */
        boolean insertSuccessful = db.insert(TABLE_ADHERENT, null, values) > 0;
        db.close();

        return insertSuccessful;
    }

    /* TODO Retrieve */

    /* TODO Get All */

    /* TODO update */


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
