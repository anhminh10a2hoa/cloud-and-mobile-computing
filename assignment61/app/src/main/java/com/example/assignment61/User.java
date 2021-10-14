package com.example.assignment61;

public class User {
    private String firstname;
    private String lastname;
    private String phonenumber;

    User(String firstname, String lastname, String phonenumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
    }

    public String toString() {
        String res = "";
        res += firstname + "-";
        res += lastname + "-";
        res += phonenumber;

        return res;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public String getLastname() {
        return this.lastname;
    }

    public String getPhonenumber() {
        return this.phonenumber;
    }
}