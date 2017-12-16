package team_adher.adher.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import team_adher.adher.R;


/**
 * Created by basti on 11/29/2017.
 */

public class Intervention_fragment extends Fragment {
    View myView;
    EditText date_fin_contrat;
    final Calendar myCalendar = Calendar.getInstance();



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
//        myView = inflater.inflate(R.layout.intervention_layout,container,false);

        myView = inflater.inflate(R.layout.form_adherent_layout,container,false);
        date_fin_contrat = (EditText) myView.findViewById(R.id.date_fin_contrat);

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        final DatePickerDialog datePicker = new DatePickerDialog(getActivity(), R.style.DatePicker, date, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH));


        date_fin_contrat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePicker.show();
            }
        });

        /*Set Custom Title*/
        getActivity().setTitle(R.string.intervention);

        return myView;
    }

    private void updateLabel() {
        String myFormat = "dd/MM/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.FRANCE);
        date_fin_contrat.setText(sdf.format(myCalendar.getTime()));
    }



}
