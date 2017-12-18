package team_adher.adher.fragments;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import team_adher.adher.MainActivity;
import team_adher.adher.R;
import team_adher.adher.bdd.ActiviteDAO;
import team_adher.adher.bdd.SecteurDAO;
import team_adher.adher.classes.Activite;
import team_adher.adher.classes.Secteur;

import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

/**
 * Created by R on 17/12/2017.
 */
public class Contrat_service_add_dialog extends DialogFragment {

    private List<String> arrayList_secteur = new ArrayList<>();
    private List<String> arrayList_activite = new ArrayList<>();
    private EditText date_debut_contrat;
    private EditText date_fin_contrat;
    private Calendar myCalendar = Calendar.getInstance();
    private String editext_state; //Use to determine which editext is selected
    private static Context mContext;
    private static FragmentManager fragmentManager;
    private boolean state_for_spinner = false;

    public static Contrat_service_add_dialog newInstance(Context context, FragmentManager fragmentManager1) {
        Contrat_service_add_dialog contrat_service_add_dialog = new Contrat_service_add_dialog();
        mContext = context;
        fragmentManager = fragmentManager1;
        return contrat_service_add_dialog;
    }

    // onCreate --> (onCreateDialog) --> onCreateView --> onActivityCreated
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View dialogView = inflater.inflate(R.layout.adherents_layout_contrat_add_alertdialog, container, false);


        //SECTEUR SPINNER
        Spinner spinner_secteur_cs = (Spinner) dialogView.findViewById(R.id.spinner_secteur_cs);

        SecteurDAO secteurDAO = new SecteurDAO(getContext());
        for (Secteur secteur : secteurDAO.getAllSecteur()) {
            String secteur_item = secteur.getNumero() + " - " + secteur.getNom();
            arrayList_secteur.add(secteur_item);
        }
        ArrayAdapter<String> adapter_secteur = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, arrayList_secteur);
        adapter_secteur.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_secteur_cs.setAdapter(adapter_secteur);

        //ACTIVITE SPINNER

        /*Get data*/
        final Spinner spinner_activite_cs = (Spinner) dialogView.findViewById(R.id.spinner_activite_cs);
        ArrayList<Activite> array_act = new ArrayList<>();
        ActiviteDAO activiteDAO = new ActiviteDAO(getContext());
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

                    /*On parcoure le tableau des id pour verifier si l'activité selectionné n'est pas deja selectionné*/
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
                            Toast.makeText(getActivity(), "Cette activité est deja ajouté", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Nombres d'activités maximales atteintes", Toast.LENGTH_LONG).show();
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

        //BUTTON
        Button buttonPos = (Button) dialogView.findViewById(R.id.pos_button);
        buttonPos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //A FAIRE:
                /*
                * CREER un contrat dans la table contrat service
                * avec l'id du secteur, id de l'adherent (A faire passer depuis le fragment adher_modif...), date_debut + fin (Rajouter un attribut tarif et enveler tarif intervention ?)
                * +
                * Lier activites et contrats avec la table concerner
                * */


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

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        if (editext_state.equals("DEBUT_CONTRAT")) {
            date_debut_contrat.setText(sdf.format(myCalendar.getTime()));

        }
        if (editext_state.equals("FIN_CONTRAT")) {
            date_fin_contrat.setText(sdf.format(myCalendar.getTime()));

        }
    }


}
