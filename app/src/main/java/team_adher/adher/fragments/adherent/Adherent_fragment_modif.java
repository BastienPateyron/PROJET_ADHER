package team_adher.adher.fragments.adherent;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import team_adher.adher.MainActivity;
import team_adher.adher.R;
import team_adher.adher.bdd.AdherentDAO;
import team_adher.adher.bdd.ContratServiceDAO;
import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.ContratService;
import team_adher.adher.fragments.contrat_service.Contrat_service_ajout_fragment;
import team_adher.adher.fragments.contrat_service.Contrat_service_modif_dialog;

/**
 * Created by R on 17/12/2017.
 */

public class Adherent_fragment_modif extends Fragment {

    View myView;
    private int id_adherent;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.adherents_layout_info, container, false);

        Bundle bundle = this.getArguments();

        if (bundle != null) {
            id_adherent = Integer.valueOf(bundle.get("id_adherent").toString());
        }
        System.out.println("Id Adherent: " + id_adherent);

        final EditText value_raison_sociale = (EditText) myView.findViewById(R.id.value_raison_sociale);
        final EditText value_responsable = (EditText) myView.findViewById(R.id.value_responsable);
        final EditText value_num_rue = (EditText) myView.findViewById(R.id.value_numero_rue);
        final EditText value_nom_rue = (EditText) myView.findViewById(R.id.value_nom_rue);
        final EditText value_ville = (EditText) myView.findViewById(R.id.value_ville);
        final EditText value_cp = (EditText) myView.findViewById(R.id.value_cp);
        // TODO ajouter Num Telephone

        final AdherentDAO adherentDAO = new AdherentDAO(getContext());
        final Adherent adherent = adherentDAO.retrieveAdherent(id_adherent);

        value_raison_sociale.setText(adherent.getRaison_sociale());
        value_responsable.setText(adherent.getNom_responsable());
        value_num_rue.setText("" + adherent.getNum_rue());
        value_nom_rue.setText(adherent.getNom_rue());
        value_ville.setText(adherent.getVille());
        value_cp.setText("" + adherent.getCp());


        Button button_remove_adherent = (Button) myView.findViewById(R.id.remove_adherent);

        button_remove_adherent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adherentDAO.deleteAdherent(getContext(), adherent.getId());
                ((MainActivity) getActivity()).changeFragment(new Adherent_fragment());
            }
        });
        Button button_modify_adherent = (Button) myView.findViewById(R.id.modify_adherent);

        button_modify_adherent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Adherent adherent_modify = new Adherent();

                adherent_modify.setId(id_adherent);
                adherent_modify.setRaison_sociale(value_raison_sociale.getText().toString());
                adherent_modify.setNom_responsable(value_responsable.getText().toString());
                adherent_modify.setNum_rue(Integer.valueOf(value_num_rue.getText().toString()));
                adherent_modify.setNom_rue(value_nom_rue.getText().toString());
                adherent_modify.setVille(value_ville.getText().toString());
                adherent_modify.setCp(Integer.valueOf(value_cp.getText().toString()));

                adherentDAO.updateAdherent(adherent_modify);
                MainActivity.closekeyboard(getContext(), myView);
                ((MainActivity) getActivity()).changeFragment(new Adherent_fragment());
            }
        });

        Button button_add_contrat = (Button) myView.findViewById(R.id.button_add_contrat);

        button_add_contrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Bundle arg = new Bundle(); // On crée un nouveau bundle pour passer l'ID adhérent
                arg.putString("id_adherent",String.valueOf(id_adherent)); // On lui donne la valeur de l'id ds id_adherent
                Contrat_service_ajout_fragment dialogFrag = new Contrat_service_ajout_fragment(); // On crée un nouveau dialog
                dialogFrag.setArguments(arg); // On définit les arguments du dialog avec notre bundle
                FragmentManager fm = getFragmentManager();

                dialogFrag.show(fm,"gr"); // On ouvre le dialogue
            }
        });




        // Get all contrats
        ContratServiceDAO contratServiceDAO = new ContratServiceDAO(getContext());
        ArrayList<ContratService> list_contratService = contratServiceDAO.getAllContratServiceOfAdherent(getContext(), id_adherent);

        final ArrayAdapter<ContratService> adapter = new ArrayAdapter<ContratService>(myView.getContext(),android.R.layout.simple_list_item_1, list_contratService);

        ListView listView = myView.findViewById(R.id.list_interventions);
        listView.setAdapter(adapter);

//        TODO Set event on click item
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ID Adhérent :", String.valueOf(id_adherent));
                Log.i("ID Contrat :", String.valueOf(adapter.getItem(position).getId()));

                Bundle bundle = new Bundle();
                bundle.putString("id_adherent",String.valueOf(id_adherent));
                bundle.putString("id_contrat",String.valueOf(adapter.getItem(position).getId()));


                Contrat_service_modif_dialog csfm = new Contrat_service_modif_dialog();
                csfm.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                csfm.show(fm,"gr"); // On ouvre le dialogue
            }
        });
        /*
        *
        * Creer un nouveau dialog avec le meme layout
        *   Open dialog fragment en passant l'id contrat ou obj contrat
        *   Set Edittext avec les valeurs recupd
        *   Changer le text du button pour Modifier
        *
        * Ajouter un button Supprimer un contrat (dans le dialog en bas a gauche ?)
        *
        *
        * TODO Add editext Telephone dans l'ajout et la modif
        *
        * Tjs crash si setString in champ number (activite, adherent, secteur..) + set inputtype=number dans les layouts
        * Suppr activite impossible
        *
        * */


        return myView;
    }


    private void showEditDialog() {

    }



}
