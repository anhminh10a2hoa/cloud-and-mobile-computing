package com.example.assignment52;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

import java.text.DateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    private LayoutParams viewLayoutParams;
    private TextView username_text;
    private EditText username_input;
    private TextView password_text;
    private EditText password_input;
    private Button button;
    private Button button_back;
    private TextView res;
    private Integer step = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewLayoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        viewLayoutParams.leftMargin = 5;
        viewLayoutParams.rightMargin = 5;
        viewLayoutParams.topMargin = 5;
        viewLayoutParams.bottomMargin = 1000;

        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);

        username_text = new TextView(this);
        username_text.setText(R.string.username_label);
        username_input = new EditText(this);
        password_text = new TextView(this);
        password_text.setText(R.string.password_label);
        password_input = new EditText(this);
        password_input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        button = new Button(this);
        button.setText(R.string.next_btn);
        button_back = new Button(this);
        button_back.setText(R.string.back_btn);
        res = new TextView(this);

        linearLayout.addView(username_text);
        linearLayout.addView(username_input);
        linearLayout.addView(password_text);
        linearLayout.addView(password_input);
        linearLayout.addView(button);
        linearLayout.addView(button_back);
        linearLayout.addView(res);

        //initialization
        password_text.setVisibility(TextView.INVISIBLE);
        password_input.setVisibility(EditText.INVISIBLE);
        button_back.setVisibility(EditText.INVISIBLE);
        res.setVisibility(TextView.INVISIBLE);


        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String name = username_input.getText().toString();
                String password = password_input.getText().toString();
                if(step == 0) {
                    if(TextUtils.isEmpty(name)) {
                        username_input.setBackgroundColor(Color.RED);
                    } else {
                        username_input.setBackgroundColor(Color.WHITE);
                        button_back.setVisibility(TextView.VISIBLE);
                        password_text.setVisibility(TextView.VISIBLE);
                        password_input.setVisibility(EditText.VISIBLE);
                        step++;
                    }
                } else if(step == 1) {
                    if(TextUtils.isEmpty(password)) {
                        password_input.setBackgroundColor(Color.RED);
                    } else {
                        password_input.setBackgroundColor(Color.WHITE);
                        res.setVisibility(TextView.VISIBLE);
                        button_back.setVisibility(EditText.INVISIBLE);
                        button.setText(R.string.again_btn);
                        String info = "";
                        info += "Username: " + username_input.getText().toString() + "\n";
                        info += "Password: " + password_input.getText().toString() + "\n";
                        info += "Date: " +  DateFormat.getDateTimeInstance().format(new Date());
                        res.setText(info);
                        step++;
                    }
                } else if(step == 2) {
                    username_input.setText("");
                    password_input.setText("");
                    button.setVisibility(EditText.VISIBLE);
                    button.setText(R.string.next_btn);
                    res.setVisibility(TextView.INVISIBLE);
                    password_text.setVisibility(TextView.INVISIBLE);
                    password_input.setVisibility(EditText.INVISIBLE);
                    step = 0;
                }
            }
        });

        button_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(step == 1) {
                    password_input.setBackgroundColor(Color.WHITE);
                    password_input.setText("");
                    password_text.setVisibility(TextView.INVISIBLE);
                    password_input.setVisibility(EditText.INVISIBLE);
                    button_back.setVisibility(EditText.INVISIBLE);
                    step--;
                } else if(step == 2) {
                    username_input.setText("");
                    password_input.setText("");
                    button.setVisibility(EditText.VISIBLE);
                    username_input.setBackgroundColor(Color.WHITE);
                    button_back.setVisibility(TextView.INVISIBLE);
                    res.setVisibility(TextView.INVISIBLE);
                    password_text.setVisibility(TextView.INVISIBLE);
                    password_input.setVisibility(EditText.INVISIBLE);
                    step = 0;
                }
            }
        });

        LayoutParams linearLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        this.addContentView(linearLayout, linearLayoutParams);
//        setContentView(R.layout.activity_main);

//        username_text = (TextView) findViewById(R.id.textView);
//        username_input = (EditText) findViewById(R.id.editTextTextPersonName);
//        password_text = (TextView) findViewById(R.id.textView2);
//        password_input = (EditText) findViewById(R.id.editTextTextPassword);
//        button = (Button) findViewById(R.id.button);
//        res = (TextView) findViewById(R.id.textView3);
//        password_text.setVisibility(TextView.INVISIBLE);
//        password_input.setVisibility(EditText.INVISIBLE);
//

    }
}