package team_adher.adher.fragments.intervention;

import android.support.v4.app.Fragment;

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

import team_adher.adher.MainActivity;
import team_adher.adher.R;
import team_adher.adher.bdd.ActiviteDAO;
import team_adher.adher.bdd.AdherentDAO;
import team_adher.adher.bdd.ClientDAO;
import team_adher.adher.bdd.InterventionDAO;
import team_adher.adher.bdd.SecteurDAO;
import team_adher.adher.classes.Activite;
import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.Client;
import team_adher.adher.classes.Intervention;
import team_adher.adher.classes.Secteur;

import static android.widget.ListPopupWindow.WRAP_CONTENT;

/**
 * Created by watson on 04/01/2018.
 */

public class Intervention_fragment_ajout extends DialogFragment {

    private List<String> arrayList_secteur = new ArrayList<>();
    private List<String> arrayList_activite = new ArrayList<>();
    private List<String> arrayList_client = new ArrayList<>();
    private EditText date_debut_contrat;
    private EditText date_fin_contrat;
    private EditText tarif_ht;
    private Calendar myCalendar = Calendar.getInstance();
    private String editext_state; //Use to determine which editext is selected
    private static Context mContext;
    private static FragmentManager fragmentManager;
    private boolean state_for_spinner = false;
    private Intervention intervention;

    private int id_secteur;
    private int id_client;
    private int id_activite;

    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {

        View dialogView = inflater.inflate(R.layout.intervention_layout, container, false);
//        getActivity().setTitle("Ajout Contrat Service");


        // Récupération de l'ID Adherent et de l'ID Contrat
        Bundle bundle = this.getArguments();

       /*if (bundle != null) {
            if(!(bundle.getString("id_adherent") == null)){
                idAdherent = Integer.valueOf(bundle.get("id_adherent").toString());
            }
        }*/






        // activite SPINNER 2
        final Spinner spinner_client_cs = dialogView.findViewById(R.id.spinner_client_cs); // Création du spinner
        final ArrayList<Client> array_client; // Création de la liste de secteurs
        final ClientDAO clientDAO = new ClientDAO(getContext()); // Creation de l'object secteur DAO
        array_client = clientDAO.getAllClient(); // Remplissage de la liste des secteurs

        final ArrayAdapter<Client> adapter_client = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, array_client); // Création de l'adapter
        adapter_client.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Définition du style de l'adapter
        spinner_client_cs.setAdapter(adapter_client); // Affectation de l'adapter au spinner

