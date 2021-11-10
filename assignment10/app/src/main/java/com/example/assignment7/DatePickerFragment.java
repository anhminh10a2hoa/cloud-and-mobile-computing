package com.example.assignment7;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Calendar;
import androidx.fragment.app.DialogFragment;
public  class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //Here we use Calendar to set the current time as the default values for the time picker
        final Calendar today = Calendar.getInstance();
        int  day = today.get(Calendar.DAY_OF_MONTH);
        int  month = today.get(Calendar.MONTH);
        int   year = today.get(Calendar.YEAR);
        //Here we create and return a new instance of TimePickerDialog
        return new DatePickerDialog(getActivity(), this, year, month, day);
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        TextView summaryTextView = getActivity().findViewById(R.id.textView11);
        String feedback = dayOfMonth + "." + month + "." + year;
        displayToast(feedback);
        summaryTextView.append(feedback);
    }
    private void displayToast(String message) {
        Toast.makeText(getActivity(), (CharSequence)message,
                Toast.LENGTH_SHORT).show();
    }

}
