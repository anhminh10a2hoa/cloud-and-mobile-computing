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
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText firstname_input;
    private EditText lastname_input;
    private EditText phonenumber_input;
    private Button submit_button;
    private AutoCompleteTextView autoCompleteTextViewFirstname;
    private AutoCompleteTextView autoCompleteTextViewLastname;
    private AutoCompleteTextView autoCompleteTextViewPhonenumber;
    ArrayList<User> fullInfo = new ArrayList<User>();
    ArrayList<User1> fullInfo1 = new ArrayList<User1>();
    ArrayList<User2> fullInfo2 = new ArrayList<User2>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        firstname_input = findViewById(R.id.editTextTextPersonName3);
        lastname_input = findViewById(R.id.editTextTextPersonName4);
        phonenumber_input = findViewById(R.id.editTextPhone);
        submit_button = findViewById(R.id.button);

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
                    fullInfo.add(new User(firstname, lastname, phonenumber));
                    fullInfo1.add(new User1(lastname, firstname, phonenumber));
                    fullInfo2.add(new User2(phonenumber, firstname, lastname));
                }
            }
        });

        //Here we define the AutoCompleteTextView object
        autoCompleteTextViewFirstname = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        ArrayAdapter<User> arrayAdapterFirstName = new ArrayAdapter<User>(this, android.R.layout.simple_dropdown_item_1line, fullInfo);
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
        ArrayAdapter<User1> arrayAdapterLastName = new ArrayAdapter<User1>(this, android.R.layout.simple_dropdown_item_1line, fullInfo1);
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
        ArrayAdapter<User2> arrayAdapterPhoneNumber = new ArrayAdapter<User2>(this, android.R.layout.simple_dropdown_item_1line, fullInfo2);
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
    }
}