package team_adher.adher.fragments;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;

import team_adher.adher.MainActivity;
import team_adher.adher.R;
import team_adher.adher.classes.Secteur;

/**
 * Created by R on 10/12/2017.
 */

public class Home_Fragment extends Fragment{

    View myView;
    Button button_ajout_adherent;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.home_layout, container, false);

        button_manage();
        return myView;
    }
    /* FONCTION DE REMI */
    Button button_accueil_contrats_adherents;
    Button button_accueil_appels;
    Button button_accueil_interventions;

    private void button_manage() {

        button_accueil_contrats_adherents = myView.findViewById(R.id.button_accueil_contrats_adherents);
        button_accueil_contrats_adherents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adherent_fragment af = new Adherent_fragment();
                ((MainActivity) getActivity()).changeFragment(af);
            }
        });




        button_accueil_appels = myView.findViewById(R.id.button_accueil_appels);
        button_accueil_appels.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create fragment appels
            }
        });


        button_accueil_interventions = myView.findViewById(R.id.button_accueil_interventions);
        button_accueil_interventions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Create Interventions

            }
        });

    }


}
