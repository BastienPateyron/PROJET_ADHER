package team_adher.adher.fragments;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import team_adher.adher.R;
import team_adher.adher.classes.Secteur;

/**
 * Created by R on 07/12/2017.
 */

public class SecteurAdapter extends ArrayAdapter<Secteur>{

    private static class SecteurHolder /* Objet qui contient les éléments affichés à l'écran */
    {
        TextView secteur_nom;
        TextView secteur_number;
        ImageButton delete_item;
    }

    public SecteurAdapter(Context context, ArrayList<Secteur> secteurs)
    {
        super(context,0, secteurs);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        //On recupere l'objet Secteur
        Secteur secteur = getItem(position);
        SecteurHolder viewHolder;

        if (convertView == null)
        {
            viewHolder = new SecteurHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext()); /* On désérialise les données du context */
            convertView = inflater.inflate(R.layout.secteur_list_item,parent,false); /* On désérialise le layout */
            viewHolder.secteur_number = (TextView) convertView.findViewById(R.id.secteur_item_number); /* On valorise le nombre */
            viewHolder.secteur_nom = (TextView) convertView.findViewById(R.id.secteur_item_nom); /* On valorise le nom */

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

        return convertView;
    }

}
