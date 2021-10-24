package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button addBtn;
    private Button updateBtn;
    private Button searchBtn;
    private Button allBtn;
    private Button submitBtn;
    private TextView titleTv;
    private EditText titleEt;
    private TextView participantsTv;
    private EditText participantsEt;
    private TextView startDateTv;
    private EditText startDateEt;
    private TextView startTimeTv;
    private EditText startTimeEt;
    private TextView durationTv;
    private EditText durationEt;
    private TextView displayDataTv;
    private TextView searchTv;
    private EditText searchEt;
    private Button searchDataBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addBtn = findViewById(R.id.button);
        updateBtn = findViewById(R.id.button2);
        searchBtn = findViewById(R.id.button3);
        allBtn = findViewById(R.id.button5);
        submitBtn = findViewById(R.id.button4);
        titleTv = findViewById(R.id.textView2);
        titleEt = findViewById(R.id.editTextTextPersonName);
        participantsTv = findViewById(R.id.textView3);
        participantsEt = findViewById(R.id.editTextTextPersonName2);
        startDateTv = findViewById(R.id.textView4);
        startDateEt = findViewById(R.id.editTextDate);
        startTimeTv = findViewById(R.id.textView5);
        startTimeEt = findViewById(R.id.editTextTime2);
        durationTv = findViewById(R.id.textView6);
        durationEt = findViewById(R.id.editTextNumber);
        displayDataTv = findViewById(R.id.textView7);
        searchTv = findViewById(R.id.textView8);
        searchEt = findViewById(R.id.editTextTextPersonName3);
        searchDataBtn = findViewById(R.id.button6);

        displayDataTv.setVisibility(TextView.INVISIBLE);
        searchTv.setVisibility(TextView.INVISIBLE);
        searchEt.setVisibility(EditText.INVISIBLE);
        searchDataBtn.setVisibility(Button.INVISIBLE);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleTv.setVisibility(TextView.VISIBLE);
                titleEt.setVisibility(EditText.VISIBLE);
                participantsTv.setVisibility(TextView.VISIBLE);
                participantsEt.setVisibility(EditText.VISIBLE);
                startDateTv.setVisibility(TextView.VISIBLE);
                startDateEt.setVisibility(EditText.VISIBLE);
                startTimeTv.setVisibility(TextView.VISIBLE);
                startTimeEt.setVisibility(EditText.VISIBLE);
                durationTv.setVisibility(TextView.VISIBLE);
                durationEt.setVisibility(EditText.VISIBLE);
                displayDataTv.setVisibility(TextView.INVISIBLE);
                searchTv.setVisibility(TextView.INVISIBLE);
                searchEt.setVisibility(EditText.INVISIBLE);
                searchDataBtn.setVisibility(Button.INVISIBLE);
                submitBtn.setVisibility(Button.VISIBLE);
                submitBtn.setText(R.string.add);
            }
        });

        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleTv.setVisibility(TextView.VISIBLE);
                titleEt.setVisibility(EditText.VISIBLE);
                participantsTv.setVisibility(TextView.VISIBLE);
                participantsEt.setVisibility(EditText.VISIBLE);
                startDateTv.setVisibility(TextView.VISIBLE);
                startDateEt.setVisibility(EditText.VISIBLE);
                startTimeTv.setVisibility(TextView.VISIBLE);
                startTimeEt.setVisibility(EditText.VISIBLE);
                durationTv.setVisibility(TextView.VISIBLE);
                durationEt.setVisibility(EditText.VISIBLE);
                displayDataTv.setVisibility(TextView.INVISIBLE);
                searchTv.setVisibility(TextView.INVISIBLE);
                searchEt.setVisibility(EditText.INVISIBLE);
                searchDataBtn.setVisibility(Button.INVISIBLE);
                submitBtn.setVisibility(Button.VISIBLE);
                submitBtn.setText(R.string.update);
            }
        });

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleTv.setVisibility(TextView.INVISIBLE);
                titleEt.setVisibility(EditText.INVISIBLE);
                participantsTv.setVisibility(TextView.INVISIBLE);
                participantsEt.setVisibility(EditText.INVISIBLE);
                startDateTv.setVisibility(TextView.INVISIBLE);
                startDateEt.setVisibility(EditText.INVISIBLE);
                startTimeTv.setVisibility(TextView.INVISIBLE);
                startTimeEt.setVisibility(EditText.INVISIBLE);
                durationTv.setVisibility(TextView.INVISIBLE);
                durationEt.setVisibility(EditText.INVISIBLE);
                displayDataTv.setVisibility(TextView.VISIBLE);
                submitBtn.setVisibility(Button.INVISIBLE);
                searchTv.setVisibility(TextView.VISIBLE);
                searchEt.setVisibility(EditText.VISIBLE);
                searchDataBtn.setVisibility(Button.VISIBLE);
            }
        });

        allBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleTv.setVisibility(TextView.INVISIBLE);
                titleEt.setVisibility(EditText.INVISIBLE);
                participantsTv.setVisibility(TextView.INVISIBLE);
                participantsEt.setVisibility(EditText.INVISIBLE);
                startDateTv.setVisibility(TextView.INVISIBLE);
                startDateEt.setVisibility(EditText.INVISIBLE);
                startTimeTv.setVisibility(TextView.INVISIBLE);
                startTimeEt.setVisibility(EditText.INVISIBLE);
                durationTv.setVisibility(TextView.INVISIBLE);
                durationEt.setVisibility(EditText.INVISIBLE);
                displayDataTv.setVisibility(TextView.VISIBLE);
                submitBtn.setVisibility(Button.INVISIBLE);
                searchTv.setVisibility(TextView.INVISIBLE);
                searchEt.setVisibility(EditText.INVISIBLE);
                searchDataBtn.setVisibility(Button.INVISIBLE);
            }
        });
    }
}