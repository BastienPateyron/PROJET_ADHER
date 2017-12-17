package team_adher.adher.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import team_adher.adher.MainActivity;
import team_adher.adher.MyDialogFragment;
import team_adher.adher.R;
import team_adher.adher.bdd.AdherentDAO;
import team_adher.adher.bdd.SecteurDAO;
import team_adher.adher.classes.Adherent;
import team_adher.adher.classes.Secteur;

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
        System.out.println(id_adherent);

        final EditText value_raison_sociale = (EditText) myView.findViewById(R.id.value_raison_sociale);
        final EditText value_responsable = (EditText) myView.findViewById(R.id.value_responsable);
        final EditText value_num_rue = (EditText) myView.findViewById(R.id.value_numero_rue);
        final EditText value_nom_rue = (EditText) myView.findViewById(R.id.value_nom_rue);
        final EditText value_ville = (EditText) myView.findViewById(R.id.value_ville);
        final EditText value_cp = (EditText) myView.findViewById(R.id.value_cp);

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
                adherentDAO.deleteAdherent(adherent.getId());
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
                Contrat_service_add_dialog dialogFrag = new Contrat_service_add_dialog();
                FragmentManager fm = getFragmentManager();
                dialogFrag.show(fm,"gr");
            }
        });



        return myView;
    }


    private void showEditDialog() {

    }



}
