package com.example.newsdesk.Classes;

import com.google.firebase.database.PropertyName;

public class NewsArticles {
    public String images, name, body, categoies, writerEmail;


    public NewsArticles() {
    }


    public String getWriterEmail() {
        return writerEmail;
    }

    public void setWriterEmail(String writerEmail) {
        this.writerEmail = writerEmail;
    }

    public NewsArticles(String images, String name, String body, String categoies, String writerEmail) {
        this.images = images;
        this.name = name;
        this.body = body;
        this.categoies = categoies;
        this.writerEmail = writerEmail;
    }

    @PropertyName("images")
    public String getImages() {
        return images;
    }

    @PropertyName("images")
    public void setImages(String images) {
        this.images = images;
    }

    public String getCategoies() {
        return categoies;
    }

    public void setCategoies(String categoies) {
        this.categoies = categoies;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}

