package team_adher.adher.fragments.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import team_adher.adher.MainActivity;
import team_adher.adher.R;
import team_adher.adher.bdd.ClientDAO;
import team_adher.adher.classes.Client;

/**
 * Created by François on 21/12/2017.
 */

public class Client_fragment_ajout extends Fragment {

    View myView;
    boolean validate = true;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.client_layout_add, container, false);

        getActivity().setTitle("Nouveau Client");

        Button button_add_client = (Button) myView.findViewById(R.id.button_add_client);

        button_add_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText value_nom = (EditText) myView.findViewById(R.id.value_nom);
                EditText value_prenom = (EditText) myView.findViewById(R.id.value_prenom);
                EditText value_phone = (EditText) myView.findViewById(R.id.value_phone);
                EditText value_num_rue = (EditText) myView.findViewById(R.id.value_num_rue1);
                EditText value_nom_rue = (EditText) myView.findViewById(R.id.value_nom_rue1);
                EditText value_cp = (EditText) myView.findViewById(R.id.value_cp1);
                EditText value_ville_client = (EditText) myView.findViewById(R.id.value_ville1);


                String nom = value_nom.getText().toString();
                String prenom = value_prenom.getText().toString();
                String phone = value_phone.getText().toString();
                String num_rue = value_num_rue.getText().toString();
                String nom_rue = value_nom_rue.getText().toString();
                String codepostal = value_cp.getText().toString();
                String ville = value_ville_client.getText().toString();

                if ( TextUtils.isEmpty(nom)) {
                    Toast.makeText(getContext(), "Nom manquant", Toast.LENGTH_SHORT).show();
                    validate = false;
                }
                if (TextUtils.isEmpty(prenom)) {
                    Toast.makeText(getContext(), "Prènom manquant", Toast.LENGTH_SHORT).show();
                    validate = false;
                }
                if (TextUtils.isEmpty(phone)) {
                    Toast.makeText(getContext(), "Numero dd téléphone manquant", Toast.LENGTH_SHORT).show();
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
                if (TextUtils.isEmpty(codepostal)) {
                    Toast.makeText(getContext(), "Code postal manquant", Toast.LENGTH_SHORT).show();
                    validate = false;
                }

                if (validate)
                {
                    System.out.println("Insert");
                    ((MainActivity) getActivity()).closekeyboard(getContext(), myView);
                    ClientDAO clientDAO = new ClientDAO(myView.getContext());
                    Client client = new Client
                            (
                                    0,
                                    nom,
                                    prenom,
                                    Integer.parseInt(phone),
                                    Integer.parseInt(num_rue),
                                    nom_rue,
                                    Integer.parseInt(codepostal),
                                    ville
                            );

                   clientDAO.insertClient(client);


                    ((MainActivity) getActivity()).changeFragment(new Client_fragment());
                }
            }
        });

        return myView;
    }








}
