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
import team_adher.adher.bdd.SecteurDAO;
import team_adher.adher.classes.Secteur;

/**
 * Created by R on 16/12/2017.
 */

public class Secteur_fragment_modif extends Fragment {
    private View myView;
    private int  id_secteur;
    boolean validate = true;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.secteur_ajout_layout, container, false);
        Bundle bundle = this.getArguments();

        if(bundle != null){
           id_secteur = Integer.valueOf(bundle.get("id_secteur").toString());
        }
        System.out.println("ID : " + id_secteur);
        Button button_add_secteur = (Button) myView.findViewById(R.id.button_add_secteur);
        button_add_secteur.setText("Modifier");

        final EditText value_numero_secteur = (EditText) myView.findViewById(R.id.value_numero_secteur);
        final EditText value_nom_secteur = (EditText) myView.findViewById(R.id.value_nom_secteur);

        final SecteurDAO secteurDAO = new SecteurDAO(getContext());
        Secteur secteur_rt = new Secteur();
        secteur_rt = secteurDAO.retrieveSecteur(id_secteur);
        value_numero_secteur.setText("" +secteur_rt.getNumero());
        value_nom_secteur.setText(""+secteur_rt.getNom());

        button_add_secteur.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Secteur secteur_up = new Secteur();

                secteur_up.setId(id_secteur);
                secteur_up.setNumero(Integer.valueOf(value_numero_secteur.getText().toString()));
                secteur_up.setNom(value_nom_secteur.getText().toString());
                secteurDAO.updateSecteur(secteur_up);
                MainActivity.closekeyboard(getContext(),myView);
                ((MainActivity)getActivity()).changeFragment(new Secteur_fragment());            }
        });

        return myView;
    }
}
