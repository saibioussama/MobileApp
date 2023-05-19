package com.example.examenappmobile;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class Story implements Comparable<Story>{
    public int Id;
    public String Title;
    public String Description;
    public Boolean IsFavorite;
    public int Image;

    ArrayList<Scene> Scenes;

    public Story(int id,String title, String description, Boolean isFavorite, int image, ArrayList<Scene> scenes) {
        Id = id;
        Title = title;
        Description = description;
        IsFavorite = isFavorite;
        Image = image;
        Scenes = scenes;
        if(Scenes == null)
            Scenes = new ArrayList<>();
    }

    public String getTitle() {
        return Title;
    }

    public String getDescription() {
        return Description;
    }

    public Boolean getFavorite() {
        return IsFavorite;
    }

    public int getImage() {
        return Image;
    }

    public ArrayList<Scene> getScenes() {
        return Scenes;
    }

    public int getId() {
        return Id;
    }

    public static Story GetItemById(ArrayList<Story> stories, int id){
        for(int i=0; i<stories.size();i++)
        {
            Story story = stories.get(i);
            if(story.getId() == id)
                return story;
        }
        return null;
    }


    @Override
    public int compareTo(Story o) {
        return this.Id;
    }
}
