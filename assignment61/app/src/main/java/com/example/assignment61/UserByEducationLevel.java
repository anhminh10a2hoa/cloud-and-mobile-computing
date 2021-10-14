package com.example.assignment61;

public class UserByEducationLevel {
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String educationLevel;
    private String hoobies;

    UserByEducationLevel(String phonenumber, String firstname, String lastname, String educationLevel, String hoobies) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.educationLevel = educationLevel;
        this.hoobies = hoobies;
    }

    public String toString() {
        return educationLevel + "-" + firstname + "-" + lastname + "-" + phonenumber + "-" + hoobies;
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