package com.example.assignment7;

import java.io.Serializable;

public class Meeting implements Serializable {
    private Integer id;
    private String title;
    private String participants;
    private String startTime;

    Meeting(Integer id, String title, String participants, String startTime) {
        this.id = id;
        this.title = title;
        this.participants = participants;
        this.startTime = startTime;
    }

    public Integer getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String newTitle) {
        this.title = newTitle;
    }

    public String getParticipants() {
        return this.participants;
    }

    public void setParticipants(String newParticipants) {
        this.participants = newParticipants;
    }

    public String getStartTime() {
        return this.startTime;
    }

    public void setStartTime(String newStartTime) {
        this.startTime = newStartTime;
    }

    public String toString() {
        String res = "";
        res +=  "Id: " + this.id
                + "\n" + "Title: " + this.title
                + "\n" + "Participants: " + this.participants
                + "\n" + "Start time: " + this.startTime + "\n\n";
        return res;
    }

    public String saveToFile() {
        return this.id + "~abc" + this.title + "~abc" + this.participants + "~abc" + this.startTime;
    }
}
