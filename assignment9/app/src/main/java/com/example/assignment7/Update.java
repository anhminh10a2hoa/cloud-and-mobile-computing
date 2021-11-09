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

import java.util.ArrayList;

public class Update extends AppCompatActivity {
    int mainRequestCode = 0;

    private EditText meetingIdEt;
    private Button findBtn;
    private EditText titleEt;
    private EditText participantsEt;
    private Button timePickerBtn;
    private TextView timeTv;
    private Button datePickerBtn;
    private TextView dateTv;
    DialogFragment newFragment;
    private TextView errorTv;
    private Boolean isIdExist = false;
    private Button updateBtn;
    private Button backBtn;
    ArrayList<Meeting> meetingArrayList = new ArrayList<Meeting>();

    private TextView titleTv;
    private TextView meetingId;
    private TextView meetingTitleTv;
    private TextView participantsTv;
    private TextView startDateTv;
    private TextView startTimeTv;

    String backgroundColor = "";
    String fontColor = "";
    String fontSize = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        meetingIdEt = findViewById(R.id.editTextTextPersonName7);
        findBtn = findViewById(R.id.button14);
        titleEt = findViewById(R.id.editTextTextPersonName);
        participantsEt = findViewById(R.id.editTextTextPersonName2);
        timePickerBtn = findViewById(R.id.time_button);
        datePickerBtn = findViewById(R.id.date_button);
        timeTv = findViewById(R.id.textView12);
        dateTv = findViewById(R.id.textView11);
        errorTv = findViewById(R.id.textView10);
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
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            meetingArrayList = (ArrayList<Meeting>)extras.getSerializable("data");
            backgroundColor = extras.getString("backgroundcolor");
            fontColor = extras.getString("fontcolor");
            fontSize = extras.getString("fontsize");
            if(fontSize != "") {
                float fontSizeZ = Float.parseFloat(fontSize);
                titleTv.setTextSize(fontSizeZ);
                meetingId.setTextSize(fontSizeZ);
                meetingTitleTv.setTextSize(fontSizeZ);
                participantsTv.setTextSize(fontSizeZ);
                startDateTv.setTextSize(fontSizeZ);
                startTimeTv.setTextSize(fontSizeZ);
            }
            if(fontColor != "") {
                int fontColorZ = Integer.parseInt(fontColor);
                titleTv.setTextColor(fontColorZ);
                meetingId.setTextColor(fontColorZ);
                meetingTitleTv.setTextColor(fontColorZ);
                participantsTv.setTextColor(fontColorZ);
                startDateTv.setTextColor(fontColorZ);
                startTimeTv.setTextColor(fontColorZ);
            }
            if(backgroundColor != "") {
                int backgroundColorZ = Integer.parseInt(backgroundColor);
                getWindow().getDecorView().setBackgroundColor(backgroundColorZ);
            }
        }

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

        findBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer id = Integer.parseInt(meetingIdEt.getText().toString());
                isIdExist = false;
                for (Meeting m : meetingArrayList) {
                    if(m.getId().equals(id)) {
                        isIdExist = true;
                        String[] date;
                        date = m.getStartTime().split(" ");
                        titleEt.setText(m.getTitle());
                        participantsEt.setText(m.getParticipants());
                        dateTv.setText(date[0]);
                        timeTv.setText(date[1]);
                        errorTv.setText("");
                    }
                } if(isIdExist == false) {
                    errorTv.setText("id " + id +" does not exist!");
                    errorTv.setTextColor(Color.RED);
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isIdExist) {
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
                        for (Meeting m : meetingArrayList) {
                            if(m.getId().equals(id)) {
                                m.setTitle(title);
                                m.setParticipants(participants);
                                m.setStartTime(startDate + " " + startTime);
                            }
                        }
                        titleEt.setText("");
                        participantsEt.setText("");
                        dateTv.setText("");
                        timeTv.setText("");
                    }
                }
            }
        });
    }

    private void startActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.putExtra("data", meetingArrayList);
        if(!fontSize.isEmpty()) {
            intent.putExtra("fontsize", fontSize);
        }
        if(!fontColor.isEmpty()) {
            intent.putExtra("fontcolor", fontColor);
        }
        if(!backgroundColor.isEmpty()) {
            intent.putExtra("backgroundcolor", backgroundColor);
        }
        startActivityForResult(intent, mainRequestCode);
    }

    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity();
        }
    };

}