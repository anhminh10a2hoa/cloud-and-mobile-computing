package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    int mainRequestCode = 0;
    private Button searchButton;
    private Button backButton;
    private TextView dataTv;
    private EditText searchTimeEt;
    private EditText searchParticipantsEt;
    private TextView searchTimeTv;
    private TextView searchParticipantsTv;
    String backgroundColor = "";
    String fontColor = "";
    String fontSize = "";
    ArrayList<Meeting> data = new ArrayList<Meeting>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        searchButton = findViewById(R.id.button6);
        backButton = findViewById(R.id.button7);
        searchTimeEt = findViewById(R.id.editTextTextPersonName3);
        searchParticipantsEt = findViewById(R.id.editTextTextPersonName4);
        searchTimeTv = findViewById(R.id.textView7);
        searchParticipantsTv = findViewById(R.id.textView8);
        dataTv = findViewById(R.id.textView9);
        backButton.setOnClickListener(backClickListener);

        Bundle extras = getIntent().getExtras();
        if(extras.size() != 0) {
            data = (ArrayList<Meeting>)extras.getSerializable("data");
            String backgroundColorE = extras.getString("backgroundcolor");
            String fontColorE = extras.getString("fontcolor");
            String fontSizeE = extras.getString("fontsize");
            if(!fontSizeE.isEmpty()) {
                fontSize = fontSizeE;
                float fontSizeZ = Float.parseFloat(fontSizeE);
                searchTimeTv.setTextSize(fontSizeZ);
                searchParticipantsTv.setTextSize(fontSizeZ);
                dataTv.setTextSize(fontSizeZ);
            }
            if(!fontColorE.isEmpty()) {
                fontColor = fontColorE;
                int fontColorZ = Integer.parseInt(fontColorE);
                searchTimeTv.setTextColor(fontColorZ);
                searchParticipantsTv.setTextColor(fontColorZ);
                dataTv.setTextColor(fontColorZ);
            }
            if(!backgroundColorE.isEmpty()) {
                backgroundColor = backgroundColorE;
                int backgroundColorZ = Integer.parseInt(backgroundColorE);
                getWindow().getDecorView().setBackgroundColor(backgroundColorZ);
            }
        }

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTime = searchTimeEt.getText().toString();
                String searchParticipants = searchParticipantsEt.getText().toString();
                if(!TextUtils.isEmpty(searchTime) && TextUtils.isEmpty(searchParticipants)) {
                    String res = "";
                    for (Meeting m : data) {
                        if(m.getStartTime().contains(searchTime)) {
                            res += m.toString();
                        }
                    }
                    dataTv.setText(res);
                } else if(TextUtils.isEmpty(searchTime) && !TextUtils.isEmpty(searchParticipants)) {
                    String res = "";
                    String[] name = searchParticipants.split(",");
                    for (Meeting m : data) {
                        int a = 0;
                        for (int i = 0; i < name.length; i++) {
                            if(m.getParticipants().contains(name[i])) {
                                a++;
                            }
                        }
                        if(a == name.length) {
                            res += m.toString();
                        }
                    }
                    dataTv.setText(res);
                } else if(TextUtils.isEmpty(searchTime) && TextUtils.isEmpty(searchParticipants)) {
                    String res = "";
                    for (Meeting m : data) {
                        res += m.toString();
                    }
                    dataTv.setText(res);
                } else {
                    String res = "";
                    String[] name = searchParticipants.split(",");
                    for (Meeting m : data) {
                        int a = 0;
                        for (int i = 0; i < name.length; i++) {
                            if(m.getParticipants().contains(name[i])) {
                                a++;
                            }
                        }
                        if(a == name.length && m.getStartTime().contains(searchTime)) {
                            res += m.toString();
                        }
                    }
                    dataTv.setText(res);
                }
            }
        });
    }

    private void startActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.putExtra("data", data);
        intent.putExtra("fontsize", fontSize);
        intent.putExtra("fontcolor", fontColor);
        intent.putExtra("backgroundcolor", backgroundColor);
        startActivityForResult(intent, mainRequestCode);
    }

    private View.OnClickListener backClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity();
        }
    };
}