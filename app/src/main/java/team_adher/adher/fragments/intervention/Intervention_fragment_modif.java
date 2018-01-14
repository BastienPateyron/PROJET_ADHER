package team_adher.adher.fragments.intervention;

import android.app.Activity;
import android.app.AlertDialog;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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

/**
 * Created by François on 04/01/2018.
 */

public class Intervention_fragment_modif extends DialogFragment {

    private List<String> arrayList_client = new ArrayList<>();
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
    private Intervention intervention;

    // Elements pour l'instanciation du contrat
    private int id_Secteur;
    private int id_Adherent;
    private int id_Client;
    private int id_Activite;
    private int id_Intervention;
    private Activity myView;

    public static Intervention_fragment_modif newInstance(Context context, FragmentManager fragmentManager1) {
        Intervention_fragment_modif intervention_fragment_modif = new Intervention_fragment_modif();
        mContext = context;
        fragmentManager = fragmentManager1;
        return intervention_fragment_modif;
    }

    // onCreate --> (onCreateDialog) --> onCreateView --> onActivityCreated)
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View dialogView = inflater.inflate(R.layout.intervention_layout_modif, container, false);
        btn_delete = dialogView.findViewById(R.id.btn_delete);
        btn_delete.setVisibility(View.VISIBLE);

        // Récupération de l'ID Adherent et de l'ID Contrat
        Bundle bundle = this.getArguments();

            if (bundle != null) {
                if(!(bundle.getString("id_client") == null)){
                    id_Client = Integer.valueOf(bundle.get("id_client").toString());
                }
            }

        if (!(bundle.getString("id_intervention") == null)) {
            // On instancie un Contrat
            id_Intervention = Integer.valueOf(bundle.get("id_intervention").toString());
            InterventionDAO interventionDAO = new InterventionDAO(getContext());
            intervention = interventionDAO.retrieveIntervention(id_Intervention, getContext());
            id_Adherent = intervention.getAdherent().getId();


            // Delete button
            btn_delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder;
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                    } else {
                        builder = new AlertDialog.Builder(getContext());
                    }
                    builder.setTitle("Supprimer Intervention").setMessage("Voulez-vous supprimer cette intervention de manière définitive ?").setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            InterventionDAO interventionDAO = new InterventionDAO(getContext());
                            interventionDAO.deleteIntervention(id_Intervention);
                            if (getShowsDialog()) getDialog().cancel();
                            else ((MainActivity) getActivity()).changeFragment(new Intervention_fragment_home());;
                        }
                    }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if (getShowsDialog()) getDialog().cancel();
                            else dismiss();
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();

