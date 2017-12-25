package team_adher.adher.fragments.contrat_service;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import team_adher.adher.R;
import team_adher.adher.bdd.ActiviteDAO;
import team_adher.adher.bdd.AdherentDAO;
import team_adher.adher.bdd.ConcernerDAO;
import team_adher.adher.bdd.ContratServiceDAO;
import team_adher.adher.bdd.SecteurDAO;
import team_adher.adher.classes.Activite;
import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.Concerner;
import team_adher.adher.classes.ContratService;
import team_adher.adher.classes.Secteur;
import team_adher.adher.fragments.activité.ActivitéSpinnerCustom;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by basti on 12/16/2017.
 */

public class Contrat_service_ajout_fragment extends DialogFragment {

    private List<String> arrayList_secteur = new ArrayList<>();
    private List<String> arrayList_activite = new ArrayList<>();
    private ImageButton btn_delete;
    private EditText date_debut_contrat;
    private EditText date_fin_contrat;
    private EditText tarif_ht;
    private Calendar myCalendar = Calendar.getInstance();
    private String editext_state; //Use to determine which editext is selected
    private static Context mContext;
    private static FragmentManager fragmentManager;
    private boolean state_for_spinner = false;
    private ContratService contratService;

    // Elements pour l'instanciation du contrat
    private int idSecteur;
    private int idAdherent;

    public static Contrat_service_modif_dialog newInstance(Context context, FragmentManager fragmentManager1) {
        Contrat_service_modif_dialog contrat_service_add_dialog = new Contrat_service_modif_dialog();
        mContext = context;
        fragmentManager = fragmentManager1;
        return contrat_service_add_dialog;
    }

    // onCreate --> (onCreateDialog) --> onCreateView --> onActivityCreated)
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View dialogView = inflater.inflate(R.layout.adherents_layout_contrat_add_alertdialog, container, false);
//        getActivity().setTitle("Ajout Contrat Service");
        btn_delete = dialogView.findViewById(R.id.btn_delete);
        btn_delete.setVisibility(View.INVISIBLE);

