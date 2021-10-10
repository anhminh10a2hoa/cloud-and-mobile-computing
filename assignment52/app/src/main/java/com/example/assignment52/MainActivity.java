package com.example.assignment52;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    private TextView username_text;
    private EditText username_input;
    private TextView password_text;
    private EditText password_input;
    private Button button;
    private TextView res;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username_text = (TextView) findViewById(R.id.textView);
        username_input = (EditText) findViewById(R.id.editTextTextPersonName);
        password_text = (TextView) findViewById(R.id.textView2);
        password_input = (EditText) findViewById(R.id.editTextTextPassword);
        button = (Button) findViewById(R.id.button);
        res = (TextView) findViewById(R.id.textView3);
        password_text.setVisibility(TextView.INVISIBLE);
        password_input.setVisibility(EditText.INVISIBLE);

        button.setOnClickListener(new View.OnClickListener() {
            Integer step = 0;
            public void onClick(View v) {
                if(step == 0) {
                    username_text.setVisibility(TextView.INVISIBLE);
                    username_input.setVisibility(EditText.INVISIBLE);
                    res.setVisibility(TextView.INVISIBLE);
                    password_text.setVisibility(TextView.VISIBLE);
                    password_input.setVisibility(EditText.VISIBLE);
                    step++;
                } else if(step == 1) {
                    username_text.setVisibility(TextView.INVISIBLE);
                    username_input.setVisibility(EditText.INVISIBLE);
                    res.setVisibility(TextView.VISIBLE);
                    password_text.setVisibility(TextView.INVISIBLE);
                    password_input.setVisibility(EditText.INVISIBLE);
                    button.setText("Back");
                    String info = "";
                    info += "Username: " + username_input.getText().toString() + "\n";
                    info += "Password: " + password_input.getText().toString() + "\n";
                    info += "Date: " +  DateFormat.getDateTimeInstance().format(new Date());
                    res.setText(info);
                    step++;
                } else if(step == 2) {
                    username_input.setText("");
                    password_input.setText("");
                    username_text.setVisibility(TextView.VISIBLE);
                    username_input.setVisibility(EditText.VISIBLE);
                    res.setVisibility(TextView.INVISIBLE);
                    password_text.setVisibility(TextView.INVISIBLE);
                    password_input.setVisibility(EditText.INVISIBLE);
                    button.setText("Continue");
                    step = 0;
                }
            }
        });
    }
}