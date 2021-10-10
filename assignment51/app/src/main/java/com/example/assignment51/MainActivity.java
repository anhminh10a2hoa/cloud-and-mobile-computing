package com.example.assignment51;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.content.res.Resources;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class MainActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button1 = (Button) findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button2.isShown()){
                    button2.setVisibility(Button.INVISIBLE);
                }
                else {
                    button2.setVisibility(Button.VISIBLE);
                }
            }
        });

        button2 = (Button) findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            int y = 0;
            public void onClick(View v) {
                if(y > 1000) {
                    y = 0;
                }
                button1.setY(y);
                y += 80;
            }
        });
    }
}