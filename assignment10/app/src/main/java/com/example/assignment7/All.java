package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class All extends AppCompatActivity {
    int mainRequestCode = 0;
    private Button backButton;
    private TextView dataTv;
    private String preferencesName = "my_setting";
    DBAdapter dbAdapter=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        dbAdapter = new DBAdapter(getApplicationContext());
        SharedPreferences loadedSharedPrefs = getSharedPreferences(preferencesName, MODE_PRIVATE);

        getWindow().getDecorView().setBackgroundColor(loadedSharedPrefs.getInt("backgroundcolor", getResources().getColor(android.R.color.white)));
        

        backButton = findViewById(R.id.button8);
        dataTv = findViewById(R.id.textView6);
        backButton.setOnClickListener(backClickListener);
        String queryResult = dbAdapter.getAllCustomers();
        dataTv.setText(queryResult);

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
}