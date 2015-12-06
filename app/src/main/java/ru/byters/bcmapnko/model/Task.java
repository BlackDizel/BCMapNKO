package ru.byters.bcmapnko.model;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Locale;

public class Task implements Serializable {
    private String title;
    private String description;
    private double latitude;
    private double longitude;
    private Calendar calendar;
    private String contacts;

    public Task(String title, String description, double latitude, double longitude, Calendar cal, String contacts) {
        this.title = title;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
        this.calendar = cal;
        this.contacts = contacts;
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

    public static String getDisplayedDate(Calendar date) {
        return String.format("%d %s %d"
                , date.get(Calendar.DAY_OF_MONTH)
                , date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault())
                , date.get(Calendar.YEAR));
    }

    public String getContacts() {
        return contacts;
    }
}
