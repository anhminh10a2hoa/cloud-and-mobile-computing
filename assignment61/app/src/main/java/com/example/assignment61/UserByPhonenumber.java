package com.example.assignment61;

public class UserByPhonenumber {
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String educationLevel;
    private String hoobies;

    UserByPhonenumber(String phonenumber, String firstname, String lastname, String educationLevel, String hoobies) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.educationLevel = educationLevel;
        this.hoobies = hoobies;
    }

    public String toString() {
        return phonenumber + "-" + firstname + "-" + lastname + "-" + educationLevel + "-" + hoobies;
    }
}