//
                }
            });


            //Affichage de l'adhérent
            final TextView adherent_value = (TextView) dialogView.findViewById(R.id.value_id_adherent);

            final AdherentDAO adherentDAO = new AdherentDAO(getContext());
            final Adherent adherent = adherentDAO.retrieveAdherent(id_Adherent);

            adherent_value.setText(adherent.getNom_responsable());


            //Spinner Client
            final Spinner spinner_client_cs = dialogView.findViewById(R.id.spinner_client_cs); // Création du spinner
            final ArrayList<Client> array_client; // Création de la liste de secteurs
            final ClientDAO clientDAO = new ClientDAO(getContext()); // Creation de l'object secteur DAO
            array_client = clientDAO.getAllClient(); // Remplissage de la liste des secteurs

            final ArrayAdapter<Client> adapter_client = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, array_client); // Création de l'adapter
            adapter_client.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Définition du style de l'adapter
            spinner_client_cs.setAdapter(adapter_client); // Affectation de l'adapter au spinner

            if(! intervention.getClient().getNom().toString().equals(null)) spinner_client_cs.setSelection(getIndex(spinner_client_cs, intervention.getClient().toString()));
            // Récupération de l'ID secteur sélectionné
            spinner_client_cs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (adapter_client.getItem(position).getId() == -1) { // Si l'item est l'item par défaut on fait rien

                    } else id_Client = adapter_client.getItem(position).getId(); // On récupère l'ID du secteur selectionné
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            //Spinner Activite
            final Spinner spinner_activite_cs = dialogView.findViewById(R.id.spinner_activite_cs); // Création du spinner
            final ArrayList<Activite> array_activite; // Création de la liste de secteurs
            final ActiviteDAO activiteDAO = new ActiviteDAO(getContext()); // Creation de l'object secteur DAO
            array_activite = activiteDAO.getAllActivite(); // Remplissage de la liste des secteurs

            final ArrayAdapter<Activite> adapter_activite = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, array_activite); // Création de l'adapter
            adapter_activite.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Définition du style de l'adapter
            spinner_activite_cs.setAdapter(adapter_activite); // Affectation de l'adapter au spinner

            if(! intervention.getActivite().getNom().toString().equals(null)) spinner_activite_cs.setSelection(getIndex(spinner_activite_cs, intervention.getActivite().toString()));
            // Récupération de l'ID secteur sélectionné
            spinner_activite_cs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (adapter_activite.getItem(position).getId() == -1) { // Si l'item est l'item par défaut on fait rien

                    } else id_Activite = adapter_activite.getItem(position).getId(); // On récupère l'ID du secteur selectionné
                }

                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

            // SECTEUR SPINNER
            final Spinner spinner_secteur_int = dialogView.findViewById(R.id.spinner_secteur_int); // Création du spinner
            final ArrayList<Secteur> array_secteur; // Création de la liste de secteurs
            final SecteurDAO secteurDAO = new SecteurDAO(getContext()); // Creation de l'object secteur DAO
            array_secteur = secteurDAO.getAllSecteur(); // Remplissage de la liste des secteurs

            final ArrayAdapter<Secteur> adapter_secteur = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, array_secteur); // Création de l'adapter
            adapter_secteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // Définition du style de l'adapter
            spinner_secteur_int.setAdapter(adapter_secteur); // Affectation de l'adapter au spinner

            if(! intervention.getSecteur().getNom().toString().equals(null)) spinner_secteur_int.setSelection(getIndex(spinner_secteur_int, intervention.getSecteur().toString()));
            // Récupération de l'ID secteur sélectionné
            spinner_secteur_int.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (adapter_secteur.getItem(position).getId() == -1) { // Si l'item est l'item par défaut on fait rien

                    } else id_Secteur = adapter_secteur.getItem(position).getId(); // On récupère l'ID du secteur selectionné
                }

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
            date_debut_contrat.setText(intervention.getDate_debut());
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
            date_fin_contrat.setText(intervention.getDate_fin());
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

                    // -- Modification de l'intervention --

                    // Récupérer un client
                    System.out.println("Id Client: " + id_Client);
                    ClientDAO clientDAO = new ClientDAO(getContext());
                    Client client = clientDAO.retrieveClient(id_Client);

                    //Récupérer une activité
                    System.out.println("Id Activité" + id_Activite);
                    Activite activite = activiteDAO.retrieveActivite(id_Activite);

                    //Récupérer Secteur
                    System.out.println("Id Secteur = " + id_Secteur);
                    Secteur secteur = secteurDAO.retrieveSecteur(id_Secteur);

                    //Récupérer Adherent
                    AdherentDAO adherentDAO = new AdherentDAO(getContext());
                    Adherent adherent = adherentDAO.retrieveAdherent(id_Adherent);

                    // Recupérer les dates
                    System.out.println("Date Debut: " + date_debut_contrat.getText());
                    System.out.println("Date Fin: " + date_fin_contrat.getText());

                    try {
                        if (champsRemplis(intervention)) { // Si tout les champs sont bien remplis on réalise l'insertion
                            intervention.setClient(client);
                            intervention.setActivite(activite);
                            intervention.setSecteur(secteur);
                            intervention.setAdherent(adherent);
                            intervention.setDate_debut(date_debut_contrat.getText().toString());
                            intervention.setDate_fin(date_fin_contrat.getText().toString());

                            // Update
                            InterventionDAO interventionDAO = new InterventionDAO(getContext());
                            interventionDAO.updateIntervention(intervention);


                            if (getShowsDialog())
                                getDialog().cancel();
                            else
                                dismiss();
                            ((MainActivity) getActivity()).changeFragment(new Intervention_fragment_home());
                            Toast.makeText(getActivity(), "Intervention modifié", Toast.LENGTH_SHORT).show();
                            dismiss();
                        }
                        else
                        {
                            Toast.makeText(getActivity(),"Erreur : intervention non modifié",Toast.LENGTH_SHORT).show();

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    try {
                        if(champsRemplis(intervention)){

                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    // dismiss();


                }

            });
        }
        // "Cancel" button
        Button buttonNeg = (Button) dialogView.findViewById(R.id.neg_button);
        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getShowsDialog())
                    getDialog().cancel();
                else
                    dismiss();
                ((MainActivity) getActivity()).changeFragment(new Intervention_fragment_home());
            }
        });

        return dialogView;
    }


    @Override
    public void onResume() {
        super.onResume();
        if (getShowsDialog()) {
            // Set the width of the dialog to the width of the screen in portrait mode
            DisplayMetrics metrics = getActivity().getResources().getDisplayMetrics();
            int dialogWidth = Math.min(metrics.widthPixels, metrics.heightPixels);
            getDialog().getWindow().setLayout(dialogWidth, ViewGroup.LayoutParams.WRAP_CONTENT);
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

    private int getIndex(Spinner spinner, String myString) {
        int index = 0;

        for (int i = 0; i < spinner.getCount(); i++) {
            if (spinner.getItemAtPosition(i).toString().equalsIgnoreCase(myString)) {
                index = i;
                break;
            }
        }
        return index;
    }

    private boolean champsRemplis(Intervention intervention) throws ParseException {
        boolean isSet = true;
        String date_d = date_debut_contrat.getText().toString();
        String date_f = date_fin_contrat.getText().toString();
        String myFormat = "dd/MM/yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        Date date_du_Jour = new Date();
        String du_jour = sdf.format(date_du_Jour);
        date_du_Jour = sdf.parse(du_jour);
        System.out.println("Date du jour :" + date_du_Jour);
        Date date_fin = sdf.parse(date_f);
        Date date_debut = sdf.parse(date_d);
        System.out.println("date " + date_debut.compareTo(date_fin));
        if (intervention.getClient().equals("")) {
            Toast.makeText(getActivity(), "Client manquant", Toast.LENGTH_SHORT).show();
            isSet = false;
        } else if (intervention.getActivite().equals("")) {
            Toast.makeText(getActivity(), "Activite manquant", Toast.LENGTH_SHORT).show();
            isSet = false;
        } else if (intervention.getActivite().equals("")) {
            Toast.makeText(getActivity(), "Secteur manquant", Toast.LENGTH_SHORT).show();
            isSet = false;

        } else if (intervention.getDate_debut().equals("")) {
            Toast.makeText(getActivity(), "Date de début manquante", Toast.LENGTH_SHORT).show();
            isSet = false;

        } else if (intervention.getDate_fin().equals("")) {
            Toast.makeText(getActivity(), "Date de fin manquante", Toast.LENGTH_SHORT).show();
            isSet = false;

    } else if (date_debut.compareTo(date_fin) > 0) {
            Toast.makeText(getActivity(), "Date de début après la date de fin", Toast.LENGTH_SHORT).show();
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
    private void compare_date()
    {


    }
}