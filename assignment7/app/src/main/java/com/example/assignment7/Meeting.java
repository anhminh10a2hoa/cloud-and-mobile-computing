package com.example.assignment7;
import java.time.format.DateTimeFormatter;

public class Meeting {
    private String id;
    private String title;
    private String participants;
    private DateTimeFormatter startTime;
    private Double duration;

    Meeting(String title, String participants, DateTimeFormatter startTime, Double duration) {
        this.id = id;
        this.title = title;
        this.participants = participants;
        this.startTime = startTime;
        this.duration = duration;
    }

    public String getId() {
        return this.id;
    }

    public String getTitle() {
        return this.title;
    }

    public String getParticipants() {
        return this.participants;
    }

    public DateTimeFormatter getStartTime() {
        return this.startTime;
    }

    public Double getDuration() {
        return this.duration;
    }

    public String toString() {
        String res = "";
        res +=  "Id: " + this.id
                + "\n" + "Title: " + this.title
                + "\n" + "Participants: " + this.participants
                + "\n" + "Start time: " + this.startTime
                + "\n" + "Duration: " + this.duration;
        return res;
    }
}
