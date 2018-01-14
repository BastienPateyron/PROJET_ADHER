package team_adher.adher.fragments.adherent;

import android.content.DialogInterface;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

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
    int i;
    String myFormat = "dd/MM/yyyy"; //In which you need put here
    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
    Date date_du_jour = new Date();
    private int id_contrat_service;
    private ContratService contrat_Service;

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
        final EditText value_tel = (EditText) myView.findViewById(R.id.value_telephone);

        final AdherentDAO adherentDAO = new AdherentDAO(getContext());
        final Adherent adherent = adherentDAO.retrieveAdherent(id_adherent);

        value_raison_sociale.setText(adherent.getRaison_sociale());
        value_responsable.setText(adherent.getNom_responsable());
        value_num_rue.setText("" + adherent.getNum_rue());
        value_nom_rue.setText(adherent.getNom_rue());
        value_ville.setText(adherent.getVille());
        value_cp.setText("" + adherent.getCp());
        value_tel.setText("" + adherent.getTelephone());


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
                Adherent adherent_modify = new Adherent ();

                adherent_modify.setId(id_adherent);
                adherent_modify.setRaison_sociale(value_raison_sociale.getText().toString());
                adherent_modify.setNom_responsable(value_responsable.getText().toString());
                adherent_modify.setNum_rue(Integer.valueOf(value_num_rue.getText().toString()));
                adherent_modify.setNom_rue(value_nom_rue.getText().toString());
                adherent_modify.setVille(value_ville.getText().toString());
                adherent_modify.setCp(value_cp.getText().toString());
                adherent_modify.setTelephone(value_tel.getText().toString());

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
                dialogFrag.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        System.out.println("Ajout réussi ? On update la liste");
                        updateListe(); // On met à jour la liste à la fermeture du dialog
                    }
                });

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

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ID Adhérent :", String.valueOf(id_adherent));
                Log.i("ID Contrat :", String.valueOf(adapter.getItem(position).getId()));

                Bundle bundle = new Bundle();
                bundle.putString("id_adherent",String.valueOf(id_adherent));
                bundle.putString("id_contrat",String.valueOf(adapter.getItem(position).getId()));


                Contrat_service_modif_dialog csfm = new Contrat_service_modif_dialog();
                csfm.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        System.out.println("On update la liste");
                        //updateListe();
                    }
                });

                csfm.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                csfm.show(fm,"gr"); // On ouvre le dialogue

            }
        });
        /*

        */

        for (i = 0; i < list_contratService.size(); i++) {
            // id_Intervention = list_intervention;
            id_contrat_service = adapter.getItem(i).getId();
            contrat_Service = contratServiceDAO.retrieveContratService(id_contrat_service, getContext());


            String date_f = contrat_Service.getDate_fin();
            String du_jour = sdf.format(date_du_jour);

            try {
                date_du_jour = sdf.parse(du_jour);
                Date date_fin = sdf.parse(date_f);
                System.out.println("Date_fin : " + date_fin);
                if (date_du_jour.after(date_fin)) {

                    contratServiceDAO.deleteContratService(getContext(),id_contrat_service);
                    updateListe();
                } else {
                    System.out.println("La date est à jour");
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return myView;
    }

    private void showEditDialog() {

    }

    private void updateListe() {
        ContratServiceDAO contratServiceDAO = new ContratServiceDAO(getContext());
        ArrayList<ContratService> list_contratService = contratServiceDAO.getAllContratServiceOfAdherent(getContext(), id_adherent);


        final ArrayAdapter<ContratService> adapter = new ArrayAdapter<>(myView.getContext(),android.R.layout.simple_list_item_1, list_contratService);

        // On redéfinit le listener avec la nouvelle liste
        ListView listView = myView.findViewById(R.id.list_interventions);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("ID Adhérent :", String.valueOf(id_adherent));
                Log.i("ID Contrat :", String.valueOf(adapter.getItem(position).getId()));

                Bundle bundle = new Bundle();
                bundle.putString("id_adherent",String.valueOf(id_adherent));
                bundle.putString("id_contrat",String.valueOf(adapter.getItem(position).getId()));


                Contrat_service_modif_dialog csfm = new Contrat_service_modif_dialog();
                csfm.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        System.out.println("On update la liste");
                        updateListe();
                    }
                });

                csfm.setArguments(bundle);
                FragmentManager fm = getFragmentManager();
                csfm.show(fm,"gr"); // On ouvre le dialogue

            }
        });
        listView.setAdapter(adapter);
    }
}
