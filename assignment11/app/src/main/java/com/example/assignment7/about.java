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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class about extends AppCompatActivity {
    int mainRequestCode = 0;
    private Button backButton;
    private String preferencesName = "my_setting";
    private EditText addData;
    private TextView aboutText;
    private TextView aboutTitle;
    private static final int BLOCK_SIZE = 128;
    String path;
    String dirName;
    String dataFileName;
    File savingDirectory;
    File file;
    private final String lineSeparator=System.getProperty("line.separator");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        //Here we initialize directory and file names.
        dirName=getResources().getText(R.string.dir_name).toString();
        dataFileName=getResources().getText(R.string.data_file_name).toString();
        //Here we define a File object, which refers to a directory on
        //the SD card.
        savingDirectory = getApplicationContext().getExternalFilesDir(dirName);
        //Here we create the directory on the SD card
        if(savingDirectory!=null && !savingDirectory.exists()) savingDirectory.mkdirs();
        Toast.makeText(getBaseContext(),
                savingDirectory.getAbsolutePath() + getResources().getText(R.string.saving_directory_fb) + " " +savingDirectory.exists(), Toast.LENGTH_LONG)
                .show();
        path = getFilesDir().getAbsolutePath() + File.separator;

        SharedPreferences loadedSharedPrefs = getSharedPreferences(preferencesName, MODE_PRIVATE);

        getWindow().getDecorView().setBackgroundColor(loadedSharedPrefs.getInt("backgroundcolor", getResources().getColor(android.R.color.white)));


        backButton = findViewById(R.id.button17);
        addData = findViewById(R.id.editTextTextPersonName5);
        aboutText = findViewById(R.id.textView18);
        aboutTitle = findViewById(R.id.textView19);
        backButton.setOnClickListener(backClickListener);

        float fontSize = loadedSharedPrefs.getFloat("fontsize", 14.0f);
        aboutText.setTextSize(fontSize);
        aboutTitle.setTextSize(fontSize);

        int fontColor = loadedSharedPrefs.getInt("fontcolor", getResources().getColor(android.R.color.black));
        aboutText.setTextColor(fontColor);
        aboutTitle.setTextColor(fontColor);
        // get content
        FileInputStream fileInputStream;
        InputStreamReader inputStreamReader;
        try {
            savingDirectory = getApplicationContext().getExternalFilesDir(dirName);
//Here we initialize the File object, which refers to
//the comment file on the SD card under the specified directory
            file = new File(savingDirectory, dataFileName);
            fileInputStream = new FileInputStream(file);
            inputStreamReader = new InputStreamReader(fileInputStream);
            char[] inputBuffer = new char[BLOCK_SIZE];
            StringBuilder fileContent = new StringBuilder();
            int charRead;
            while ((charRead = inputStreamReader.read(inputBuffer)) > 0) {
// Here we convert chars to string
                String readString = String.copyValueOf(inputBuffer, 0,
                        charRead);
                fileContent.append(readString).append(lineSeparator);
// Here we re-initialize the inputBuffer array to remove
// its content
                inputBuffer = new char[BLOCK_SIZE];
            }
// Here we set the text of the commentEditText to the one,
// which has been read
            aboutText.setText(fileContent);
            Toast.makeText(getBaseContext(),
                    getString(R.string.file_load_fb), Toast.LENGTH_LONG)
                    .show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

//        backButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String text = addData.getText().toString();
//                try {
////Here we initialize the File object, which refers to
////the comment file on the SD card under the specified directory
//                    File file = new File(savingDirectory, dataFileName);
////Here we choose to append new content to the previous file content
//                    FileOutputStream fileOutputStream = new FileOutputStream(file, true);
////Here we intialize the write stream
//                    OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
////Here we write the text to the file
//                    outputStreamWriter.write(text);
////Here we close the write stream
//                    outputStreamWriter.close();
//                    Toast.makeText(getBaseContext(),
//                            getString(R.string.file_save_fb), Toast.LENGTH_LONG)
//                            .show();
//                    addData.setText("");
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
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