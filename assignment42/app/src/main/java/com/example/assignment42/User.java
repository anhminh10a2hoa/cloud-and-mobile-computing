package com.example.assignment42;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;

public class User {
    private String username;
    private String comment;
    private String date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    User(String username, String comment) {
        this.username = username;
        this.comment = comment;
        this.date = LocalDate.now().toString();
    }

    User(String username, String comment, String date) {
        this.username = username;
        this.comment = comment;
        this.date = date;
    }

    public String toString() {
        String res = "";
        res += "Date: " + date + "\n";
        res += "Name: " + username + "\n";
        res += "Comment: " + comment + "\n\n";

        return res;
    }

    public String getComment() {
        return this.comment;
    }

    public String getDate() {
        return this.date;
    }
}
