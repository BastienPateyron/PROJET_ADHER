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
import java.util.List;

import team_adher.adher.R;
import team_adher.adher.bdd.SecteurDAO;
import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.Secteur;

/**
 * Created by basti on 11/29/2017.
 */

public class Adherent_fragment extends Fragment {
    View myView;
    private ArrayList<Secteur> listeSecteurs = new ArrayList<Secteur>();
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.adherents_layout,container,false);


        Secteur secteur = new Secteur(0, 23, "Creuse"); /* On crée un objet prédéfini */
        /* On peut laisser l'ID à 0 car il est AUTOINCREMENT*/

        SecteurDAO secteurDAO = new SecteurDAO(myView.getContext());

        secteurDAO.insertSecteur(secteur);
        secteur = secteurDAO.retrieveSecteur(1);
        System.out.println("Secteur ajouté: " + secteur.getNom() + "\n - Liste des Secteurs -\n");

        listeSecteurs = secteurDAO.getAllSecteur();
        for(Secteur sect:listeSecteurs){ /* Pour chaque objet de la liste, on crée un objet Secteur nommé sect */
            System.out.println("Id: " + sect.getId() + " -- Nom: " + sect.getNom() + "\n");
        }
        // Construct the data source
        ArrayList<Secteur> customListSecteur = new ArrayList<Secteur>();

        customListSecteur = secteurDAO.getAllSecteur();
        // Create the adapter to convert the array to views
        SecteurAdapter adapter = new SecteurAdapter(getActivity(), customListSecteur);
        // Attach the adapter to a ListView
        ListView listView = (ListView) myView.findViewById(R.id.list_adherent);
        listView.setAdapter(adapter);

//        String[] listeAdherents = new String[] {"Joe","Patrick","Mario","Bobby","Christian Gomet"};
//        ListView listView = (ListView)myView.findViewById(R.id.list_adherent);
//        ArrayAdapter<Secteur> adapter = new ArrayAdapter<Secteur>(myView.getContext(),android.R.layout.simple_list_item_1 ,listeSecteurs);

        listView.setAdapter(adapter); /* On affecte l'adaptateur à la liste view */
        /*Set Custom Title*/
        getActivity().setTitle(R.string.adherent);




        return myView;
    }
}
