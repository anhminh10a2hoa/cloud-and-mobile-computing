package com.example.assignment3;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.Date;

@RequiresApi(api = Build.VERSION_CODES.O)
public class MainActivity extends AppCompatActivity {
    String tag = "assignment3";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Log.d(tag, "Created.");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        String orient_port=getResources().getString(R.string.orient_port_txt);
        String orient_land=getResources().getString(R.string.orient_land_txt);
        String orient_def=getResources().getString(R.string.orient_def_txt);
        int orientation = newConfig.orientation;
        // Checks the orientation of the screen
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Toast.makeText(this, orient_land, Toast.LENGTH_LONG).show();
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            Toast.makeText(this, orient_port, Toast.LENGTH_LONG).show();
        }
        String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());
        Log.d(tag, "Orientation changed at "+ currentDateTimeString);
    }
}