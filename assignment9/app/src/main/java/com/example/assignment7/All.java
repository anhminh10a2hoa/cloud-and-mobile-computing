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
            if(data != null && data.size() > 0) {
                String res = "";
                for (Meeting m : data) {
                    res += m.toString();
                }
                dataTv.setText(res);
            }
            String backgroundColorE = extras.getString("backgroundcolor");
            String fontColorE = extras.getString("fontcolor");
            String fontSizeE = extras.getString("fontsize");
            if(!fontSizeE.isEmpty()) {
                fontSize = fontSizeE;
                float fontSizeZ = Float.parseFloat(fontSizeE);
                dataTv.setTextSize(fontSizeZ);
            }
            if(!fontColorE.isEmpty()) {
                fontColor = fontColorE;
                int fontColorZ = Integer.parseInt(fontColorE);
                dataTv.setTextColor(fontColorZ);
            }
            if(!backgroundColorE.isEmpty()) {
                backgroundColor = backgroundColorE;
                int backgroundColorZ = Integer.parseInt(backgroundColorE);
                getWindow().getDecorView().setBackgroundColor(backgroundColorZ);
            }
        }

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