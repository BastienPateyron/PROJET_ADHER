package team_adher.adher.bdd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by basti on 12/2/2017.
 */

public class SQLiteDBHelper extends SQLiteOpenHelper {
    protected static final String DATABASE_NAME = "ADHER";
    private static final int DATABASE_VERSION = 42; /* A incrémenter quand on modifie la structure de la table */


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
            "CP_ADHERENT TEXT NOT NULL  ," +
            "VILLE_ADHERENT TEXT NOT NULL ," +
            "NOM_RESPONSABLE_ADHERENT TEXT ," +
            "NUM_TELEPHONE TEXT NOT NULL" +
            ");";


    private static final String CREATE_TABLE_CONTRAT_SERVICE = "CREATE TABLE CONTRAT_SERVICE (" +
            "ID_CONTRAT_SERVICE INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL  ," +
            "ID_SECTEUR INTEGER NOT NULL  ," +
            "ID_ADHERENT INTEGER NOT NULL ," +
            "DATE_DEBUT_CONTRAT_SERVICE TEXT NOT NULL  ," +
            "DATE_FIN_CONTRAT_SERVICE TEXT NOT NULL ," +
            "TARIF_HT REAL NOT NULL CHECK(TARIF_HT >= 0.00) ," +
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

    // Secteur
    private static final String INSERT_PUYDEDOME = "INSERT INTO SECTEUR VALUES (1, 63, 'PUY DE DOME');";
    private static final String INSERT_ALLIER = "INSERT INTO SECTEUR VALUES (2, 09, 'ALLIER');";
    private static final String INSERT_HAUTE_LOIRE = "INSERT INTO SECTEUR VALUES (3, 43, 'HAUTE-LOIRE');";
    private static final String INSERT_CANTAL = "INSERT INTO SECTEUR VALUES (4, 15, 'CANTAL');";

    // Adhérents
    private static final String INSERT_ADHERANT = "INSERT INTO ADHERENT VALUES " +
            "(1, 'SARL Ledoux', 15, 'Rue du marteau', '63000', 'Clermont Ferrand', 'Ledoux Fred','0658545421');";
    private static final String INSERT_ADHERANT1 = "INSERT INTO ADHERENT VALUES " +
            "(2, 'EARL Frère & co', 78, 'Rue du Ciment', '43000', 'Puy en Velay', 'Tapis Benoit','0473685236');";
    private static final String INSERT_ADHERANT2 = "INSERT INTO ADHERENT VALUES " +
            "(3, 'SARL La Pinède', 98, 'Avenue du Sapin', '63000', 'Clermont Ferrand', 'Lehollandais Francis','0473855365');";
    private static final String INSERT_ADHERANT3 = "INSERT INTO ADHERENT VALUES " +
            "(4, 'EARL Des Briques', 23, 'Boulevard de la Brique', '09000', 'Vichy', 'Pompadour George','0636325417');";

    //Client

    private static final String INSERT_CLIENT = "INSERT INTO CLIENT VALUES" +
            "(1, 'Leger', 'Paul', '0601020301', 33, 'rue du ravallement','63000','Clermont Ferrand');";
    private static final String INSERT_CLIENT1 = "INSERT INTO CLIENT VALUES" +
            "(2, 'Ricard', 'Bob', '0658742698', 29, 'rue de la Fuite','09000','Vichy');";
    private static final String INSERT_CLIENT2 = "INSERT INTO CLIENT VALUES" +
            "(3, 'Dupont', 'Henri', '0605455345', 89, 'rue de la panne','43000','Puy en Velay');";
    private static final String INSERT_CLIENT3 = "INSERT INTO CLIENT VALUES" +
            "(4, 'Macaron', 'Manuel', '0473568978', 3, 'Avenue du débouchage','15000','Aurillac');";

    //Activité

    private static final String INSERT_PLOMBIER = "INSERT INTO ACTIVITE VALUES " +
            "(1, 'Plombier');";
    private static final String INSERT_PEINTRE = "INSERT INTO ACTIVITE VALUES " +
            "(2, 'Peintre');";
    private static final String INSERT_MACON = "INSERT INTO ACTIVITE VALUES " +
            "(3, 'Maçon');";
    private static final String INSERT_ELECTRICIEN = "INSERT INTO ACTIVITE VALUES " +
            "(4, 'Electricien');";

    //Contrat Service
    private static final String INSERT_CONTRAT_SERVICE ="INSERT INTO CONTRAT_SERVICE VALUES" +
            "(1, 1, 1, '20/12/2017', '30/02/2018' , 25.00);";

    // Intervention
    private static final String INSERT_CONTRAT_INTERVENTION = "INSERT INTO CONTRAT_INTERVENTION VALUES" +
            "(1, 1, 1, 1, 1, '21/12/2017', '24/03/2018' );";

    //Concerner
    private static final String INSERT_CONCERNER = "INSERT INTO CONCERNER VALUES" +
            "(1, 1);";




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
        //Secteur :
        db.execSQL(INSERT_PUYDEDOME);
        db.execSQL(INSERT_ALLIER);
        db.execSQL(INSERT_HAUTE_LOIRE);
        db.execSQL(INSERT_CANTAL);

        //ACtivités :
        db.execSQL(INSERT_PLOMBIER);
        db.execSQL(INSERT_ELECTRICIEN);
        db.execSQL(INSERT_PEINTRE);
        db.execSQL(INSERT_MACON);

        //Clients :
        db.execSQL(INSERT_CLIENT);
        db.execSQL(INSERT_CLIENT1);
        db.execSQL(INSERT_CLIENT2);
        db.execSQL(INSERT_CLIENT3);
        //Adhérents
        db.execSQL(INSERT_ADHERANT);
        db.execSQL(INSERT_ADHERANT1);
        db.execSQL(INSERT_ADHERANT2);
        db.execSQL(INSERT_ADHERANT3);
        //Contrat Service :
        db.execSQL(INSERT_CONTRAT_SERVICE);

        //Intervention :
        db.execSQL(INSERT_CONTRAT_INTERVENTION);

        //Concerner :
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
