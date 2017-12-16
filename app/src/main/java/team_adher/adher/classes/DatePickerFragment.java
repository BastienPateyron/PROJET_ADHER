package team_adher.adher.classes;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.DatePicker;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by basti on 12/9/2017.
 */

/* Debut test pour changer la couleur */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{
    TextView textView;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        // Use the current date as the default date in the date picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        System.out.println("Year : " + year);
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