        // Récupération de l'ID secteur sélectionné
        spinner_client_cs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter_client.getItem(position).getId() == -1) { // Si l'item est l'item par défaut on fait rien

                } else id_client = adapter_client.getItem(position).getId(); // On récupère l'ID du secteur selectionné
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // activite SPINNER 2
        final Spinner spinner_activite_cs = dialogView.findViewById(R.id.spinner_activite_cs); // Création du spinner
        final ArrayList<Activite> array_activite; // Création de la liste de secteurs
        final ActiviteDAO activiteDAO = new ActiviteDAO(getContext()); // Creation de l'object secteur DAO
        array_activite = activiteDAO.getAllActivite(); // Remplissage de la liste des secteurs

        final ArrayAdapter<Activite> adapter_activite = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, array_activite); // Création de l'adapter
        adapter_activite.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Définition du style de l'adapter
        spinner_activite_cs.setAdapter(adapter_activite); // Affectation de l'adapter au spinner

        // Récupération de l'ID secteur sélectionné
        spinner_activite_cs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter_activite.getItem(position).getId() == -1) { // Si l'item est l'item par défaut on fait rien

                } else id_secteur = adapter_activite.getItem(position).getId(); // On récupère l'ID du secteur selectionné
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



        // SECTEUR SPINNER 2
        final Spinner spinner_secteur_cs = dialogView.findViewById(R.id.spinner_secteur_int); // Création du spinner
        final ArrayList<Secteur> array_secteur; // Création de la liste de secteurs
        final SecteurDAO secteurDAO = new SecteurDAO(getContext()); // Creation de l'object secteur DAO
        array_secteur = secteurDAO.getAllSecteur(); // Remplissage de la liste des secteurs

        final ArrayAdapter<Secteur> adapter_secteur = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, array_secteur); // Création de l'adapter
        adapter_secteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Définition du style de l'adapter
        spinner_secteur_cs.setAdapter(adapter_secteur); // Affectation de l'adapter au spinner

        // Récupération de l'ID secteur sélectionné
        spinner_secteur_cs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (adapter_secteur.getItem(position).getId() == -1) { // Si l'item est l'item par défaut on fait rien

                } else id_secteur = adapter_secteur.getItem(position).getId(); // On récupère l'ID du secteur selectionné
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //ACTIVITE SPINNER

        /*Get data*/


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


        //BUTTON
        Button buttonPos = (Button) dialogView.findViewById(R.id.pos_button);
        buttonPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // -- CREATION DU  CONTRAT_SERVICE --



                // Récupérer un client
                System.out.println("Id Client: " + id_client);
                ClientDAO clientDAO = new ClientDAO(getContext());
                Client client = clientDAO.retrieveClient(id_client);

                //Récupérer une activité
                System.out.println("Id Activité" + id_activite);
                Activite activite = activiteDAO.retrieveActivite(id_activite);

                //Récupérer Secteur
                System.out.println("Id Secteur = " + id_secteur);
                Secteur secteur = secteurDAO.retrieveSecteur(id_secteur);

                //Récupérer Adherent
                AdherentDAO adherentDAO = new AdherentDAO(getContext());
                Adherent adherent = adherentDAO.retrieveAdherent(0);

                // Recupérer les dates
                System.out.println("Date Debut: " + date_debut_contrat.getText());
                System.out.println("Date Fin: " + date_fin_contrat.getText());


                // Insert un nouveau contrat
                Intervention intervention = new Intervention(
                        0,
                        secteur,
                        activite,
                        adherent,
                        client,
                        date_debut_contrat.getText().toString(),
                        date_fin_contrat.getText().toString()
                );

                if(champsRemplis(intervention)){ // Si tout les champs sont bien remplis on réalise l'insertion
                    InterventionDAO interventionDAO = new InterventionDAO(getContext());
                    interventionDAO.insertIntervention(intervention);

                    // Récupérer l'ID de intervention
                    int id_intervention= interventionDAO.retrieveLastInterventionID(getContext());
                    intervention = interventionDAO.retrieveIntervention(id_intervention, getContext());


                    }
                    Toast.makeText(getActivity(), "Contrat ajouté", Toast.LENGTH_SHORT).show();
                ((MainActivity) getActivity()).changeFragment(new Intervention_fragment_home());
                    dismiss();
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
                    //dismiss();
                ((MainActivity) getActivity()).changeFragment(new Intervention_fragment_home());


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
    private DialogInterface.OnDismissListener onDismissListener;

    public void setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (onDismissListener != null) {
            onDismissListener.onDismiss(dialog);
        }
    }

    private boolean champsRemplis(Intervention intervention){
        boolean isSet = true;
        // TODO véfifier si les champs obligatoires sont remplis
        if (intervention.getClient().equals("")){
            Toast.makeText(getActivity(), "Client manquant", Toast.LENGTH_SHORT).show();
            isSet = false;
        } else if(intervention.getActivite().equals("")) {
            Toast.makeText(getActivity(), "Activite manquant", Toast.LENGTH_SHORT).show();
            isSet = false;
        } else if(intervention.getActivite().equals("")) {
            Toast.makeText(getActivity(), "Secteur manquant", Toast.LENGTH_SHORT).show();
            isSet = false;

        } else if (intervention.getDate_debut().equals("")){
            Toast.makeText(getActivity(), "Date de début manquante", Toast.LENGTH_SHORT).show();
            isSet = false;
        } else if (intervention.getDate_fin().equals("")){
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





