package team_adher.adher.fragments.secteur;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import team_adher.adher.MainActivity;
import team_adher.adher.R;
import team_adher.adher.bdd.SecteurDAO;
import team_adher.adher.classes.Secteur;

public class Secteur_fragment extends Fragment {
    View myView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.general_layout_consultation, container, false);
        getActivity().setTitle("Secteurs");
        //Set button text
        TextView add_part_textview = (TextView) myView.findViewById(R.id.add_part_textview);
        add_part_textview.setText("Ajout Secteur");
        //Event on add zone
        LinearLayout add_part = (LinearLayout) myView.findViewById(R.id.add_part);
        add_part.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Secteur_fragment_ajout sfa = new Secteur_fragment_ajout();
                ((MainActivity) getActivity()).changeFragment(sfa);
            }
        });

        //Load data in listview
        ListView listView = (ListView) myView.findViewById(R.id.list_generique);

        SecteurDAO secteurDAO = new SecteurDAO(myView.getContext());
        ArrayList<Secteur> listSecteur;
        listSecteur = secteurDAO.getAllSecteur();
        SecteurAdapter adapter = new SecteurAdapter(getActivity(), listSecteur);
        listView.setAdapter(adapter);


        return myView;
    }
}
