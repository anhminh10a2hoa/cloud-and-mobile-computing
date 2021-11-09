package com.example.assignment7;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;
import androidx.fragment.app.DialogFragment;
public  class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        //Here we use Calendar to set the current time as the default values for the time picker
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);
        //Here we create and return a new instance of TimePickerDialog
        return new TimePickerDialog(getActivity(),this, hour, minute,
                DateFormat.is24HourFormat(getActivity()));
    }
    //Here we define onTimeSet() callback method
    public void onTimeSet(TimePicker view, int hourOfDay, int minute){
        int  selectedHour = hourOfDay;
        int   selectedMinute = minute;
        String feedback = selectedHour + ":"
                + selectedMinute;
        Toast.makeText(getActivity(), feedback, Toast.LENGTH_SHORT)
                .show();
        TextView selectedTimeTextView =  getActivity().findViewById(R.id.textView12);
        selectedTimeTextView.setText(feedback);
    }
}