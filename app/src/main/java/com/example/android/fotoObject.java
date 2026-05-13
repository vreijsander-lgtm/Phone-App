package com.example.android;

public class fotoObject {

    private String fotoId;
    private String imageLink;

    public fotoObject(String fotoId, String imageLink) {
        this.fotoId = fotoId;
        this.imageLink = imageLink;
    }

    public String getFotoId() {
        return fotoId;
    }

    public String getImageLink() {
        return imageLink;
    }
}