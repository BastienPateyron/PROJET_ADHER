package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.team_adher.basti.ADHER.classes.Faire;

/**
 * Created by basti on 12/4/2017.
 */

public class FaireDAO extends SQLiteDBHelper {

    public FaireDAO(Context context) {
        super(context);
    }

    public static final String TABLE_FAIRE = "FAIRE";
    public static final String COL_FK_ACTIVITE = "ID_ACTIVITE"; /* TODO On en est là ! */
    public static final String COL_FK_ADHERENT = "ID_ADHERENT";



    public boolean insertFaire(Faire faire){
        ContentValues values = new ContentValues();

        values.put(COL_FK_ACTIVITE, faire.getId_activite());
        values.put(COL_FK_ADHERENT, faire.getId_adherent());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_FAIRE, null, values) > 0;

        return createSuccessful;
    }

    /* TODO retrieve Faire */

    /* TODO getAll Faire */

    /* TODO update Faire */

}
