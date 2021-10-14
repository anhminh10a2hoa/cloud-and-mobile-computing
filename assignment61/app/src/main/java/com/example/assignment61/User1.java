package com.example.assignment61;

public class User1 {
    private String firstname;
    private String lastname;
    private String phonenumber;

    User1(String lastname, String firstname, String phonenumber) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
    }

    public String toString() {
        String res = "";
        res += lastname + "-";
        res += firstname + "-";
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