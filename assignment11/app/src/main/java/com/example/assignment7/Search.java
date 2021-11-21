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
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class Search extends AppCompatActivity {

    int mainRequestCode = 0;
    private Button searchButton;
    private Button backButton;
    private TextView dataTv;
    private EditText searchTimeEt;
    private EditText searchParticipantsEt;
    private String preferencesName = "my_setting";
    private TextView searchTimeTv;
    private TextView searchParticipantsTv;
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
        setContentView(R.layout.activity_search);
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

        searchButton = findViewById(R.id.button6);
        backButton = findViewById(R.id.button7);
        searchTimeEt = findViewById(R.id.editTextTextPersonName3);
        searchParticipantsEt = findViewById(R.id.editTextTextPersonName4);
        searchTimeTv = findViewById(R.id.textView7);
        searchParticipantsTv = findViewById(R.id.textView8);
        dataTv = findViewById(R.id.textView9);
        backButton.setOnClickListener(backClickListener);

        //        set font size font color
        float fontSize = loadedSharedPrefs.getFloat("fontsize", 14.0f);
        searchTimeTv.setTextSize(fontSize);
        searchParticipantsTv.setTextSize(fontSize);
        dataTv.setTextSize(fontSize);

        int fontColor = loadedSharedPrefs.getInt("fontcolor", getResources().getColor(android.R.color.black));
        searchTimeTv.setTextColor(fontColor);
        searchParticipantsTv.setTextColor(fontColor);
        dataTv.setTextColor(fontColor);

        Bundle extras = getIntent().getExtras();

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTime = searchTimeEt.getText().toString();
                String searchParticipants = searchParticipantsEt.getText().toString();
                if(!TextUtils.isEmpty(searchTime) && TextUtils.isEmpty(searchParticipants)) {
                    String queryResult = displayData(dbAdapter.getCustomerByTime(searchTime));
                    dataTv.setText(queryResult);
                } else if(TextUtils.isEmpty(searchTime) && !TextUtils.isEmpty(searchParticipants)) {
                    String queryResult = displayData(dbAdapter.getCustomerByParticipants(searchParticipants));
                    dataTv.setText(queryResult);
                } else if(TextUtils.isEmpty(searchTime) && TextUtils.isEmpty(searchParticipants)) {
                    String res = "";
                    dataTv.setText(res);
                } else {
                    String queryResult = displayData(dbAdapter.getCustomerByTimeAndParticipant(searchTime, searchParticipants));
                    dataTv.setText(queryResult);
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
    private String displayData(Vector<Object[]> dataRows){
        String data = "";
        for(Object[] dataRow : dataRows ) {
            for (Object obj : dataRow) {
                data += obj;
            }
        }
        return data;
    }
}