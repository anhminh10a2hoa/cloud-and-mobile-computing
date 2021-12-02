package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

public class Search extends AppCompatActivity {
    int mainRequestCode = 0;
    Button backButton, searchButton;

    LinearLayout.LayoutParams textLayoutParams, buttonLayoutParams;
    TextView participantsTextViewLabel, starttimeTextViewLabel, meetingTextView, participantTextView;
    EditText participantsEditText, starttimeEditText;
    DBAdapter dbAdapter = null;
    Vector<Object[]> dataRows = new Vector<>();
    Context self = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        textLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        textLayoutParams.leftMargin = 5;
        textLayoutParams.rightMargin = 5;
        textLayoutParams.topMargin = 5;

        buttonLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        ScrollView scrollView = new ScrollView(this);
        scrollView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        dbAdapter = new DBAdapter(getApplicationContext(),MainActivity.dbPathFile.getAbsolutePath() + File.separator, MainActivity.dbName);

        participantsTextViewLabel = new TextView(this);
        participantsTextViewLabel.setText("search by participants");
        participantsTextViewLabel.setTextSize(20);
        linearLayout.addView(participantsTextViewLabel);
        participantsEditText = new EditText(this);
        linearLayout.addView(participantsEditText);

        starttimeTextViewLabel = new TextView(this);
        starttimeTextViewLabel.setText("search by start time");
        starttimeTextViewLabel.setTextSize(20);
        linearLayout.addView(starttimeTextViewLabel);
        starttimeEditText = new EditText(this);
        linearLayout.addView(starttimeEditText);

        searchButton = new Button(this);
        searchButton.setLayoutParams(buttonLayoutParams);
        searchButton.setText(R.string.search);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String participants = participantsEditText.getText().toString();
                String starttime = starttimeEditText.getText().toString();
                dataRows = dbAdapter.searchMeetings(participants, starttime);
                String message;
                for (Object[] dataRow : dataRows) {
                    meetingTextView = new TextView(self);
                    meetingTextView.setLayoutParams(textLayoutParams);

                    message = "";
                    message += "ID: " + dataRow[0] + "\n";
                    message += "Title: " + dataRow[1] + "\n";
                    message += "Start time: " + dataRow[2] + "\n";
                    message += "Participants: \n";
                    meetingTextView.setText(message);
                    meetingTextView.setTextSize(20);
                    linearLayout.addView(meetingTextView);

                    Vector<Object[]> participantsVector = (Vector<Object[]>) dataRow[3];
                    for (Object[] participant : participantsVector) {
                        participantTextView = new TextView(self);
                        participantTextView.setLayoutParams(textLayoutParams);
                        String name = (String) participant[0];
                        participantTextView.setText(name);
                        participantTextView.setTextSize(20);
                        linearLayout.addView(participantTextView);

                        ImageView image = (ImageView) participant[1];
                        linearLayout.addView(image);
                    }
                }
            }
        });
        linearLayout.addView(searchButton);

        backButton = new Button(this);
        backButton.setLayoutParams(buttonLayoutParams);
        backButton.setText(R.string.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        linearLayout.addView(backButton);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        scrollView.addView(linearLayout);
        this.addContentView(scrollView, linearLayoutParams);
    }
}