package com.example.assignment7;

import android.graphics.Bitmap;

import java.io.Serializable;

public class ImageParticipants implements Serializable {
    private String name;
    private Bitmap image;
    private String imagePath;


    ImageParticipants(String name, Bitmap image, String imagePath) {
        this.name = name;
        this.image = image;
        this.imagePath = imagePath;
    }

    public String getName() {
        return this.name;
    }
    public void setImage(Bitmap image) {
        this.image = image;
    }
    public Bitmap getImage() {
        return this.image;
    }
    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public String toString() {
        String res = "";
        res +=  "Name: " + this.name
                + "\n" + "Image path: " + this.imagePath + "\n\n";
        return res;
    }
}
