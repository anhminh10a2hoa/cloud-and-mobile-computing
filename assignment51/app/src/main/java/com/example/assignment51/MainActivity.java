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
    private LayoutParams viewLayoutParams;
    private Button button1;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        viewLayoutParams.leftMargin = 5;
        viewLayoutParams.rightMargin = 5;
        viewLayoutParams.topMargin = 5;
        viewLayoutParams.bottomMargin = 1000;

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);

        button1 = new Button(this);
        button1.setText(R.string.hide_btn);
        button1.setLayoutParams(viewLayoutParams);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (button2.isShown()){
                    button2.setVisibility(Button.INVISIBLE);
                    button1.setText(R.string.show_btn);
                }
                else {
                    button2.setVisibility(Button.VISIBLE);
                    button1.setText(R.string.hide_btn);
                }
            }
        });

        button2 = new Button(this);
        button2.setText("Button 2");
        button2.setLayoutParams(viewLayoutParams);
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

        linearLayout.addView(button1);
        linearLayout.addView(button2);

        LayoutParams linearLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        this.addContentView(linearLayout, linearLayoutParams);
    }
}