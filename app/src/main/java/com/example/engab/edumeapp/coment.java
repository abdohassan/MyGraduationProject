package com.example.engab.edumeapp;

/**
 * Created by engab on 25-Jun-16.
 */
public class coment {
    private String content, name,thumbnailUrl;
    private int id;
    public String getThumbnailUrl(){return thumbnailUrl;}

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}
}
