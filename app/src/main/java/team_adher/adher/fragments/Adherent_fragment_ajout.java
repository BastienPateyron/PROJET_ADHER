package team_adher.adher.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import team_adher.adher.R;

/**
 * Created by R on 10/12/2017.
 */

public class Adherent_fragment_ajout extends Fragment {

    View myView;
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.form_adherent_layout_alternative, container, false);

        getActivity().setTitle("Nouvel Adhérent");

        return myView;

    }
}
