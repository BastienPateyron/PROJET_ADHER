package team_adher.adher.fragments;

import android.content.Context;
import android.widget.ArrayAdapter;

import team_adher.adher.classes.Activite;

/**
 * Created by R on 18/12/2017.
 */

public class ActivitéSpinnerCustom extends ArrayAdapter<Activite> {

 public ActivitéSpinnerCustom(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    @Override
    public int getCount() {
        int count = super.getCount();
        return count>0 ? count-1 : count ;
    }


}