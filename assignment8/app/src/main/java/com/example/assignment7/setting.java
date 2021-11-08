package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import yuku.ambilwarna.AmbilWarnaDialog;

public class setting extends AppCompatActivity {
    int mainRequestCode = 0;
    private SharedPreferences sharedPreferences;
    private Button colorBtn;
    private Button backBtn;
    private Button fontColorBtn;
    int mDefaultColor;
    private String preferencesName = "my_setting";

    private TextView backgroundTv;
    private TextView fontcolorTv;
    private TextView fontsizeTv;

    private EditText fontsizeEt;
    private Button submitBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SharedPreferences loadedSharedPrefs = getSharedPreferences(preferencesName, MODE_PRIVATE);

        getWindow().getDecorView().setBackgroundColor(loadedSharedPrefs.getInt("backgroundcolor", getResources().getColor(android.R.color.white)));

        mDefaultColor = getResources().getColor(android.R.color.white);
        sharedPreferences = getSharedPreferences(preferencesName, MODE_PRIVATE);

        colorBtn = findViewById(R.id.button11);
        colorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openColorPicker();
            }
        });
        fontColorBtn = findViewById(R.id.button13);
        fontColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFontColorPicker();
            }
        });
        backBtn = findViewById(R.id.button12);
        backBtn.setOnClickListener(backClickListener);
        backgroundTv = findViewById(R.id.textView13);
        fontcolorTv = findViewById(R.id.textView14);
        fontsizeTv = findViewById(R.id.textView17);
        fontsizeEt = findViewById(R.id.editTextNumber);
        submitBtn = findViewById(R.id.button15);

        float fontSize = loadedSharedPrefs.getFloat("fontsize", 14.0f);

        fontsizeEt.setText(String.valueOf(fontSize));
        backgroundTv.setTextSize(fontSize);
        fontcolorTv.setTextSize(fontSize);
        fontsizeTv.setTextSize(fontSize);

        int fontColor = loadedSharedPrefs.getInt("fontcolor", getResources().getColor(android.R.color.black));
        backgroundTv.setTextColor(fontColor);
        fontcolorTv.setTextColor(fontColor);
        fontsizeTv.setTextColor(fontColor);

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float newFontSize = Float.parseFloat(fontsizeEt.getText().toString());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putFloat("fontsize", newFontSize);
                editor.commit();
                backgroundTv.setTextSize(newFontSize);
                fontcolorTv.setTextSize(newFontSize);
                fontsizeTv.setTextSize(newFontSize);
            }
        });
    }

    public void openColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                // color is the color selected by the user.
                mDefaultColor = color;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("backgroundcolor", color);
                editor.commit();
                SharedPreferences loadedSharedPrefs = getSharedPreferences(preferencesName, MODE_PRIVATE);
                getWindow().getDecorView().setBackgroundColor(loadedSharedPrefs.getInt("backgroundcolor", getResources().getColor(android.R.color.white)));
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // cancel was selected by the user
            }
        });
        colorPicker.show();
    }

    public void openFontColorPicker() {
        AmbilWarnaDialog colorPicker = new AmbilWarnaDialog(this, mDefaultColor, new AmbilWarnaDialog.OnAmbilWarnaListener() {
            @Override
            public void onOk(AmbilWarnaDialog dialog, int color) {
                // color is the color selected by the user.
                mDefaultColor = color;
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putInt("fontcolor", color);
                editor.commit();
                backgroundTv.setTextColor(color);
                fontcolorTv.setTextColor(color);
                fontsizeTv.setTextColor(color);
            }

            @Override
            public void onCancel(AmbilWarnaDialog dialog) {
                // cancel was selected by the user
            }
        });
        colorPicker.show();
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