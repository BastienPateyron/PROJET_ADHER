package team_adher.adher.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team_adher.adher.R;

/**
 * Created by R on 17/12/2017.
 */

public class Adherent_fragment_modif extends Fragment {

    View myView;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        myView = inflater.inflate(R.layout.info_adherent_layout, container, false);




        return myView;
    }

}
