package team_adher.adher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


import java.util.ArrayList;

import team_adher.adher.R;
import team_adher.adher.bdd.ActiviteDAO;
import team_adher.adher.classes.Activite;

/**
 * Created by basti on 12/5/2017.
 */

public class Activite_fragment extends Fragment{
    View myView;
    private ArrayList<Activite> listeActivite = new ArrayList<>();
    private ArrayAdapter<Activite> adapterActivite;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        myView = inflater.inflate(R.layout.activite_layout, container, false);
        listView = (ListView) LayoutInflater.from(myView.getContext()).inflate(R.layout.activite_layout,null);

        /* On crée un objet prédéfini pour l'ajouter à la base */
        /* On peut laisser l'ID à 0 car il est AUTOINCREMENT*/
        Activite activite = new Activite(0,"Plomberie");
        ActiviteDAO activiteDAO = new ActiviteDAO(myView.getContext());
        activiteDAO.insertActivite(activite);

        /* TODO toast qui annonce l'ajout d'activité */

        /* Liste des Activités */
        listeActivite = activiteDAO.getAllActivite();
        for(Activite act:listeActivite){/* Pour chaque objet de la liste, on crée un objet Secteur nommé sect */
            System.out.println("Id: " + act.getId() + " -- Nom: " + act.getNom() + "\n");
        }
       // adapterActivite = new ArrayAdapter<Activite>(LayoutInflater.from(myView.getContext()).inflate(R.layout.support_simple_spinner_dropdown_item,null),listeActivite);

        return myView;
    }

}
