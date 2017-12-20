package team_adher.adher.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by basti on 12/2/2017.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "ADHER";
    private static final int DATABASE_VERSION = 1; /* A incrémenter quand on modifie la structure de la table */

    // TODO Penser à incrémenter la version de la base après les changements

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
            "CP_CLIENT TEXT NOT NULL," +
            "VILLE_CLIENT TEXT NOT NULL" +
            ");";

    private static final String CREATE_TABLE_ADHERENT = "CREATE TABLE ADHERENT (" +
            "ID_ADHERENT INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ," +
            "RAISON_SOCIALE_ADHERENT TEXT NOT NULL  ," +
            "NUM_RUE_ADHERENT INTEGER NOT NULL  ," +
            "NOM_RUE_ADHERENT TEXT NOT NULL  ," +
            "CP_ADHERENT INTEGER NOT NULL  ," +
            "VILLE_ADHERENT TEXT NOT NULL ," +
            "NOM_RESPONSABLE_ADHERENT TEXT ," +
            "NUM_TELEPHONE INTEGER NOT NULL" +
            ");";


    private static final String CREATE_TABLE_CONTRAT_SERVICE = "CREATE TABLE CONTRAT_SERVICE (" +
            "ID_CONTRAT_SERVICE INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ," +
            "ID_SECTEUR INTEGER NOT NULL  ," +
            "ID_ADHERENT INTEGER NOT NULL ," +
            "DATE_DEBUT_CONTRAT_SERVICE TEXT NOT NULL  ," +
            "DATE_FIN_CONTRAT_SERVICE TEXT NOT NULL ," +
            "TARIF_HT REAL DEFAULT 0.00 CHECK(TARIF_HT >= 0.00) ," +
            "FOREIGN KEY (ID_SECTEUR) REFERENCES SECTEUR (ID_SECTEUR) ," +
            "FOREIGN KEY (ID_ADHERENT) REFERENCES ADHERENT (ID_ADHERENT)" +
            ");";


    private static final String CREATE_TABLE_ACTIVITE = "CREATE TABLE ACTIVITE" +
            "(" +
            "ID_ACTIVITE INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
            "NOM_ACTIVITE TEXT NOT NULL" +
            ");";

    private static final String CREATE_TABLE_CONCERNER = "CREATE TABLE CONCERNER" +
            "(" +
            "ID_CONTRAT INTEGER NOT NULL," +
            "ID_ACTIVITE INTEGER NOT NULL," +
            "PRIMARY KEY(ID_CONTRAT, ID_ACTIVITE)," +
            "FOREIGN KEY (ID_CONTRAT) REFERENCES CONTRAT (ID_CONTRAT)," +
            "FOREIGN KEY (ID_ACTIVITE) REFERENCES ACTIVITE (ID_ACTIVITE)" +
            ");";


    private static final String CREATE_TABLE_CONTRAT_INTERVENTION = "CREATE TABLE CONTRAT_INTERVENTION (" +
            "ID_CONTRAT_INTERVENTION INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ," +
            "ID_SECTEUR INTEGER NOT NULL ," +
            "ID_ACTIVITE INTEGER NOT NULL ," +
            "ID_ADHERENT INTEGER NOT NULL  ," +
            "ID_CLIENT INTEGER NOT NULL  ," +
            "DATE_DEBUT_CONTRAT_INTERVENTION TEXT NOT NULL  ," +
            "DATE_FIN_CONTRAT_INTERVENTION TEXT NOT NULL   ," +
            "FOREIGN KEY (ID_SECTEUR) REFERENCES SECTEUR (ID_SECTEUR) ," +
            "FOREIGN KEY (ID_ACTIVITE) REFERENCES ACTIVITE (ID_ACTIVITE) ," +
            "FOREIGN KEY (ID_ADHERENT) REFERENCES ADHERENT (ID_ADHERENT) ," +
            "FOREIGN KEY (ID_CLIENT) REFERENCES CLIENT (ID_CLIENT)" +
            ");";



    /* INSERTS  → Si on veut mettre des valeurs par défaut */
    /*private static final String INSERT_TABLE_ADHERANT */
    private static final String INSERT_PUYDEDOME = "INSERT INTO SECTEUR VALUES (0, 63, 'PUY DE DOME');";
    private static final String INSERT_ALLIER = "INSERT INTO SECTEUR VALUES (1, 09, 'ALLIER');";
    private static final String INSERT_ADHERANT = "INSERT INTO ADHERENT VALUES " +
            "(0, 'SARL', 15, 'rue_du_marteau', 63000, 'Clermont Ferrand', 'Joe',0455855365);";
    private static final String INSERT_CLIENT = "INSERT INTO CLIENT VALUES" +
            "(0, 'Bob', 'noBricolee', 0601020301,33, 'rue du ravallement',63000,'Clermont Ferrand');";
    private static final String INSERT_PLOMBIER = "INSERT INTO ACTIVITE VALUES " +
            "(0, 'Plombier');";
    private static final String INSERT_PEINTRE = "INSERT INTO ACTIVITE VALUES " +
            "(1, 'Peintre');";
    private static final String INSERT_MACON = "INSERT INTO ACTIVITE VALUES " +
            "(2, 'Maçon');";
    private static final String INSERT_ELECTRICIEN = "INSERT INTO ACTIVITE VALUES " +
            "(3, 'Electricien');";
    private static final String INSERT_CONTRAT_SERVICE ="INSERT INTO CONTRAT_SERVICE VALUES" +
            "(0, 1, 1, '20/12/2017', '30/12/2017' , 25);";
    private static final String INSERT_CONTRAT_INTERVENTION = "INSERT INTO CONTRAT_INTERVENTION VALUES" +
            "(0, 1, 1, 1, 1, '21/12/2017', '24/12/2017' );";
    private static final String INSERT_CONCERNER = "INSERT INTO CONCERNER VALUES" +
            "(1, 1);";
    //private static final String INSERT_CLIENT = "INSERT INTO CLIENT VALUES (0,"



    /* Constructeur */
    public SQLiteDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SECTEUR);
        db.execSQL(CREATE_TABLE_CLIENT);
        db.execSQL(CREATE_TABLE_ADHERENT);
        db.execSQL(CREATE_TABLE_CONTRAT_SERVICE);
        db.execSQL(CREATE_TABLE_ACTIVITE);
        db.execSQL(CREATE_TABLE_CONCERNER);
        db.execSQL(CREATE_TABLE_CONTRAT_INTERVENTION);

        /* Inserts  Jeu d'essai*/
        db.execSQL(INSERT_PUYDEDOME);
        db.execSQL(INSERT_ALLIER);
        db.execSQL(INSERT_ADHERANT);
        db.execSQL(INSERT_PLOMBIER);
        db.execSQL(INSERT_ELECTRICIEN);
        db.execSQL(INSERT_PEINTRE);
        db.execSQL(INSERT_MACON);
        db.execSQL(INSERT_CLIENT);
        db.execSQL(INSERT_CONTRAT_SERVICE);
        db.execSQL(INSERT_CONTRAT_INTERVENTION);
        db.execSQL(INSERT_CONCERNER);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + "CONTRAT_INTERVENTION");
        db.execSQL("DROP TABLE IF EXISTS " + "CONCERNER");
        db.execSQL("DROP TABLE IF EXISTS " + "ACTIVITE");
        db.execSQL("DROP TABLE IF EXISTS " + "CONTRAT_SERVICE");
        db.execSQL("DROP TABLE IF EXISTS " + "ADHERENT");
        db.execSQL("DROP TABLE IF EXISTS " + "CLIENT");
        db.execSQL("DROP TABLE IF EXISTS " + "SECTEUR");
        onCreate(db);
    }

}
