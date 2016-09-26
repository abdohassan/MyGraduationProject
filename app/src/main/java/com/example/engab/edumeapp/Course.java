package com.example.engab.edumeapp;

/**
 * Created by engab on 05-May-16.
 */
public class Course {
    private String title, description,thumbnailUrl,pInstractor,nInstractor;
    private int id;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public String getnInstractor() {
        return nInstractor;
    }

    public void setnInstractor(String nInstractor) {
        this.nInstractor = nInstractor;
    }

    public String getpInstractor() {
        return pInstractor;
    }

    public void setpInstractor(String pInstractor) {
        this.pInstractor = pInstractor;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
    public String getDescription () {return description;}

    public void setDescription(String description) {this.description = description;}






}
