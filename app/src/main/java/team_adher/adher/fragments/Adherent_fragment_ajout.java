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
import team_adher.adher.bdd.AdherentDAO;
import team_adher.adher.classes.Adherent;

/**
 * Created by R on 10/12/2017.
 */

public class Adherent_fragment_ajout extends Fragment {

    View myView;
    boolean validate = true;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.adherents_layout_add, container, false);

        getActivity().setTitle("Nouvel Adhérent");

        Button button_add_adherent = (Button) myView.findViewById(R.id.button_add_adherent);

        button_add_adherent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText value_raison_sociale = (EditText) myView.findViewById(R.id.value_raison_sociale);
                EditText value_responsable = (EditText) myView.findViewById(R.id.value_responsable);
                EditText value_num_rue = (EditText) myView.findViewById(R.id.value_numero_rue);
                EditText value_nom_rue = (EditText) myView.findViewById(R.id.value_nom_rue);
                EditText value_ville = (EditText) myView.findViewById(R.id.value_ville);
                EditText value_cp = (EditText) myView.findViewById(R.id.value_cp);

                String raison_sociale = value_raison_sociale.getText().toString();
                String responsable = value_responsable.getText().toString();
                String num_rue = value_num_rue.getText().toString();
                String nom_rue = value_nom_rue.getText().toString();
                String ville = value_ville.getText().toString();
                String cp = value_cp.getText().toString();

                if ( TextUtils.isEmpty(raison_sociale)) {
                    Toast.makeText(getContext(), "Raison sociale manquante", Toast.LENGTH_SHORT).show();
                    validate = false;
                }
                if (TextUtils.isEmpty(responsable)) {
                    Toast.makeText(getContext(), "Nom responsable manquant", Toast.LENGTH_SHORT).show();
                    validate = false;
                }
                if (TextUtils.isEmpty(num_rue)) {
                    Toast.makeText(getContext(), "Numero de rue manquant", Toast.LENGTH_SHORT).show();
                    validate = false;
                }
                if (TextUtils.isEmpty(nom_rue)) {
                    Toast.makeText(getContext(), "Nom de rue manquant", Toast.LENGTH_SHORT).show();
                    validate = false;
                }
                if (TextUtils.isEmpty(ville)) {
                    Toast.makeText(getContext(), "Ville manquante", Toast.LENGTH_SHORT).show();
                    validate = false;
                }
                if (TextUtils.isEmpty(cp)) {
                    Toast.makeText(getContext(), "Code postal manquant", Toast.LENGTH_SHORT).show();
                    validate = false;
                }

                if (validate)
                {
                    System.out.println("Insert");
                    ((MainActivity) getActivity()).closekeyboard(getContext(), myView);
                    AdherentDAO adherentDAO = new AdherentDAO(myView.getContext());
                    Adherent adherent = new Adherent
                            (
                                    0,
                                    raison_sociale,
                                    Integer.parseInt(num_rue),
                                    nom_rue,
                                    Integer.parseInt(cp),
                                    ville,
                                    responsable,
                                    0102030405 //
                            );

                    adherentDAO.insertAdherent(adherent);

                    //Return to the consultation adherent
                    ((MainActivity) getActivity()).changeFragment(new Adherent_fragment());
                }
            }
        });

        return myView;
    }
}
