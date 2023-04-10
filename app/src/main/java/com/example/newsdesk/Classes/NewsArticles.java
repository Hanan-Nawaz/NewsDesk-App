package com.example.newsdesk.Classes;

public class NewsArticles {
    public String images, name, body;

    public NewsArticles() {
    }

    public NewsArticles(String images, String name, String body) {
        this.images = images;
        this.name = name;
        this.body = body;
    }

    public String getImages() {
        return images;
    }

    public void setImage(String images) {
        this.images = images;
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

