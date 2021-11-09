package com.example.assignment7;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import yuku.ambilwarna.AmbilWarnaDialog;

public class setting extends AppCompatActivity {
    int mainRequestCode = 0;
    private Button colorBtn;
    private Button backBtn;
    private Button fontColorBtn;
    int mDefaultColor;

    private TextView backgroundTv;
    private TextView fontcolorTv;
    private TextView fontsizeTv;

    private EditText fontsizeEt;
    private Button submitBtn;
    String path;
    private File file;
    String settingFileName = "setting.txt";
    String backgroundColor = "";
    String fontColor = "";
    String fontSize = "";
    private static final int BLOCK_SIZE = 128;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mDefaultColor = getResources().getColor(android.R.color.white);

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

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            String backgroundColorE = extras.getString("backgroundcolor");
            String fontColorE = extras.getString("fontcolor");
            String fontSizeE = extras.getString("fontsize");

            if(!backgroundColorE.isEmpty()) {
                backgroundColor = backgroundColorE;
                getWindow().getDecorView().setBackgroundColor(Integer.parseInt(backgroundColorE));
            }
            if(!fontSizeE.isEmpty()) {
                fontSize = fontSizeE;
                fontsizeEt.setText(fontSizeE);
                backgroundTv.setTextSize(Float.parseFloat(fontSizeE));
                fontcolorTv.setTextSize(Float.parseFloat(fontSizeE));
                fontsizeTv.setTextSize(Float.parseFloat(fontSizeE));
            } else {
                fontsizeEt.setText("14");
            }
            if(!fontColorE.isEmpty()) {
                fontColor = fontColorE;
                backgroundTv.setTextColor(Integer.parseInt(fontColorE));
                fontcolorTv.setTextColor(Integer.parseInt(fontColorE));
                fontsizeTv.setTextColor(Integer.parseInt(fontColorE));
            }
        }

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                float newFontSize = Float.parseFloat(fontsizeEt.getText().toString());
                try {
                    fontSize = fontsizeEt.getText().toString();
                    file = new File(settingFileName);
                    //Here we make the file readable by other applications too.
                    file.setReadable(true, false);
                    FileOutputStream fileOutputStream = openFileOutput(settingFileName, MODE_PRIVATE);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    //Here we write the text to the file
                    outputStreamWriter.write(backgroundColor + "~abcd12345" + fontColor + "~abcd12345" + fontSize);
                    outputStreamWriter.close();
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.save_file_confirmation), Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.file_not_found), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.io_excp), Toast.LENGTH_LONG).show();
                }
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
                getWindow().getDecorView().setBackgroundColor(color);
                try {
                    backgroundColor = Integer.toString(color);
                    file = new File(settingFileName);
                    //Here we make the file readable by other applications too.
                    file.setReadable(true, false);
                    FileOutputStream fileOutputStream = openFileOutput(settingFileName, MODE_PRIVATE);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    //Here we write the text to the file
                    outputStreamWriter.write(backgroundColor + "~abcd12345" + fontColor + "~abcd12345" + fontSize);
                    outputStreamWriter.close();
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.save_file_confirmation), Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.file_not_found), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.io_excp), Toast.LENGTH_LONG).show();
                }
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
                backgroundTv.setTextColor(color);
                fontcolorTv.setTextColor(color);
                fontsizeTv.setTextColor(color);
                try {
                    fontColor = Integer.toString(color);
                    file = new File(settingFileName);
                    //Here we make the file readable by other applications too.
                    file.setReadable(true, false);
                    FileOutputStream fileOutputStream = openFileOutput(settingFileName, MODE_PRIVATE);
                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                    //Here we write the text to the file
                    outputStreamWriter.write(backgroundColor + "~abcd12345" + fontColor + "~abcd12345" + fontSize);
                    outputStreamWriter.close();
                    Toast.makeText(getApplicationContext(),getResources().getString(R.string.save_file_confirmation), Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.file_not_found), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.io_excp), Toast.LENGTH_LONG).show();
                }
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