package team_adher.adher.fragments.intervention;

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
import team_adher.adher.bdd.InterventionDAO;
import team_adher.adher.classes.Intervention;




/**
 * Created by watson on 04/01/2018.
 */

public class Intervention_fragment_home extends Fragment{
    View myView;
    private int id_intervention;
    private ArrayList<String> list_nom = new ArrayList<String>();
    private ArrayList<Intervention> list_intervention = new ArrayList<Intervention>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.general_layout_consultation,container,false);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            id_intervention = Integer.valueOf(bundle.get("id_intervention").toString());
        }
        System.out.println("Id " + "intervention: " + id_intervention);
        /*Set Custom Title*/
        getActivity().setTitle(R.string.interventions);

        TextView add_part_textview = (TextView) myView.findViewById(R.id.add_part_textview);
        add_part_textview.setText("Ajout Intervention");


        LinearLayout add_part = (LinearLayout) myView.findViewById(R.id.add_part);
        add_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intervention_fragment_ajout ifa = new Intervention_fragment_ajout();
                ((MainActivity)getActivity()).changeFragment(ifa);
            }
        });
//
        /* Création d'une liste d'intervention */
        InterventionDAO interventionDAO = new InterventionDAO(getContext());
        ArrayList<Intervention> list_intervention = interventionDAO.getAllIntervention(getContext(), id_intervention);

        /* Affichage de la liste */
        ListView listView = (ListView) myView.findViewById(R.id.list_generique);
        final ArrayAdapter<Intervention> adapter = new ArrayAdapter<Intervention>(myView.getContext(),android.R.layout.simple_list_item_1, list_intervention);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ID INTERVENTION :", String.valueOf(adapter.getItem(position).getId()));

                Bundle bundle = new Bundle();
                bundle.putString("id_intervention",String.valueOf(adapter.getItem(position).getId()));
                Intervention_fragment_modif ifm = new Intervention_fragment_modif();
                ifm.setArguments(bundle);
                ((MainActivity)getContext()).changeFragment(ifm);
            }
        });

        return myView;
    }



}
