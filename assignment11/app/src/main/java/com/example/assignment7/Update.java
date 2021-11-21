package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class Update extends AppCompatActivity {
    int mainRequestCode = 0;

    private EditText meetingIdEt;
    private EditText titleEt;
    private EditText participantsEt;
    private Button timePickerBtn;
    private TextView timeTv;
    private Button datePickerBtn;
    private TextView dateTv;
    DialogFragment newFragment;
    private Button updateBtn;
    private Button backBtn;
    private String preferencesName = "my_setting";

    private TextView titleTv;
    private TextView meetingId;
    private TextView meetingTitleTv;
    private TextView participantsTv;
    private TextView startDateTv;
    private TextView startTimeTv;
    DBAdapter dbAdapter=null;
    String dirName;
    //Here we define the directory path for the database on SD card
    File dbPathFile;
    //Here we define the name of the database and the table
    String dbName;
    String tableName;
    File dbAbsolutePathFileName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        dbName=getString(R.string.db_name);
        tableName=getString(R.string.table_name);
        dirName=getString(R.string.dir_name_database);
        dbPathFile = getApplicationContext().getExternalFilesDir(dirName);
        if(dbPathFile == null) {
            displayMessage(getString(R.string.not_found));
            return;
        }
        dbAbsolutePathFileName = new File(dbPathFile.getAbsolutePath() + File.separator + dbName);
        dbAdapter = new DBAdapter(getApplicationContext(), this.dbPathFile.getAbsolutePath() + File.separator, dbName, tableName );

        SharedPreferences loadedSharedPrefs = getSharedPreferences(preferencesName, MODE_PRIVATE);

        getWindow().getDecorView().setBackgroundColor(loadedSharedPrefs.getInt("backgroundcolor", getResources().getColor(android.R.color.white)));

        meetingIdEt = findViewById(R.id.editTextTextPersonName7);
        titleEt = findViewById(R.id.editTextTextPersonName);
        participantsEt = findViewById(R.id.editTextTextPersonName2);
        timePickerBtn = findViewById(R.id.time_button);
        datePickerBtn = findViewById(R.id.date_button);
        timeTv = findViewById(R.id.textView12);
        dateTv = findViewById(R.id.textView11);
        updateBtn = findViewById(R.id.button4);
        backBtn = findViewById(R.id.button9);
        backBtn.setOnClickListener(backClickListener);
        titleTv = findViewById(R.id.textView15);
        meetingId = findViewById(R.id.textView16);
        meetingTitleTv= findViewById(R.id.textView2);
        participantsTv= findViewById(R.id.textView3);
        startDateTv= findViewById(R.id.textView4);
        startTimeTv= findViewById(R.id.textView5);

        //        set font size font color
        float fontSize = loadedSharedPrefs.getFloat("fontsize", 14.0f);
        titleTv.setTextSize(fontSize);
        meetingId.setTextSize(fontSize);
        meetingTitleTv.setTextSize(fontSize);
        participantsTv.setTextSize(fontSize);
        startDateTv.setTextSize(fontSize);
        startTimeTv.setTextSize(fontSize);

        int fontColor = loadedSharedPrefs.getInt("fontcolor", getResources().getColor(android.R.color.black));
        titleTv.setTextColor(fontColor);
        meetingId.setTextColor(fontColor);
        meetingTitleTv.setTextColor(fontColor);
        participantsTv.setTextColor(fontColor);
        startDateTv.setTextColor(fontColor);
        startTimeTv.setTextColor(fontColor);

        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = titleEt.getText().toString();
                String participants = participantsEt.getText().toString();
                String startDate = dateTv.getText().toString();
                String startTime = timeTv.getText().toString();
                Integer id = Integer.parseInt(meetingIdEt.getText().toString());
                if(TextUtils.isEmpty(title)) {
                    titleEt.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(participants)) {
                    participantsEt.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(startDate)) {
                    dateTv.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(startTime)) {
                    timeTv.setBackgroundColor(Color.RED);
                }
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(participants) && !TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(startTime)) {
                    titleEt.setBackgroundColor(Color.WHITE);
                    participantsEt.setBackgroundColor(Color.WHITE);
                    dateTv.setBackgroundColor(Color.WHITE);
                    timeTv.setBackgroundColor(Color.WHITE);
                    dbAdapter.updateCustomer(id, title, participants, startDate + " " + startTime);
                    Toast.makeText(getBaseContext(),
                            "Id " + id + " was updated", Toast.LENGTH_LONG)
                            .show();
                    meetingIdEt.setText("");
                    titleEt.setText("");
                    participantsEt.setText("");
                    dateTv.setText("");
                    timeTv.setText("");
                }
            }
        });
    }

    private void startActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        startActivityForResult(intent, mainRequestCode);
    }

    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity();
        }
    };
    private void displayMessage(String text) {
        Toast.makeText(getApplicationContext(), text , Toast.LENGTH_LONG).show();
    }
}