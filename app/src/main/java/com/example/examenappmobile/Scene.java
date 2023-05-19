package com.example.examenappmobile;

public class Scene {
    String Title;
    String Content;
    int Image;

    public Scene(String title, String content, int image) {
        Title = title;
        Content = content;
        Image = image;
    }

    public String getTitle() {
        return Title;
    }

    public String getContent() {
        return Content;
    }

    public int getImage() {
        return Image;
    }
}
