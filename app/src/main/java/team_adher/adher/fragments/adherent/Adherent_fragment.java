package team_adher.adher.fragments.adherent;

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
import team_adher.adher.bdd.AdherentDAO;
import team_adher.adher.classes.Adherent;

/**
 * Created by basti on 11/29/2017.
 */

public class Adherent_fragment extends Fragment {
    View myView;
    private ArrayList<String> list_raison_sociale = new ArrayList<String>();
    private ArrayList<Adherent> list_adherent = new ArrayList<Adherent>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.general_layout_consultation,container,false);
        getActivity().setTitle("Adhérents");

        TextView add_part_textview = (TextView) myView.findViewById(R.id.add_part_textview);
        add_part_textview.setText("Ajout Adherent");

        LinearLayout add_part = (LinearLayout) myView.findViewById(R.id.add_part);
        add_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adherent_fragment_ajout afa = new Adherent_fragment_ajout();
                ((MainActivity)getActivity()).changeFragment(afa);
            }
        });
//
        /* Création d'une liste de raisons sociales */
        AdherentDAO adherentDAO = new AdherentDAO(getContext());
        list_adherent = adherentDAO.getAllAdherent();

        /* Affichage de la liste */
        ListView listView = (ListView) myView.findViewById(R.id.list_generique);
        final ArrayAdapter<Adherent> adapter = new ArrayAdapter<Adherent>(myView.getContext(),android.R.layout.simple_list_item_1, list_adherent);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ID ADHERENT :", String.valueOf(adapter.getItem(position).getId()));

                Bundle bundle = new Bundle();
                bundle.putString("id_adherent",String.valueOf(adapter.getItem(position).getId()));
                Adherent_fragment_modif afm = new Adherent_fragment_modif();
                afm.setArguments(bundle);
                ((MainActivity)getContext()).changeFragment(afm);
            }
        });

        return myView;
    }
}
