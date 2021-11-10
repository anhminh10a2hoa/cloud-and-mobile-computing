package com.example.assignment7;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    int searchRequestCode = 1;
    int allRequestCode = 2;
    int updateRequestCode = 3;
    int settingRequestCode = 4;
    int aboutRequestCode = 5;
    DBAdapter dbAdapter=null;

    private Button addBtn;
    private Button updateBtn;
    private Button searchBtn;
    private Button allBtn;
    private Button submitBtn;
    private EditText titleEt;
    private EditText participantsEt;
    private Button timePickerBtn;
    private TextView timeTv;
    private Button datePickerBtn;
    private TextView dateTv;
    private Button settingBtn;
    private String preferencesName = "my_setting";
    private Button aboutBtn;

    private TextView titleTv;
    private TextView meetingTitleTv;
    private TextView participantsTv;
    private TextView startDateTv;
    private TextView startTimeTv;
    private TextView dateInputTv;
    private TextView timeInputTv;

    DialogFragment newFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dbAdapter = new DBAdapter(getApplicationContext());

        //set background color
        SharedPreferences loadedSharedPrefs = getSharedPreferences(preferencesName, MODE_PRIVATE);

        getWindow().getDecorView().setBackgroundColor(loadedSharedPrefs.getInt("backgroundcolor", getResources().getColor(android.R.color.white)));

        addBtn = findViewById(R.id.button);
        updateBtn = findViewById(R.id.button2);
        searchBtn = findViewById(R.id.button3);
        allBtn = findViewById(R.id.button5);
        submitBtn = findViewById(R.id.button4);
        titleEt = findViewById(R.id.editTextTextPersonName);
        participantsEt = findViewById(R.id.editTextTextPersonName2);
        timePickerBtn = findViewById(R.id.time_button);
        datePickerBtn = findViewById(R.id.date_button);
        timeTv = findViewById(R.id.textView12);
        dateTv = findViewById(R.id.textView11);
        settingBtn = findViewById(R.id.button10);
        titleTv = findViewById(R.id.textView);
        meetingTitleTv= findViewById(R.id.textView2);
        participantsTv= findViewById(R.id.textView3);
        startDateTv= findViewById(R.id.textView4);
        startTimeTv= findViewById(R.id.textView5);
        dateInputTv= findViewById(R.id.button11);
        timeInputTv= findViewById(R.id.button12);
        aboutBtn = findViewById(R.id.button16);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        updateBtn.setOnClickListener(updateClickListener);

        searchBtn.setOnClickListener(searchClickListener);

        settingBtn.setOnClickListener(settingClickListener);

        allBtn.setOnClickListener(allClickListener);

        aboutBtn.setOnClickListener(aboutClickListener);

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

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String title = titleEt.getText().toString();
                String participants = participantsEt.getText().toString();
                String startDate = dateTv.getText().toString();
                String startTime = timeTv.getText().toString();
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
//                    meetingArrayList.add(new Meeting(meetingArrayList.size(), title, participants, startDate + " " + startTime));
                    long id = dbAdapter.addCustomer(randomId(), title, participants, startDate + " " + startTime);
                    Toast.makeText(getBaseContext(),
                            "Id " + id + " was added to the database", Toast.LENGTH_LONG)
                            .show();
                    titleEt.setText("");
                    participantsEt.setText("");
                    dateTv.setText("");
                    timeTv.setText("");
                }
            }
        });

//        set font size font color
        float fontSize = loadedSharedPrefs.getFloat("fontsize", 14.0f);
        titleTv.setTextSize(fontSize);
        meetingTitleTv.setTextSize(fontSize);
        participantsTv.setTextSize(fontSize);
        startDateTv.setTextSize(fontSize);
        startTimeTv.setTextSize(fontSize);

        int fontColor = loadedSharedPrefs.getInt("fontcolor", getResources().getColor(android.R.color.black));
        titleTv.setTextColor(fontColor);
        meetingTitleTv.setTextColor(fontColor);
        participantsTv.setTextColor(fontColor);
        startDateTv.setTextColor(fontColor);
        startTimeTv.setTextColor(fontColor);
    }

    private void startActivity() {
        Intent intent = new Intent(getApplication(), Search.class);
        startActivityForResult(intent, searchRequestCode);
    }

    private void startActivityAll() {
        Intent intent = new Intent(getApplication(), All.class);
        startActivityForResult(intent, allRequestCode);
    }

    private void startActivityUpdate() {
        Intent intent = new Intent(getApplication(), Update.class);
        startActivityForResult(intent, updateRequestCode);
    }

    private void startActivitySetting() {
        Intent intent = new Intent(getApplication(), setting.class);
        startActivityForResult(intent, settingRequestCode);
    }

    private void startActivityAbout() {
        Intent intent = new Intent(getApplication(), about.class);
        startActivityForResult(intent, aboutRequestCode);
    }

    private View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity();
        }
    };

    private View.OnClickListener allClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityAll();
        }
    };

    private View.OnClickListener updateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityUpdate();
        }
    };

    private View.OnClickListener settingClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivitySetting();
        }
    };

    private View.OnClickListener aboutClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityAbout();
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == searchRequestCode) {
            if(resultCode == RESULT_OK) {

            }
        }
    }

    private int randomId() {
        int min = 1;
        int max = 2000;
        int b = (int)(Math.random()*(max-min+1)+min);
        return b;
    }
}