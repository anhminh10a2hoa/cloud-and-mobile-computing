package com.example.assignment2;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    String tag="EVH_Demo: ";
    long lastTimeCount = System.currentTimeMillis();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        long startTime = System.currentTimeMillis();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tag, tag + " onCreate() at: " + (startTime - lastTimeCount) + " ms");
        lastTimeCount = startTime;
    }
    protected void onStart() {
        long startTime = System.currentTimeMillis();
        super.onStart();
        Log.d(tag, tag + " onStart() at: " + (startTime - lastTimeCount) + " ms");
        lastTimeCount = startTime;
    }

    protected void onRestart() {
        long startTime = System.currentTimeMillis();
        super.onRestart();
        Log.d(tag, tag + " onRestart() at: " + (startTime - lastTimeCount) + " ms");
        lastTimeCount = startTime;
    }
    protected void onResume() {
        long startTime = System.currentTimeMillis();
        super.onResume();
        Log.d(tag, tag + " onResume() at: " + (startTime - lastTimeCount) + " ms");
        lastTimeCount = startTime;
    }
    protected void onPause() {
        long startTime = System.currentTimeMillis();
        super.onPause();
        Log.d(tag, tag + " onPause() at: " + (startTime - lastTimeCount) + " ms");
        lastTimeCount = startTime;
    }
    protected void onStop() {
        long startTime = System.currentTimeMillis();
        super.onStop();
        Log.d(tag, tag + " onStop() at: " + (startTime - lastTimeCount) + " ms");
        lastTimeCount = startTime;
    }

    protected void onDestroy() {
        long startTime = System.currentTimeMillis();
        super.onDestroy();
        Log.d(tag, tag + " onDestroy() at: " + (startTime - lastTimeCount) + " ms");
        lastTimeCount = startTime;
    }
}