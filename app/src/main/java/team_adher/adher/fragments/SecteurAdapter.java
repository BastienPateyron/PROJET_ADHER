package team_adher.adher.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import team_adher.adher.MainActivity;
import team_adher.adher.R;
import team_adher.adher.bdd.SecteurDAO;
import team_adher.adher.classes.Secteur;

/**
 * Created by R on 07/12/2017.
 */

public class SecteurAdapter extends ArrayAdapter<Secteur>{

    private ArrayList<Secteur> secteurs = new ArrayList<>();
    private static class SecteurHolder /* Objet qui contient les éléments affichés à l'écran */
    {
        TextView secteur_nom;
        TextView secteur_number;
        ImageButton delete_item;
    }

    public SecteurAdapter(Context context, ArrayList<Secteur> secteurs)
    {
        super(context,0, secteurs);
        this.secteurs = secteurs;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent)
    {
        //On recupere l'objet Secteur
        final Secteur secteur = getItem(position);
        SecteurHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new SecteurHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext()); /* On désérialise les données du context */
            convertView = inflater.inflate(R.layout.secteur_list_item,parent,false); /* On désérialise le layout */
            viewHolder.secteur_number = (TextView) convertView.findViewById(R.id.secteur_item_number); /* On valorise le nombre */
            viewHolder.secteur_nom = (TextView) convertView.findViewById(R.id.secteur_item_nom); /* On valorise le nom */
            viewHolder.delete_item = (ImageButton) convertView.findViewById(R.id.secteur_item_trash);


            // Cache the viewHolder object inside the fresh view
            convertView.setTag(viewHolder);
        }
        else {
            // View is being recycled, retrieve the viewHolder object from tag
            viewHolder = (SecteurHolder) convertView.getTag();
        }
        // setText avec notre object
        viewHolder.secteur_number.setText(String.valueOf(secteur.getNumero()));
        viewHolder.secteur_nom.setText(secteur.getNom());
        viewHolder.delete_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    builder = new AlertDialog.Builder(getContext(), android.R.style.Theme_Material_Dialog_Alert);
                } else {
                    builder = new AlertDialog.Builder(getContext());
                }
                builder.setTitle("Delete entry")
                        .setMessage("Are you sure you want to delete this entry?")
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                SecteurDAO secteurDAO = new SecteurDAO(getContext());
                                secteurDAO.deleteSecteur(secteur.getId());
                                secteurs.remove(position);
                                notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                // do nothing
                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .show();
            }
        });


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("ROW: ", "ROW PRESSED");
                System.out.println(secteur.getId());
                Bundle bundle = new Bundle();
                bundle.putString("id_secteur",String.valueOf(secteur.getId()));
                Secteur_fragment_modif sfm = new Secteur_fragment_modif();
                sfm.setArguments(bundle);
                ((MainActivity)getContext()).changeFragment(sfm);
            }
        });
        return convertView;
    }

}
