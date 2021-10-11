package com.example.assignment42;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.time.LocalDate;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText username_input;
    EditText comment_input;
    EditText date_search;
    EditText comment_search;
    Button submit_button;
    Button search_button;
    TextView search_result;
    ArrayList<User> data = new ArrayList<User>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        User user1 = new User("minh", "minh says hello", "2021-10-10");
        User user2 = new User("toan", "toan says hi", "2021-10-10");
        User user3 = new User("mg", "mg says welcome", "2021-10-10");
        data.add(user1);
        data.add(user2);
        data.add(user3);

        username_input = findViewById(R.id.editTextTextPersonName);
        comment_input = findViewById(R.id.editTextTextPersonName2);
        date_search = findViewById(R.id.editTextTextPersonName3);
        comment_search = findViewById(R.id.editTextTextPersonName4);
        submit_button = findViewById(R.id.button);
        search_button = findViewById(R.id.button2);
        search_result = findViewById(R.id.textView5);

        submit_button.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String name = username_input.getText().toString();
                String comment = comment_input.getText().toString();
                if(TextUtils.isEmpty(name)) {
                    username_input.setBackgroundColor(Color.RED);
                }
                if(TextUtils.isEmpty(comment)) {
                    comment_input.setBackgroundColor(Color.RED);
                }
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(comment)) {
                    username_input.setBackgroundColor(Color.WHITE);
                    comment_input.setBackgroundColor(Color.WHITE);
                    User user = new User(name, comment);
                    data.add(user);
                }
            }
        });

        search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String date = date_search.getText().toString();
                String comment = comment_search.getText().toString();
                String res = "";
                search_result.setText("hello");
                Integer i = 1;
                for (User user : data) {
                    if(user.getComment().contains(comment) && user.getDate().contains(date)) {
                        res += "Number " + i + "\n";
                        res += user.toString();
                    }
                    i++;
                }
                search_result.setText(res);
            }
        });
    }
}