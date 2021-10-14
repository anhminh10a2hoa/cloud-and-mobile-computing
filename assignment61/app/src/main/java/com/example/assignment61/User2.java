package com.example.assignment61;

public class User2 {
    private String firstname;
    private String lastname;
    private String phonenumber;

    User2(String phonenumber, String firstname, String lastname) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
    }

    public String toString() {
        String res = "";
        res += phonenumber + "-";
        res += firstname + "-";
        res += lastname;

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