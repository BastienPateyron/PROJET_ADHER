package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.team_adher.basti.ADHER.classes.Contrat;

/**
 * Created by basti on 12/4/2017.
 */

public class ContratDAO extends SQLiteDBHelper {
    public static final String TABLE_CONTRAT = "CONTRAT";
    public static final String COL_ID = "ID_CONTRAT";
    public static final String COL_DATE_DEBUT = "DATE_DEBUT_CONTRAT";
    public static final String COL_DATE_FIN = "DATE_FIN_CONTRAT";
    public static final String COL_FK_SECTEUR = "ID_SECTEUR";

    public ContratDAO(Context context) {
        super(context);
    }

    public boolean insertContrat(Contrat contrat){
        ContentValues values = new ContentValues();

        values.put(COL_ID,contrat.getId());
        values.put(COL_DATE_DEBUT,contrat.getDate_debut());
        values.put(COL_DATE_FIN,contrat.getDate_fin());
        values.put(COL_FK_SECTEUR,contrat.getFk_secteur());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_CONTRAT,null,values) > 0;
        db.close();

        return createSuccessful;
    }

    /* TODO retrieve Contrat */

    /* TODO getAllContrat */

    /* TODO update Contrat */

}
