package com.example.assignment7;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
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
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    int searchRequestCode = 1;
    int allRequestCode = 2;
    int updateRequestCode = 3;
    int settingRequestCode = 4;

    private Button addBtn;
    private Button updateBtn;
    private Button searchBtn;
    private Button allBtn;
    private Button submitBtn;
    private EditText titleEt;
    private EditText participantsEt;
    private Button timePickerBtn;
    private TextView timeTv;
    private Button datePickerBtn;
    private TextView dateTv;
    private Button settingBtn;
    private TextView titleTv;
    private TextView meetingTitleTv;
    private TextView participantsTv;
    private TextView startDateTv;
    private TextView startTimeTv;
    private Button loadDataBtn;
    private Button loadSettingBtn;
    String path;
    private File file;
    String dataFileName = "user.txt";
    String settingFileName = "setting.txt";
    String backgroundColor = "";
    String fontColor = "";
    String fontSize = "";
    private static final int BLOCK_SIZE = 128;

    DialogFragment newFragment;
    ArrayList<Meeting> meetingArrayList = new ArrayList<Meeting>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        path = getFilesDir().getAbsoluteFile() + "/";

        addBtn = findViewById(R.id.button);
        updateBtn = findViewById(R.id.button2);
        searchBtn = findViewById(R.id.button3);
        allBtn = findViewById(R.id.button5);
        submitBtn = findViewById(R.id.button4);
        titleEt = findViewById(R.id.editTextTextPersonName);
        participantsEt = findViewById(R.id.editTextTextPersonName2);
        timePickerBtn = findViewById(R.id.time_button);
        datePickerBtn = findViewById(R.id.date_button);
        timeTv = findViewById(R.id.textView12);
        dateTv = findViewById(R.id.textView11);
        settingBtn = findViewById(R.id.button10);
        titleTv = findViewById(R.id.textView);
        meetingTitleTv= findViewById(R.id.textView2);
        participantsTv= findViewById(R.id.textView3);
        startDateTv= findViewById(R.id.textView4);
        startTimeTv= findViewById(R.id.textView5);
        loadDataBtn = findViewById(R.id.button16);
        loadSettingBtn = findViewById(R.id.button17);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            meetingArrayList = (ArrayList<Meeting>)extras.getSerializable("data");
            String backgroundColorE = extras.getString("backgroundcolor");
            String fontColorE = extras.getString("fontcolor");
            String fontSizeE = extras.getString("fontsize");
            if(!fontSizeE.isEmpty()) {
                fontSize = fontSizeE;
                float fontSizeZ = Float.parseFloat(fontSizeE);
                titleTv.setTextSize(fontSizeZ);
                meetingTitleTv.setTextSize(fontSizeZ);
                participantsTv.setTextSize(fontSizeZ);
                startDateTv.setTextSize(fontSizeZ);
                startTimeTv.setTextSize(fontSizeZ);
            }
            if(!fontColorE.isEmpty()) {
                fontColor = fontColorE;
                int fontColorZ = Integer.parseInt(fontColorE);
                titleTv.setTextColor(fontColorZ);
                meetingTitleTv.setTextColor(fontColorZ);
                participantsTv.setTextColor(fontColorZ);
                startDateTv.setTextColor(fontColorZ);
                startTimeTv.setTextColor(fontColorZ);
            }
            if(!backgroundColorE.isEmpty()) {
                backgroundColor = backgroundColorE;
                int backgroundColorZ = Integer.parseInt(backgroundColorE);
                getWindow().getDecorView().setBackgroundColor(backgroundColorZ);
            }
        }

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        updateBtn.setOnClickListener(updateClickListener);

        searchBtn.setOnClickListener(searchClickListener);

        settingBtn.setOnClickListener(settingClickListener);

        allBtn.setOnClickListener(allClickListener);

        timePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new TimePickerFragment();
                newFragment.show(getSupportFragmentManager(), "TimePicker");
            }
        });

        datePickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "DatePicker");
            }
        });

        submitBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String title = titleEt.getText().toString();
                String participants = participantsEt.getText().toString();
                String startDate = dateTv.getText().toString();
                String startTime = timeTv.getText().toString();
                if(TextUtils.isEmpty(title)) {
                    titleEt.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(participants)) {
                    participantsEt.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(startDate)) {
                    dateTv.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(startTime)) {
                    timeTv.setBackgroundColor(Color.RED);
                }
                if(!TextUtils.isEmpty(title) && !TextUtils.isEmpty(participants) && !TextUtils.isEmpty(startDate) && !TextUtils.isEmpty(startTime)) {
                    titleEt.setBackgroundColor(Color.WHITE);
                    participantsEt.setBackgroundColor(Color.WHITE);
                    dateTv.setBackgroundColor(Color.WHITE);
                    timeTv.setBackgroundColor(Color.WHITE);
                    meetingArrayList.add(new Meeting(meetingArrayList.size(), title, participants, startDate + " " + startTime));
                    titleEt.setText("");
                    participantsEt.setText("");
                    dateTv.setText("");
                    timeTv.setText("");
                    try {
                        file = new File(dataFileName);
                        //Here we make the file readable by other applications too.
                        file.setReadable(true, false);
                        FileOutputStream fileOutputStream = openFileOutput(dataFileName, MODE_PRIVATE);
                        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);
                        //Here we write the text to the file
                        String res = "";
                        for(Meeting m: meetingArrayList) {
                            res += m.saveToFile() + "~xyz";
                        }
                        outputStreamWriter.write(res);
                        outputStreamWriter.close();
                        Toast.makeText(getApplicationContext(),getResources().getString(R.string.save_file_confirmation), Toast.LENGTH_LONG).show();
                    } catch (FileNotFoundException e) {
                        Toast.makeText(getApplicationContext(), getResources().getString(R.string.file_not_found), Toast.LENGTH_LONG).show();
                    } catch (IOException e) {
                        Toast.makeText(getApplicationContext(), getString(R.string.io_excp), Toast.LENGTH_LONG).show();
                    }
                }
            }
        });

        loadDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileInputStream fileInputStream;
                InputStreamReader inputStreamReader;
                try {
                    fileInputStream = openFileInput(dataFileName);
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    char[] inputBuffer = new char[BLOCK_SIZE];
                    StringBuilder fileContent= new StringBuilder();
                    int charRead;
                    while((charRead = inputStreamReader.read(inputBuffer))>0) {
                        //Here we convert chars to string
                        String readString =String.copyValueOf(inputBuffer, 0, charRead);
                        fileContent.append(readString);
                        //Here we re-initialize the inputBuffer array to remove its content
                        inputBuffer = new char[BLOCK_SIZE];
                    }
                    //Here we set the text of the commentEditText to the one, which has been read
                    if(fileContent.toString() != "") {
                        String[] arr = fileContent.toString().split("~xyz");
                        ArrayList<Meeting> meetingArrayListFromFile = new ArrayList<Meeting>();
                        for (int i = 0; i < arr.length; i++) {
                            if(arr[i] != "") {
                                String[] arrData = arr[i].split("~abc");
                                meetingArrayListFromFile.add(new Meeting(Integer.parseInt(arrData[0]), arrData[1], arrData[2], arrData[3]));
                            }
                        }
                        meetingArrayList = meetingArrayListFromFile;
                    }
                    Toast.makeText(getApplicationContext(), getString(R.string.file_load_fb), Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.file_not_found), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.io_excp), Toast.LENGTH_LONG).show();
                }
            }
        });

        loadSettingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileInputStream fileInputStream;
                InputStreamReader inputStreamReader;
                try {
                    fileInputStream = openFileInput(settingFileName);
                    inputStreamReader = new InputStreamReader(fileInputStream);
                    char[] inputBuffer = new char[BLOCK_SIZE];
                    StringBuilder fileContent= new StringBuilder();
                    int charRead;
                    while((charRead = inputStreamReader.read(inputBuffer))>0) {
                        //Here we convert chars to string
                        String readString =String.copyValueOf(inputBuffer, 0, charRead);
                        fileContent.append(readString);
                        //Here we re-initialize the inputBuffer array to remove its content
                        inputBuffer = new char[BLOCK_SIZE];
                    }
                    //Here we set the text of the commentEditText to the one, which has been read
                    if(fileContent.toString() != "") {
                        String[] arr = fileContent.toString().split("~abcd12345");
                        if(!arr[2].isEmpty()) {
                            fontSize = arr[2];
                            float fontSizeZ = Float.parseFloat(arr[2]);
                            titleTv.setTextSize(fontSizeZ);
                            meetingTitleTv.setTextSize(fontSizeZ);
                            participantsTv.setTextSize(fontSizeZ);
                            startDateTv.setTextSize(fontSizeZ);
                            startTimeTv.setTextSize(fontSizeZ);
                        }
                        if(!arr[1].isEmpty()) {
                            fontColor = arr[1];
                            int fontColorZ = Integer.parseInt(arr[1]);
                            titleTv.setTextColor(fontColorZ);
                            meetingTitleTv.setTextColor(fontColorZ);
                            participantsTv.setTextColor(fontColorZ);
                            startDateTv.setTextColor(fontColorZ);
                            startTimeTv.setTextColor(fontColorZ);
                        }
                        if(!arr[0].isEmpty()) {
                            backgroundColor = arr[0];
                            int backgroundColorZ = Integer.parseInt(arr[0]);
                            getWindow().getDecorView().setBackgroundColor(backgroundColorZ);
                        }
                    }
                    Toast.makeText(getApplicationContext(), getString(R.string.file_load_fb), Toast.LENGTH_LONG).show();
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.file_not_found), Toast.LENGTH_LONG).show();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(), getString(R.string.io_excp), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startActivity() {
        Intent intent = new Intent(getApplication(), Search.class);
        intent.putExtra("data", meetingArrayList);
        intent.putExtra("fontsize", fontSize);
        intent.putExtra("fontcolor", fontColor);
        intent.putExtra("backgroundcolor", backgroundColor);
        startActivityForResult(intent, searchRequestCode);
    }

    private void startActivityAll() {
        Intent intent = new Intent(getApplication(), All.class);
        intent.putExtra("data", meetingArrayList);
        intent.putExtra("fontsize", fontSize);
        intent.putExtra("fontcolor", fontColor);
        intent.putExtra("backgroundcolor", backgroundColor);
        startActivityForResult(intent, allRequestCode);
    }

    private void startActivityUpdate() {
        Intent intent = new Intent(getApplication(), Update.class);
        intent.putExtra("data", meetingArrayList);
        intent.putExtra("fontsize", fontSize);
        intent.putExtra("fontcolor", fontColor);
        intent.putExtra("backgroundcolor", backgroundColor);
        startActivityForResult(intent, updateRequestCode);
    }

    private void startActivitySetting() {
        Intent intent = new Intent(getApplication(), setting.class);
        intent.putExtra("fontsize", fontSize);
        intent.putExtra("fontcolor", fontColor);
        intent.putExtra("backgroundcolor", backgroundColor);
        startActivityForResult(intent, settingRequestCode);
    }

    private View.OnClickListener searchClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity();
        }
    };

    private View.OnClickListener allClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityAll();
        }
    };

    private View.OnClickListener updateClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivityUpdate();
        }
    };

    private View.OnClickListener settingClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivitySetting();
        }
    };

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == searchRequestCode) {
            if(resultCode == RESULT_OK) {

            }
        }
    }
}