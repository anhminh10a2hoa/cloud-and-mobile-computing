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
    ArrayList<Meeting> data;
    private String preferencesName = "my_setting";
    String backgroundColor = "";
    String fontColor = "";
    String fontSize = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all);
        

        backButton = findViewById(R.id.button8);
        dataTv = findViewById(R.id.textView6);
        backButton.setOnClickListener(backClickListener);
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            data = (ArrayList<Meeting>)extras.getSerializable("data");
            String res = "";
            for (Meeting m : data) {
                res += m.toString();
            }
            dataTv.setText(res);
//            backgroundColor = extras.getString("backgroundcolor");
//            fontColor = extras.getString("fontcolor");
//            fontSize = extras.getString("fontsize");
            if(fontSize != "") {
                float fontSizeZ = Float.parseFloat(fontSize);
                dataTv.setTextSize(fontSizeZ);
            }
            if(fontColor != "") {
                int fontColorZ = Integer.parseInt(fontColor);
                dataTv.setTextColor(fontColorZ);
            }
            if(backgroundColor != "") {
                int backgroundColorZ = Integer.parseInt(backgroundColor);
                getWindow().getDecorView().setBackgroundColor(backgroundColorZ);
            }
        }

    }

    private void startActivity() {
        Intent intent = new Intent(getApplication(), MainActivity.class);
        intent.putExtra("data", data);
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