package team_adher.adher.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import team_adher.adher.MainActivity;
import team_adher.adher.R;
import team_adher.adher.bdd.ActiviteDAO;
import team_adher.adher.bdd.AdherentDAO;
import team_adher.adher.classes.Activite;
import team_adher.adher.classes.Adherent;

/**
 * Created by R on 17/12/2017.
 */

public class Activite_fragment_ajout extends Fragment {

    View myView;
    boolean validate = true;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.activite_layout_add, container, false);

        getActivity().setTitle("Nouvelle Activité");

        Button button_add_adherent = (Button) myView.findViewById(R.id.button_add_adherent);

        button_add_adherent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText value_nom_activite = (EditText) myView.findViewById(R.id.value_activite_nom);


                String nom_activite = value_nom_activite.getText().toString();


                if ( TextUtils.isEmpty(nom_activite)) {
                    Toast.makeText(getContext(), "Nom activité manquant", Toast.LENGTH_SHORT).show();
                    validate = false;
                }

                if (validate)
                {
                    System.out.println("Insert");
                    ((MainActivity) getActivity()).closekeyboard(getContext(), myView);
                    ActiviteDAO activiteDAO = new ActiviteDAO(myView.getContext());
                    Activite activite = new Activite
                            (
                                    0,
                                    nom_activite
                            );

                    activiteDAO.insertActivite(activite);

                    //Return to the consultation adherent
                    ((MainActivity) getActivity()).changeFragment(new Activite_fragment());
                }
            }
        });

        return myView;
    }
}