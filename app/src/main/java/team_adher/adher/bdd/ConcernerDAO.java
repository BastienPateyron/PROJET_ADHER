package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import team_adher.adher.classes.Activite;
import team_adher.adher.classes.Concerner;

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



}
