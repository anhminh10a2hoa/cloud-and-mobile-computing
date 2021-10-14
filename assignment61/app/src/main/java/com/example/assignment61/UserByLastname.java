package com.example.assignment61;

public class UserByLastname {
    private String firstname;
    private String lastname;
    private String phonenumber;
    private String educationLevel;
    private String hoobies;

    UserByLastname(String lastname, String firstname, String phonenumber, String educationLevel, String hoobies) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phonenumber = phonenumber;
        this.educationLevel = educationLevel;
        this.hoobies = hoobies;
    }

    public String toString() {
        return lastname + "-" + firstname + "-" + phonenumber + "-" + educationLevel + "-" + hoobies;
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