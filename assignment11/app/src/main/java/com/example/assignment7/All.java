package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Vector;

public class All extends AppCompatActivity {
    int mainRequestCode = 0;
    private Button backButton;
    private TextView dataTv;
    private String preferencesName = "my_setting";
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
        setContentView(R.layout.activity_all);
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
        

        backButton = findViewById(R.id.button8);
        dataTv = findViewById(R.id.textView6);
        backButton.setOnClickListener(backClickListener);
        dataTv.setText(displayData(dbAdapter.getAllCustomers()));

        float fontSize = loadedSharedPrefs.getFloat("fontsize", 14.0f);
        dataTv.setTextSize(fontSize);

        int fontColor = loadedSharedPrefs.getInt("fontcolor", getResources().getColor(android.R.color.black));
        dataTv.setTextColor(fontColor);
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
            displayMessage(dataRows.toString());
            for (Object obj : dataRow) {
                data += obj;
            }
        }
        return data;
    }
}