        // Récupération de l'ID Adherent et de l'ID Contrat
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            if(!(bundle.getString("id_adherent") == null)){
                idAdherent = Integer.valueOf(bundle.get("id_adherent").toString());
            }
        }


        // SECTEUR SPINNER 2
        final Spinner spinner_secteur_cs = dialogView.findViewById(R.id.spinner_secteur_cs); // Création du spinner
        final ArrayList<Secteur> array_secteur; // Création de la liste de secteurs
        final SecteurDAO secteurDAO = new SecteurDAO(getContext()); // Creation de l'object secteur DAO
        array_secteur =  secteurDAO.getAllSecteur(); // Remplissage de la liste des secteurs

        final ArrayAdapter<Secteur> adapter_secteur = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, array_secteur); // Création de l'adapter
        adapter_secteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Définition du style de l'adapter
        spinner_secteur_cs.setAdapter(adapter_secteur); // Affectation de l'adapter au spinner

        // Récupération de l'ID secteur sélectionné
        spinner_secteur_cs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter_secteur.getItem(position).getId() == -1) { // Si l'item est l'item par défaut on fait rien

                } else idSecteur = adapter_secteur.getItem(position).getId(); // On récupère l'ID du secteur selectionné
            }
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        //ACTIVITE SPINNER

        /*Get data*/
        final Spinner spinner_activite_cs = (Spinner) dialogView.findViewById(R.id.spinner_activite_cs);
        ArrayList<Activite> array_act = new ArrayList<>();
        final ActiviteDAO activiteDAO = new ActiviteDAO(getContext());
        array_act = activiteDAO.getAllActivite();
        /*Define adapter for spinner*/
        final ActivitéSpinnerCustom activiteActivitéSpinnerCustom = new ActivitéSpinnerCustom(getActivity(), android.R.layout.simple_spinner_item);
        activiteActivitéSpinnerCustom.addAll(array_act);
        Activite act_hint = new Activite(-1, "Selectionner une activité");
        activiteActivitéSpinnerCustom.add(act_hint);
        activiteActivitéSpinnerCustom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_activite_cs.setAdapter(activiteActivitéSpinnerCustom);
        spinner_activite_cs.setSelection(activiteActivitéSpinnerCustom.getCount());

        /*Add value on textview the selected item*/
        final TextView value_act1 = (TextView) dialogView.findViewById(R.id.value_act1);
        final TextView value_act2 = (TextView) dialogView.findViewById(R.id.value_act2);
        final TextView value_act3 = (TextView) dialogView.findViewById(R.id.value_act3);

        final HashMap<Integer, Integer> id_activite = new HashMap<Integer, Integer>(); //Retiens les id des activites deja ajoutés

        spinner_activite_cs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (activiteActivitéSpinnerCustom.getItem(position).getId() == -1) {

                } else {
                    boolean already_add = false; //Variable pour eviter les doublons

                    /*On parcours le tableau des id pour verifier si l'activité selectionnée n'est pas deja selectionnée */
                    for (Integer key : id_activite.keySet()) {
                        if (activiteActivitéSpinnerCustom.getItem(position).getId() == id_activite.get(key)) {
                            already_add = true;
                        }
                    }

                    if (TextUtils.isEmpty(value_act1.getText().toString()) && !already_add) {
                        value_act1.setText(activiteActivitéSpinnerCustom.getItem(position).getNom());
                        id_activite.put(1, activiteActivitéSpinnerCustom.getItem(position).getId());

                    } else if (TextUtils.isEmpty(value_act2.getText().toString()) && !already_add) {
                        value_act2.setText(activiteActivitéSpinnerCustom.getItem(position).getNom());
                        id_activite.put(2, activiteActivitéSpinnerCustom.getItem(position).getId());

                    } else if (TextUtils.isEmpty(value_act3.getText().toString()) && !already_add) {

                        value_act3.setText(activiteActivitéSpinnerCustom.getItem(position).getNom());
                        id_activite.put(3, activiteActivitéSpinnerCustom.getItem(position).getId());

                    } else {
                        if (already_add) {
                            Toast.makeText(getActivity(), "Cette activité est déja ajoutée", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Nombre d'activités maximales atteintes", Toast.LENGTH_LONG).show();
                        }
                    }
                }
            } // to close the onItemSelected

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //DATE PICKER SETTINGS
        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };
        //DATE DEBUT CONTRAT


        date_debut_contrat = (EditText) dialogView.findViewById(R.id.value_date_debut_cs);
        final DatePickerDialog datePicker_debut_ct = new DatePickerDialog(getActivity(), R.style.DatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        date_debut_contrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editext_state = "DEBUT_CONTRAT";
                datePicker_debut_ct.show();
            }
        });

        //DATE FIN CONTRAT
        date_fin_contrat = (EditText) dialogView.findViewById(R.id.value_date_fin_cs);
        final DatePickerDialog datePicker_fin_ct = new DatePickerDialog(getActivity(), R.style.DatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));
        date_fin_contrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editext_state = "FIN_CONTRAT";
                datePicker_fin_ct.show();
            }
        });

        // TARIF_HT
        tarif_ht = (EditText) dialogView.findViewById(R.id.value_tarif_ht_cs);
        tarif_ht.setText("0.00");

        //BUTTON
        Button buttonPos = (Button) dialogView.findViewById(R.id.pos_button);
        buttonPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // -- CREATION DU  CONTRAT_SERVICE --

                //Récupérer Secteur
                System.out.println("Id Secteur = " + idSecteur);
                Secteur secteur = secteurDAO.retrieveSecteur(idSecteur);

                // Récupérer adhérent depuis le fragment adherent_fragment_modif
                System.out.println("Id Adherent: " + idAdherent);
                AdherentDAO adherentDAO = new AdherentDAO(getContext());
                Adherent adherent = adherentDAO.retrieveAdherent(idAdherent);

                // Recupérer les dates
                System.out.println("Date Debut: " + date_debut_contrat.getText());
                System.out.println("Date Fin: " + date_fin_contrat.getText());


                if(tarif_ht.getText().toString().equals("")) tarif_ht.setText("0.00");
                // Insert un nouveau contrat
                ContratService contratService = new ContratService(0, secteur, adherent, date_debut_contrat.getText().toString(), date_fin_contrat.getText().toString(), Double.valueOf(tarif_ht.getText().toString()));

                System.out.println("Tarif HT: " + tarif_ht.getText().toString());
                if(champsRemplis(contratService)){ // Si tout les champs sont bien remplis on réalise l'insertion
                    ContratServiceDAO contratServiceDAO = new ContratServiceDAO(getContext());
                    contratServiceDAO.insertContratService(contratService);

                    // Récupérer l'ID du contrat ajouté
                    int idContratService = contratServiceDAO.retrieveLastContratServiceID(getContext());
                    contratService = contratServiceDAO.retrieveContratService(idContratService, getContext());

                    // Faire une boucle qui Insert les activites dans la table avec cet IDContratService
                    ConcernerDAO concernerDAO = new ConcernerDAO(getContext());
                    Concerner concerner = new Concerner();

                    for (Integer i : id_activite.keySet()) { // On va lister la liste des clés "id_activite.keySet()"
                        System.out.println("idActivite ajoutée " + i + ": " +id_activite.get(i));

                        concerner.setContratService(contratService);

                        Activite activite = activiteDAO.retrieveActivite(id_activite.get(i));
                        concerner.setActivite(activite);

                        concernerDAO.insertConcerner(concerner);
                    }
                    Toast.makeText(getActivity(), "Contrat ajouté", Toast.LENGTH_SHORT).show();
                    dismiss();
                }
            }
        });

        // "Cancel" button
        Button buttonNeg = (Button) dialogView.findViewById(R.id.neg_button);
        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getShowsDialog())
                    getDialog().cancel();
                else
                    dismiss();
            }
        });

        return dialogView;
    }

    // If shown as dialog, set the width of the dialog window
    // onCreateView --> onActivityCreated -->  onViewStateRestored --> onStart --> onResume
    @Override
    public void onResume() {
        super.onResume();
        if (getShowsDialog()) {
            // Set the width of the dialog to the width of the screen in portrait mode
            DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
            int dialogWidth = Math.min(metrics.widthPixels, metrics.heightPixels);
            getDialog().getWindow().setLayout(dialogWidth, WRAP_CONTENT);
        }
    }


    // If dialog is cancelled: onCancel --> onDismiss
    @Override
    public void onCancel(DialogInterface dialog) {

    }

    // If dialog is cancelled: onCancel --> onDismiss
    // If dialog is dismissed: onDismiss
    @Override
    public void onDismiss(DialogInterface dialog) {

    }

    private boolean champsRemplis(ContratService contratService){
        boolean isSet = true;
        // TODO véfifier si les champs obligatoires sont remplis
        if (contratService.getSecteur().equals("")){
            Toast.makeText(getActivity(), "Secteur manquant", Toast.LENGTH_SHORT).show();
            isSet = false;
        } else if(contratService.getAdherent().equals("")){
            Toast.makeText(getActivity(), "Adhérent manquant", Toast.LENGTH_SHORT).show();
            isSet = false;
        } else if (contratService.getDate_debut().equals("")){
            Toast.makeText(getActivity(), "Date de début manquante", Toast.LENGTH_SHORT).show();
            isSet = false;
        } else if (contratService.getDate_fin().equals("")){
            Toast.makeText(getActivity(), "Date de fin manquante", Toast.LENGTH_SHORT).show();
            isSet = false;
        } else if (tarif_ht.getText().toString().equals("")){
            tarif_ht.setText(0); // On met le tarif à 0
            Toast.makeText(getActivity(), "Date de fin manquante", Toast.LENGTH_SHORT).show();
            isSet = false;
        }

        return isSet;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        if (editext_state.equals("DEBUT_CONTRAT")) {
            date_debut_contrat.setText(sdf.format(myCalendar.getTime()));
        }
        if (editext_state.equals("FIN_CONTRAT")) {
            date_fin_contrat.setText(sdf.format(myCalendar.getTime()));
        }
    }


}
