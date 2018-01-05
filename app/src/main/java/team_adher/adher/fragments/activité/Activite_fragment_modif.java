package team_adher.adher.fragments.activité;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import team_adher.adher.MainActivity;
import team_adher.adher.R;
import team_adher.adher.bdd.ActiviteDAO;
import team_adher.adher.classes.Activite;
import team_adher.adher.fragments.activité.Activite_fragment;


/**
 */

public class Activite_fragment_modif extends Fragment {
    private View myView;
    private int  id_activite;
    boolean validate = true;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.activite_layout_modif, container, false);
        Bundle bundle = this.getArguments();

        if(bundle != null){
            id_activite = Integer.valueOf(bundle.get("id_activite").toString());
        }
        System.out.println("ID : " + id_activite);
        Button button_add_activite = (Button) myView.findViewById(R.id.button_add_activite);


        final EditText value_activite_nom = (EditText) myView.findViewById(R.id.value_activite_nom);
        
        final ActiviteDAO activiteDAO = new ActiviteDAO(getContext());
        final Activite activite = activiteDAO.retrieveActivite(id_activite);
        
        value_activite_nom.setText(activite.getNom());


        button_add_activite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activite activite_modify = new Activite();

                activite_modify.setId(id_activite);
                activite_modify.setNom(value_activite_nom.getText().toString());

                activiteDAO.updateActivite(activite_modify);
                MainActivity.closekeyboard(getContext(),myView);
                ((MainActivity)getActivity()).changeFragment(new Activite_fragment());            }
        });

        Button button_delete_activite = (Button) myView.findViewById(R.id.button_delete_activite);

        button_delete_activite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activiteDAO.deleteActivite(getContext(), activite.getId());
                ((MainActivity) getActivity()).changeFragment(new Activite_fragment());

                //TODO : Supprimer les contract intervention
            }
        });
        return myView;
    }
}
