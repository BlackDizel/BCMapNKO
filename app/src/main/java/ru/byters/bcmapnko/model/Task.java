package ru.byters.bcmapnko.model;

import java.io.Serializable;
import java.util.Calendar;

public class Task implements Serializable {
    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private Calendar calendar;

    public Task(String title, String description, double latitude, double longitude, Calendar cal) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.calendar = cal;
    }

    public Calendar getCalendar() {
        return calendar;
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
