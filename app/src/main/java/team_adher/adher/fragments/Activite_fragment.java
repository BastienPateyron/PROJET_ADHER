package team_adher.adher.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;

import team_adher.adher.R;
import team_adher.adher.bdd.ActiviteDAO;
import team_adher.adher.classes.Activite;

/**
 * Created by basti on 12/5/2017.
 */

public class Activite_fragment extends Fragment {
    View myView;
    private ArrayList<Activite> listeActivite = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        myView = inflater.inflate(R.layout.activite_layout, container, false);
        getActivity().setTitle(R.string.activite);

        /* On crée un objet prédéfini pour l'ajouter à la base */
        /* On peut laisser l'ID à 0 car il est AUTOINCREMENT*/
        Activite activite = new Activite(0,"Plomberie");
        ActiviteDAO activiteDAO = new ActiviteDAO(myView.getContext());
        activiteDAO.insertActivite(activite);

        /* TODO toast qui annonce l'ajout d'activité */

        /* Liste des Activités */
        ListView listView = (ListView)myView.findViewById(R.id.list_activite);
        listeActivite = activiteDAO.getAllActivite();
//        for(Activite act:listeActivite){/* Pour chaque objet de la liste, on crée un objet Secteur nommé sect */
//            System.out.println("Id: " + act.getId() + " -- Nom: " + act.getNom() + "\n");
//        }

        ArrayAdapter<Activite> adapterActivite = new ArrayAdapter<Activite>(myView.getContext(),android.R.layout.simple_list_item_1,listeActivite);
        listView.setAdapter(adapterActivite);

        return myView;
    }

}
