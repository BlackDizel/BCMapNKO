package ru.byters.bcmapnko.model;

import java.util.Calendar;

public class MarkeredTask extends Task {
    private String markerId;

    public MarkeredTask(String title, String description, double latitude, double longitude, Calendar cal, String contacts, String markerId) {
        super(title, description, latitude, longitude, cal, contacts);
        this.markerId = markerId;
    }

    public static MarkeredTask from(Task task, String markerId) {
        return new MarkeredTask(task.getTitle(), task.getDescription(), task.getLatitude()
                , task.getLongitude(), task.getCalendar(), task.getContacts(), markerId);
    }

    public String getMarkerId() {
        return markerId;
    }
}
