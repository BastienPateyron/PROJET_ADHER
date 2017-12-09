package team_adher.adher.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

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

    /* Debut test pour changer la couleur */
    public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
        TextView textView;
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the date picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create DatePickerDialog new instance
            DatePickerDialog datePicker = new DatePickerDialog(getActivity(), this, year, month, day);

            // return the DatePickerDialog
            return datePicker;
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // Set the date to textview
            // Month value start with zero, we have to add by one
            textView.setText(dayOfMonth + "/" + (monthOfYear+1) + "/" + year);
        }
    } /* TEST Pour changer la couleur */

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
                // TODO Auto-generated method stub
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
