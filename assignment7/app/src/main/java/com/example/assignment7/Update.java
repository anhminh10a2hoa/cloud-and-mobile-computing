package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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
    private EditText startDateEt;
    private EditText startTimeEt;
    private Boolean isIdExist = false;
    private Button updateBtn;
    private Button backBtn;
    ArrayList<Meeting> meetingArrayList = new ArrayList<Meeting>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        meetingIdEt = findViewById(R.id.editTextTextPersonName7);
        findBtn = findViewById(R.id.button14);
        titleEt = findViewById(R.id.editTextTextPersonName);
        participantsEt = findViewById(R.id.editTextTextPersonName2);
        startDateEt = findViewById(R.id.editTextDate);
        startTimeEt = findViewById(R.id.editTextTime2);
        updateBtn = findViewById(R.id.button4);
        backBtn = findViewById(R.id.button9);
        backBtn.setOnClickListener(backClickListener);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            meetingArrayList = (ArrayList<Meeting>)extras.getSerializable("data");
        }

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
                        startDateEt.setText(date[0]);
                        startTimeEt.setText(date[1]);
                    }
                }
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isIdExist) {
                    String title = titleEt.getText().toString();
                    String participants = participantsEt.getText().toString();
                    String startDate = startDateEt.getText().toString();
                    String startTime = startTimeEt.getText().toString();
                    Integer id = Integer.parseInt(meetingIdEt.getText().toString());
                    if(TextUtils.isEmpty(title)) {
                        titleEt.setBackgroundColor(Color.RED);
                    }
                    if(TextUtils.isEmpty(participants)) {
                        participantsEt.setBackgroundColor(Color.RED);
                    }
                    if(TextUtils.isEmpty(startDate)) {
                        startDateEt.setBackgroundColor(Color.RED);
                    }
                    if(TextUtils.isEmpty(startTime)) {
                        startTimeEt.setBackgroundColor(Color.RED);
                    }
                    if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(participants) && !TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(startTime)) {
                        titleEt.setBackgroundColor(Color.WHITE);
                        participantsEt.setBackgroundColor(Color.WHITE);
                        startDateEt.setBackgroundColor(Color.WHITE);
                        startTimeEt.setBackgroundColor(Color.WHITE);
                        for (Meeting m : meetingArrayList) {
                            if(m.getId().equals(id)) {
                                m.setTitle(title);
                                m.setParticipants(participants);
                                m.setStartTime(startDate + " " + startTime);
                            }
                        }
                        titleEt.setText("");
                        participantsEt.setText("");
                        startDateEt.setText("");
                        startTimeEt.setText("");
                    }
                }
            }
        });
    }

    private void startActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.putExtra("data", meetingArrayList);
        startActivityForResult(intent, mainRequestCode);
    }

    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity();
        }
    };
}