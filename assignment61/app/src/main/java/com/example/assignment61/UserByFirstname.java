package com.example.assignment61;

public class UserByFirstname {
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String educationLevel;
    private String hoobies;

    UserByFirstname(String firstname, String lastname, String phonenumber, String educationLevel, String hoobies) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.educationLevel = educationLevel;
        this.hoobies = hoobies;
    }

    public String toString() {
        return firstname + "-" + lastname + "-" + phonenumber + "-" + educationLevel + "-" + hoobies;
    }
}