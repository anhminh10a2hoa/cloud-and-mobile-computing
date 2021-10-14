package com.example.assignment61;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText firstname_input;
    private EditText lastname_input;
    private EditText phonenumber_input;
    private Button submit_button;
    private AutoCompleteTextView autoCompleteTextViewFirstname;
    private AutoCompleteTextView autoCompleteTextViewLastname;
    private AutoCompleteTextView autoCompleteTextViewPhonenumber;
    private AutoCompleteTextView autoCompleteTextViewEducationLevel;
    private CheckBox sportCB;
    private CheckBox readingCB;
    private CheckBox listeningCB;
    ArrayList<UserByFirstname> fullInfoByFirstname = new ArrayList<UserByFirstname>();
    ArrayList<UserByLastname> fullInfoByLastname = new ArrayList<UserByLastname>();
    ArrayList<UserByPhonenumber> fullInfoByPhonenumber = new ArrayList<UserByPhonenumber>();
    ArrayList<UserByEducationLevel> fullInfoByEducationLevel = new ArrayList<UserByEducationLevel>();
    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstname_input = findViewById(R.id.editTextTextPersonName3);
        lastname_input = findViewById(R.id.editTextTextPersonName4);
        phonenumber_input = findViewById(R.id.editTextPhone);
        submit_button = findViewById(R.id.button);
        sportCB = findViewById(R.id.chkbox_sports);
        readingCB = findViewById(R.id.chkbox_reading);
        listeningCB = findViewById(R.id.chkbox_listening);

        Spinner educationLevelSpinner = (Spinner) findViewById(R.id.spinner2);
        String[] educationArray= getResources().getStringArray(R.array.education);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, educationArray);
        educationLevelSpinner.setAdapter(adapter);
        educationLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                index = arg0.getSelectedItemPosition();
                Toast.makeText(getApplicationContext(), "Selected item: " + educationArray[index], Toast.LENGTH_SHORT).show();;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub
            }
        });

        submit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = firstname_input.getText().toString();
                String lastname = lastname_input.getText().toString();
                String phonenumber = phonenumber_input.getText().toString();
                if(TextUtils.isEmpty(firstname)) {
                    firstname_input.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(lastname)) {
                    lastname_input.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(phonenumber)) {
                    phonenumber_input.setBackgroundColor(Color.RED);
                }
                if(!TextUtils.isEmpty(firstname) && !TextUtils.isEmpty(lastname) && !TextUtils.isEmpty(phonenumber)) {
                    firstname_input.setBackgroundColor(Color.WHITE);
                    lastname_input.setBackgroundColor(Color.WHITE);
                    phonenumber_input.setBackgroundColor(Color.WHITE);
                    firstname_input.setText("");
                    lastname_input.setText("");
                    phonenumber_input.setText("");
                    String hoobies = "";
                    if (v instanceof CheckBox) {
                        CheckBox checkBox = (CheckBox) v;
                        hoobies = checkBox.getText().toString();
                    }
                    fullInfoByFirstname.add(new UserByFirstname(firstname, lastname, phonenumber, educationArray[index], hoobies));
                    fullInfoByLastname.add(new UserByLastname(lastname, firstname, phonenumber, educationArray[index], hoobies));
                    fullInfoByPhonenumber.add(new UserByPhonenumber(phonenumber, firstname, lastname, educationArray[index], hoobies));
                    fullInfoByEducationLevel.add(new UserByEducationLevel(phonenumber, firstname, lastname, educationArray[index], hoobies));
                }
            }
        });

        //Here we define the AutoCompleteTextView object
        autoCompleteTextViewFirstname = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<UserByFirstname> arrayAdapterFirstName = new ArrayAdapter<UserByFirstname>(this, android.R.layout.simple_dropdown_item_1line, fullInfoByFirstname);
        //Here we set the text color
        autoCompleteTextViewFirstname.setTextColor(Color.RED);
        //Here we define the required number of letters to be typed in the AutoCompleteTextView
        //green color for displayed hint
        autoCompleteTextViewFirstname.setHintTextColor(Color.MAGENTA);
        autoCompleteTextViewFirstname.setThreshold(1);
        //Here we set the array adapter for the AutoCompleteTextView
        autoCompleteTextViewFirstname.setAdapter(arrayAdapterFirstName);

        //Here we define ItemClickListener for the AutoCompleteTextView instance
        autoCompleteTextViewFirstname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Here we get a copy of the text searched from the AutocompleteTextView and then modify it
                String text = autoCompleteTextViewFirstname.getText().toString();
                //Here we set the text of the AutocompleteTextView to the modified text
                autoCompleteTextViewFirstname.setText(text.split("-")[0]);
            }
        });

        //Here we define the AutoCompleteTextView object
        autoCompleteTextViewLastname = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView4);
        ArrayAdapter<UserByLastname> arrayAdapterLastName = new ArrayAdapter<UserByLastname>(this, android.R.layout.simple_dropdown_item_1line, fullInfoByLastname);
        //Here we set the text color
        autoCompleteTextViewLastname.setTextColor(Color.RED);
        //Here we define the required number of letters to be typed in the AutoCompleteTextView
        //green color for displayed hint
        autoCompleteTextViewLastname.setHintTextColor(Color.MAGENTA);
        autoCompleteTextViewLastname.setThreshold(1);
        //Here we set the array adapter for the AutoCompleteTextView
        autoCompleteTextViewLastname.setAdapter(arrayAdapterLastName);

        //Here we define ItemClickListener for the AutoCompleteTextView instance
        autoCompleteTextViewLastname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Here we get a copy of the text searched from the AutocompleteTextView and then modify it
                String text = autoCompleteTextViewLastname.getText().toString();
                //Here we set the text of the AutocompleteTextView to the modified text
                autoCompleteTextViewLastname.setText(text.split("-")[0]);
            }
        });

        //Here we define the AutoCompleteTextView object
        autoCompleteTextViewPhonenumber = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView5);
        ArrayAdapter<UserByPhonenumber> arrayAdapterPhoneNumber = new ArrayAdapter<UserByPhonenumber>(this, android.R.layout.simple_dropdown_item_1line, fullInfoByPhonenumber);
        //Here we set the text color
        autoCompleteTextViewPhonenumber.setTextColor(Color.RED);
        //Here we define the required number of letters to be typed in the AutoCompleteTextView
        //green color for displayed hint
        autoCompleteTextViewPhonenumber.setHintTextColor(Color.MAGENTA);
        autoCompleteTextViewPhonenumber.setThreshold(1);
        //Here we set the array adapter for the AutoCompleteTextView
        autoCompleteTextViewPhonenumber.setAdapter(arrayAdapterPhoneNumber);

        //Here we define ItemClickListener for the AutoCompleteTextView instance
        autoCompleteTextViewPhonenumber.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Here we get a copy of the text searched from the AutocompleteTextView and then modify it
                String text = autoCompleteTextViewPhonenumber.getText().toString();
                //Here we set the text of the AutocompleteTextView to the modified text
                autoCompleteTextViewPhonenumber.setText(text.split("-")[0]);
            }
        });

        //Here we define the AutoCompleteTextView object
        autoCompleteTextViewEducationLevel = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView2);
        ArrayAdapter<UserByEducationLevel> arrayAdapterEducationLevel = new ArrayAdapter<UserByEducationLevel>(this, android.R.layout.simple_dropdown_item_1line, fullInfoByEducationLevel);
        //Here we set the text color
        autoCompleteTextViewEducationLevel.setTextColor(Color.RED);
        //Here we define the required number of letters to be typed in the AutoCompleteTextView
        //green color for displayed hint
        autoCompleteTextViewEducationLevel.setHintTextColor(Color.MAGENTA);
        autoCompleteTextViewEducationLevel.setThreshold(1);
        //Here we set the array adapter for the AutoCompleteTextView
        autoCompleteTextViewEducationLevel.setAdapter(arrayAdapterEducationLevel);

        //Here we define ItemClickListener for the AutoCompleteTextView instance
        autoCompleteTextViewEducationLevel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Here we get a copy of the text searched from the AutocompleteTextView and then modify it
                String text = autoCompleteTextViewEducationLevel.getText().toString();
                //Here we set the text of the AutocompleteTextView to the modified text
                autoCompleteTextViewEducationLevel.setText(text.split("-")[0]);
            }
        });
    }
}