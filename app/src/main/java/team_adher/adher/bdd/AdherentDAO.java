package team_adher.adher.bdd;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.ContratService;
import team_adher.adher.classes.Intervention;

import static android.content.ContentValues.TAG;


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
        Log.d(TAG, "retrieveAdherent: ID Adherent retrieve = " + id);
        Cursor cursor = db.query(TABLE_ADHERENT, // Nom table
                new String[] { COL_ID, COL_RAISON_SOCIALE, COL_NUM_RUE, COL_NOM_RUE, COL_CP, COL_VILLE, COL_NOM_RESPONSABLE, COL_NUM_TELEPHONE }, // Liste des colonnes
                COL_ID + "=?",  // Colonne cible du WHERE
                new String[] { String.valueOf(id) }, // Valeure cible du WHERE
                null, null, null, null); // Options
        if (cursor != null){
            cursor.moveToFirst();

            /* On récupère chaque élément dans l'ordre de la table (Haut en bas) */
            adherent.setId(cursor.getInt(0));
            adherent.setRaison_sociale(cursor.getString(1));
            adherent.setNum_rue(cursor.getInt(2));
            adherent.setNom_rue(cursor.getString(3));
            adherent.setCp(cursor.getString(4));
            adherent.setVille(cursor.getString(5));
            adherent.setNom_responsable(cursor.getString(6));
            adherent.setTelephone(cursor.getString(7));

            db.close();
            return adherent;

        }else{ // Si le curseur == null alors le retrieve a échoué donc l'adhérent n'est pas la. Donc on retourne null
            System.out.println("L'Adhérent " + id + " n'existe plus !");
            db.close();
            return null;
        }
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
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
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
        InterventionDAO interventionDAO = new InterventionDAO(context);
        ArrayList<Intervention> interventionArrayList = interventionDAO.getAllInterventionOf(context, "ADHERENT",  id_adherent);
        for(Intervention intervention : interventionArrayList) interventionDAO.deleteIntervention(intervention.getId());

        // Supprimer l'adhérent
        db.delete(TABLE_ADHERENT, COL_ID + "=" + id_adherent, null);
        System.out.println("Adhérent Id " + id_adherent + " supprimé");
        db.close();
    }

    public ArrayList<Adherent> findAdherent(int idSecteur, int idActivite, String dateDebut, String dateFin){

        SQLiteDatabase db = this.getReadableDatabase();

        String fieldAdherent = "ADHERENT.ID_ADHERENT, ADHERENT.RAISON_SOCIALE_ADHERENT, ADHERENT.NUM_RUE_ADHERENT, ADHERENT.NOM_RUE_ADHERENT, ADHERENT.CP_ADHERENT, ADHERENT.VILLE_ADHERENT, ADHERENT.NOM_RESPONSABLE_ADHERENT, ADHERENT.NUM_TELEPHONE";

         // Sélection des adhérents correspondants (Secteur + Activité) et COUNT du nombre de leurs interventions
        String query = "SELECT " + fieldAdherent + ", COUNT(CONTRAT_INTERVENTION.ID_ADHERENT) AS 'NB_INTERV' " +
            "FROM ADHERENT, CONTRAT_SERVICE, CONCERNER, ACTIVITE, SECTEUR " +
            "LEFT JOIN CONTRAT_INTERVENTION ON ADHERENT.ID_ADHERENT = CONTRAT_INTERVENTION.ID_ADHERENT " +
            "WHERE ADHERENT.ID_ADHERENT = CONTRAT_SERVICE.ID_ADHERENT " +
            "AND CONTRAT_SERVICE.ID_SECTEUR = SECTEUR.ID_SECTEUR " +
            "AND CONTRAT_SERVICE.ID_CONTRAT_SERVICE = CONCERNER.ID_CONTRAT " +
            "AND CONCERNER.ID_ACTIVITE = ACTIVITE.ID_ACTIVITE " +
            "AND SECTEUR.ID_SECTEUR = " + idSecteur + " " +
            "AND ACTIVITE.ID_ACTIVITE = " + idActivite + " " +
            "GROUP BY ADHERENT.ID_ADHERENT;";

        /* Requete */

        ArrayList<Adherent> listeAdherents = new ArrayList<>();
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){ /* Si le curseur est pas null, on le place au début de la liste */
            do{
                Adherent adherent = new Adherent(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                );

                System.out.println("Adherent " + adherent.getId() + " / nom: " + adherent.getRaison_sociale() + " / Nb Intervention: " + cursor.getInt(8));

                adherent.setNbInterventions(cursor.getInt(8));


                listeAdherents.add(adherent);
            }while(cursor.moveToNext()); /* Tant que l'élément suivant existe */


        } else {
            Log.d(TAG, "findAdherent: Aucun adhérent correspondant");
        }
        db.close();
        System.out.println("Taille listeAdherent Find: " + listeAdherents.size());

        return listeAdherents;
    }


    public static boolean dateDepassee(Calendar dateCalendar){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateFormatee = sdf.format(dateCalendar.getTime());
        Log.d(TAG, "dateDepassee: dateFormatée = [ " + dateFormatee + " ]");

        return true;
    }

    /* Fonction qui retourne l'indice de l'Adhérent avec le moins d'interventions à son actif */
    public int minIntervention(ArrayList<Adherent> list){
        int min = 0; // Position du min dans la liste
        int compteur = 0;

        for (Adherent ad : list) { // On va lister la liste des clés "id_activite.keySet()"
            Log.d(TAG, "minIntervention: NbIntervention Adhérent " + ad.getId() + ": " + ad.getNbInterventions());
            if(ad.getNbInterventions() <= list.get(min).getNbInterventions() ) min = compteur;
            compteur++;
        }

        Log.d(TAG, "minIntervention == " + min);
        return min;
    }
}
