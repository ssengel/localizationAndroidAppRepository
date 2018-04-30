package com.example.ssengel.loginapp01.Model;

/**
 * Created by ssengel on 29.04.2018.
 */

public class Notification {
    private String id;
    private String title;
    private String description;
    private String location;
    private Boolean isVisited;

    public Notification() {
        isVisited = false;
    }

    public Notification(String id, String title, String description, String location, Boolean isVisited) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.location = location;
        this.isVisited = isVisited;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Boolean getVisited() {
        return isVisited;
    }

    public void setVisited(Boolean visited) {
        isVisited = visited;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", isVisited=" + isVisited +
                '}';
    }
}
