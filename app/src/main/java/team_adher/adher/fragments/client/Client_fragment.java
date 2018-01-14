package team_adher.adher.fragments.client;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import team_adher.adher.MainActivity;
import team_adher.adher.R;
import team_adher.adher.bdd.ClientDAO;
import team_adher.adher.classes.Client;
//import team_adher.adher.classes.Client;
//import team_adher.adher.fragments.adherent.Client_fragment_ajout;
//import team_adher.adher.fragments.adherent.Client_fragment_modif;


/**
 * Created by François on 11/29/2017.
 */

public class Client_fragment extends Fragment {
    View myView;
    private ArrayList<String> list_nom = new ArrayList<String>();
    private ArrayList<Client> list_client = new ArrayList<Client>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.general_layout_consultation,container,false);

        /*Set Custom Title*/
        getActivity().setTitle(R.string.client);

        TextView add_part_textview = (TextView) myView.findViewById(R.id.add_part_textview);
        add_part_textview.setText("Ajout Client");


        LinearLayout add_part = (LinearLayout) myView.findViewById(R.id.add_part);
        add_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Client_fragment_ajout afa = new Client_fragment_ajout();
                ((MainActivity)getActivity()).changeFragment(afa);
            }
        });
//
        /* Création d'une liste de raisons sociales */
        ClientDAO clientDAO = new ClientDAO(getContext());
        list_client = clientDAO.getAllClient();

        /* Affichage de la liste */
        ListView listView = (ListView) myView.findViewById(R.id.list_generique);
        final ArrayAdapter<Client> adapter = new ArrayAdapter<Client>(myView.getContext(),android.R.layout.simple_list_item_1, list_client);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ID CLIENT :", String.valueOf(adapter.getItem(position).getId()));

                Bundle bundle = new Bundle();
                bundle.putString("id_client",String.valueOf(adapter.getItem(position).getId()));
                Client_fragment_modif afm = new Client_fragment_modif();
                afm.setArguments(bundle);
                ((MainActivity)getContext()).changeFragment(afm);
            }
        });

        return myView;
    }
}
