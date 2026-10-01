package com.project;

public class Game {
    private String id;
    private String title;
    private String description;
    private String imagePath;

    public Game() {}

    public Game(String id, String title, String description, String imagePath) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
    }

    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }

    @Override
    public String toString() {
        return title;
    }
}
