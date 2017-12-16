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
    Button button_accueil_adherent;
    Button button_accueil_client;
    Button button_accueil_intervention;

    private void button_manage() {
//    {
//        button_accueil_adherent = myView.findViewById(R.id.button_accueil_adherent);
//        Fragment af = new Adherent_fragment();
//        MainActivity.button_OnClickFragment(button_accueil_adherent, af,myView.getContext());
//
//        button_accueil_client = myView.findViewById(R.id.button_accueil_client);
//        Client_fragment cf = new Client_fragment();
//        MainActivity.button_OnClickFragment(button_accueil_client, cf,myView.getContext());
//
//        button_accueil_intervention = myView.findViewById(R.id.button_accueil_intervention);
//        Intervention_fragment inf = new Intervention_fragment();
//        MainActivity.button_OnClickFragment(button_accueil_intervention, inf, myView.getContext());
//

    }


}
