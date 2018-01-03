package team_adher.adher.fragments.client;

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
import team_adher.adher.bdd.ClientDAO;
import team_adher.adher.classes.Client;
/**
 * Created by watson on 21/12/2017.
 */

public class Client_fragment_modif extends Fragment {

    View myView;
    private int id_client;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.client_layout_info, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            id_client = Integer.valueOf(bundle.get("id_client").toString());
        }
        System.out.println("Id Client: " + id_client);

        final EditText value_nom = (EditText) myView.findViewById(R.id.value_nom);
        final EditText value_prenom = (EditText) myView.findViewById(R.id.value_prenom);
        final EditText value_phone = (EditText) myView.findViewById(R.id.value_phone);
        final EditText value_num_rue = (EditText) myView.findViewById(R.id.value_num_rue1);
        final EditText value_nom_rue = (EditText) myView.findViewById(R.id.value_nom_rue1);
        final EditText value_cp = (EditText) myView.findViewById(R.id.value_cp1);
        final EditText value_ville = (EditText) myView.findViewById(R.id.value_ville1);



        final ClientDAO clientDAO = new ClientDAO(getContext());
        final Client client = clientDAO.retrieveClient(id_client);

        value_nom.setText(client.getNom());
        value_prenom.setText(client.getPrenom());
        value_phone.setText("" + client.getPhone());
        value_num_rue.setText("" + client.getNum_rue());
        value_nom_rue.setText(client.getNom_rue());
        value_cp.setText("" + client.getCp());
        value_ville.setText(client.getVille());



        Button button_remove_client = (Button) myView.findViewById(R.id.remove_client);

        button_remove_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clientDAO.deleteClient(getContext(), client.getId());
                ((MainActivity) getActivity()).changeFragment(new Client_fragment());
            }
        });
        Button button_modify_client = (Button) myView.findViewById(R.id.modify_client);

        button_modify_client.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client client_modify = new Client ();


                client_modify.setId(id_client);
                client_modify.setNom(value_nom.getText().toString());
                client_modify.setPrenom(value_prenom.getText().toString());
                client_modify.setPhone(Integer.valueOf(value_phone.getText().toString()));
                client_modify.setNum_rue(Integer.valueOf(value_num_rue.getText().toString()));
                client_modify.setNom_rue(value_nom_rue.getText().toString());
                client_modify.setCp(Integer.valueOf(value_cp.getText().toString()));
                client_modify.setVille(value_ville.getText().toString());

                clientDAO.updateClient(client_modify);
                MainActivity.closekeyboard(getContext(), myView);
                ((MainActivity) getActivity()).changeFragment(new Client_fragment());
            }
        });

        return myView;
    }


    private void showEditDialog() {

    }



}
