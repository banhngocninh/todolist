package com.example.todolist.mclass;

import java.util.Calendar;

public class Task {
    private String id;
    private String name;
    private Calendar time;
    private int icon;
    private int rating;
    private String type;

    public Task(String id, String name, Calendar time, int icon, int rating, String type) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.icon = icon;
        this.rating = rating;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Calendar getTime() {
        return time;
    }

    public void setTime(Calendar time) {
        this.time = time;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
