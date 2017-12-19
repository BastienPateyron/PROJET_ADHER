package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

import team_adher.adher.classes.Client;

/**
 * Created by pezed on 19/12/17.
 */


public class ClientDAO extends SQLiteDBHelper {
    private static final String TABLE_CLIENT = "CLIENT";
    private static final String COL_ID = "ID_CLIENT";
    private static final String COL_NOM = "NOM_CLIENT";
    private static final String COL_PRENOM = "PRENOM_CLIENT";
    private static final String COL_TELEPHONE = "TELEPHONE_CLIENT";
    private static final String COL_NUM_RUE = "NUM_RUE_CLIENT";
    private static final String COL_NOM_RUE = "NOM_RUE_CLIENT";
    private static final String COL_CP = "CP_CLIENT";
    private static final String COL_VILLE = "VILLE_CLIENT";

    public ClientDAO(Context context) {
        super(context);
    }

    /*insertClient*/
    public boolean insertActivite(Client client){
        ContentValues values = new ContentValues();

        values.put(COL_NOM,client.getNom());
        values.put(COL_PRENOM,client.getPrenom());
        values.put(COL_TELEPHONE,client.getPhone());
        values.put(COL_NUM_RUE,client.getNum_rue());
        values.put(COL_NOM_RUE,client.getNom_rue());
        values.put(COL_CP,client.getCodepostal());
        values.put(COL_VILLE,client.getVille());

        SQLiteDatabase db = this.getWritableDatabase();

        boolean createSuccessful = db.insert(TABLE_CLIENT,null,values) > 0;

        return createSuccessful;
    }

    /* getAllCLient*/
    public ArrayList<Client> getAllClient(){
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Client> listeClient = new ArrayList<>();
        String query = "SELECT * FROM CLIENT;";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()){ /* Si le curseur est pas null, on le place au début de la liste */
            do {
                Client client = new Client (cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7)); /* Création d'un Client vide pour le remplir */
                /* Colonne numéro ??? (0 = id, 1 = numero, 2 = nom) */
                client.setId(cursor.getInt(0));
                client.setNom(cursor.getString(1));
                client.setPrenom(cursor.getString(2));
                client.setPhone(cursor.getInt(3));
                client.setNum_rue(cursor.getInt(4));
                client.setNom_rue(cursor.getString(5));
                client.setCodepostal(cursor.getInt(6));
                client.setVille(cursor.getString(7));

                listeClient.add(client); /* Ajout du Client valorisé dans la liste */
            } while(cursor.moveToNext()); /* Tant qu'il reste des éléments à traiter */
        }
        db.close();
        return listeClient;
    }

    /* retrieveClient */
    public Client retrieveActivite(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        /* Requete */
        Cursor cursor = db.query(TABLE_CLIENT, // Nom de table
                new String[] {COL_ID, COL_NOM, COL_PRENOM, COL_TELEPHONE, COL_NUM_RUE, COL_NOM_RUE, COL_CP, COL_VILLE}, // Liste des Colonnes
                COL_ID + "=?", // Colonne cible du WHERE
                new String[] {String.valueOf(id)}, // Valeur cherchée par le WHERE
                null, null, null, null); // Options
        if(cursor != null) cursor.moveToFirst(); /* Si le curseur est pas null, on le place au début de la liste */
        Client client = new Client (cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getInt(4), cursor.getString(5), cursor.getInt(6), cursor.getString(7)); /* Création d'un Client vide pour le remplir */
        /* On peut mettre le cursor.getInt etc ... dans le constructeur directe */
        db.close();
        return client;
    }

    /* updateClient */
    public void updateActivite(Client client){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NOM,client.getNom());
        values.put(COL_PRENOM,client.getPrenom());
        values.put(COL_TELEPHONE,client.getPhone());
        values.put(COL_NUM_RUE,client.getNum_rue());
        values.put(COL_NOM_RUE,client.getNom_rue());
        values.put(COL_CP,client.getCodepostal());
        values.put(COL_VILLE,client.getVille());
        db.update(TABLE_CLIENT, values, COL_ID + "="+ client.getId(), null);
        db.close();
    }

    /*DELETE Client*/
    public void deleteActivite(int id_client)
    {
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_CLIENT, COL_ID + "=" + id_client, null);

        db.close();
    }

}
