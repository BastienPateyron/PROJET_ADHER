package team_adher.adher.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by basti on 12/2/2017.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "ADHER";
    private static final int DATABASE_VERSION = 5; /* A incrémenter quand on modifie la structure de la table */

    /* CREATE */
    private static final String CREATE_TABLE_SECTEUR = "CREATE TABLE SECTEUR" +
            "(" +
            "ID_SECTEUR INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "NUM_SECTEUR INTEGER NOT NULL," +
            "NOM_SECTEUR TEXT NOT NULL" +
            ");";

    private static final String CREATE_TABLE_CLIENT = "CREATE TABLE CLIENT" +
            "(" +
            "ID_CLIENT  INTEGER PRIMARY KEY," +
            "NOM_CLIENT TEXT NOT NULL," +
            "PRENOM_CLIENT TEXT NOT NULL," +
            "TELEPHONE_CLIENT TEXT NOT NULL," +
            "NUM_RUE_CLIENT TEXT NOT NULL," +
            "NOM_RUE_CLIENT TEXT NOT NULL," +
            "CP_CLIENT TEXT NOT NULL" +
            ");";

    private static final String CREATE_TABLE_CONTRAT = "CREATE TABLE CONTRAT" +
            "(" +
            "ID_CONTRAT INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "ID_SECTEUR INTEGER NOT NULL," +
            "DATE_DEBUT_CONTRAT TEXT NOT NULL," +
            "DATE_FIN_CONTRAT TEXT NOT NULL," +
            "FOREIGN KEY(ID_SECTEUR) REFERENCES SECTEUR(ID_SECTEUR)" +
            ");";

    private static final String CREATE_TABLE_ADHESION = "CREATE TABLE ADHESION" +
            "(" +
            "ID_CONTRAT INTEGER PRIMARY KEY NOT NULL," +
            "DATE_DEBUT_CONTRAT TEXT NOT NULL," +
            "DATE_FIN_CONTRAT TEXT NOT NULL," +
            "FOREIGN KEY(ID_CONTRAT) REFERENCES CONTRAT(ID_CONTRAT)" +
            ");";

    private static final String CREATE_TABLE_ADHERENT = "CREATE TABLE ADHERENT" +
            "(" +
            "ID_ADHERENT INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "ID_CONTRAT INTEGER NOT NULL," +
            "RAISON_SOCIALE_ADHERENT TEXT NOT NULL," +
            "NUM_RUE_ADHERENT INTEGER NOT NULL," +
            "NOM_RUE_ADHERENT TEXT NOT NULL," +
            "CP_ADHERENT INTEGER NOT NULL," +
            "NOM_RESPONSABLE_ADHERENT TEXT," +
            "FOREIGN KEY(ID_CONTRAT) REFERENCES ADHESION(ID_CONTRAT)" +
            ");";



    private static final String CREATE_TABLE_INTERVENTION = "CREATE TABLE INTERVENTION" +
            "(" +
            "ID_CONTRAT INTEGER PRIMARY KEY NOT NULL," +
            "ID_ADHERENT INTEGER NOT NULL," +
            "ID_CLIENT  INTEGER NOT NULL," +
            "DATE_DEBUT_CONTRAT TEXT NOT NULL," +
            "DATE_FIN_CONTRAT TEXT NOT NULL," +
            "TARIF_INTERVENTION REAL NOT NULL," +
            "FOREIGN KEY(ID_CONTRAT) REFERENCES CONTRAT(ID_CONTRAT)," +
            "FOREIGN KEY(ID_ADHERENT) REFERENCES ADHERENT(ID_ADHERENT)," +
            "FOREIGN KEY(ID_CLIENT) REFERENCES CLIENT(ID_CLIENT)" +
            ");";

    private static final String CREATE_TABLE_ACTIVITE = "CREATE TABLE ACTIVITE" +
            "(" +
            "ID_ACTIVITE INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "NOM_ACTIVITE TEXT NOT NULL" +
            ");";

    private static final String CREATE_TABLE_FAIRE = "CREATE TABLE FAIRE" +
            "(" +
            "ID_ACTIVITE INTEGER NOT NULL," +
            "ID_ADHERENT INTEGER NOT NULL," +
            "PRIMARY KEY(ID_ACTIVITE, ID_ADHERENT)," +
            "FOREIGN KEY (ID_ACTIVITE) REFERENCES ACTIVITE," +
            "FOREIGN KEY (ID_ADHERENT) REFERENCES ADHERENT" +
            ");";

    private static final String CREATE_TABLE_CONCERNER = "CREATE TABLE CONCERNER" +
            "(" +
            "ID_CONTRAT INTEGER NOT NULL," +
            "ID_ACTIVITE INTEGER NOT NULL," +
            "PRIMARY KEY(ID_CONTRAT, ID_ACTIVITE)," +
            "FOREIGN KEY (ID_CONTRAT) REFERENCES CONTRAT (ID_CONTRAT)," +
            "FOREIGN KEY (ID_ACTIVITE) REFERENCES ACTIVITE (ID_ACTIVITE)" +
            ");";



    /* INSERTS  → Si on veut mettre des valeurs par défaut */


    /* Constructeur */
    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SECTEUR);
        db.execSQL(CREATE_TABLE_CLIENT);
        db.execSQL(CREATE_TABLE_CONTRAT);
        db.execSQL(CREATE_TABLE_ADHESION);
        db.execSQL(CREATE_TABLE_ADHERENT);
        db.execSQL(CREATE_TABLE_INTERVENTION);
        db.execSQL(CREATE_TABLE_ACTIVITE);
        db.execSQL(CREATE_TABLE_FAIRE);
        db.execSQL(CREATE_TABLE_CONCERNER);


       // db.execSQL(INSERT_TABLE_TYPE); // #LES INSTERT C KOM SA

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "CONCERNER");
        db.execSQL("DROP TABLE IF EXISTS " + "FAIRE");
        db.execSQL("DROP TABLE IF EXISTS " + "ACTIVITE");
        db.execSQL("DROP TABLE IF EXISTS " + "INTERVENTION");
        db.execSQL("DROP TABLE IF EXISTS " + "ADHERENT");
        db.execSQL("DROP TABLE IF EXISTS " + "ADHESION");
        db.execSQL("DROP TABLE IF EXISTS " + "CONTRAT");
        db.execSQL("DROP TABLE IF EXISTS " + "CLIENT");
        db.execSQL("DROP TABLE IF EXISTS " + "SECTEUR");
        onCreate(db);
    }

}
