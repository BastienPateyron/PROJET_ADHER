package team_adher.adher.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.basti.test_menu.R;
import com.team_adher.basti.ADHER.bdd.SecteurDAO;
import com.team_adher.basti.ADHER.classes.Secteur;

import java.util.ArrayList;

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

        /*Set Custom Title*/
        getActivity().setTitle(R.string.adherent);


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

        return myView;
    }
}
