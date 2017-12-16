package team_adher.adher.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import team_adher.adher.MainActivity;
import team_adher.adher.R;
import team_adher.adher.bdd.SecteurDAO;
import team_adher.adher.classes.Secteur;

/**
 * Created by R on 15/12/2017.
 */

public class Secteur_fragment_ajout extends Fragment {

    View myView;
    boolean validate = true;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.secteur_ajout_layout, container, false);

        Button button_add_secteur = (Button) myView.findViewById(R.id.button_add_secteur);

        button_add_secteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText value_numero_secteur = (EditText) myView.findViewById(R.id.value_numero_secteur);
                EditText value_nom_secteur = (EditText) myView.findViewById(R.id.value_nom_secteur);
                String numero_secteur = value_numero_secteur.getText().toString();
                String nom_secteur = value_nom_secteur.getText().toString();

                if ( TextUtils.isEmpty(numero_secteur)) {
                    Toast.makeText(getContext(), "Numero secteur manquant", Toast.LENGTH_SHORT).show();
                    validate = false;
                }
                if (TextUtils.isEmpty(nom_secteur)) {
                    Toast.makeText(getContext(), "Nom secteur manquant", Toast.LENGTH_SHORT).show();
                    validate = false;
                }

                if (validate)
                {
                    System.out.println("Insert");
                    ((MainActivity) getActivity()).closekeyboard(getContext(), myView);
                    SecteurDAO secteurDAO = new SecteurDAO(myView.getContext());
                    Secteur secteur = new Secteur(0, Integer.parseInt(numero_secteur), nom_secteur);
                    secteurDAO.insertSecteur(secteur);

                    //Return to the consultation secteur
                    ((MainActivity) getActivity()).changeFragment(new Secteur_fragment());
                }


            }
        });

        return myView;
    }
}
