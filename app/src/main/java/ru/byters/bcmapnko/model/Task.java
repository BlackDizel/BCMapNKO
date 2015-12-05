package ru.byters.bcmapnko.model;

import java.io.Serializable;

public class Task implements Serializable {
    private String title;
    private String description;
    private double latitude;
    private double longitude;

    public Task(String title, String description, double latitude, double longitude) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}
