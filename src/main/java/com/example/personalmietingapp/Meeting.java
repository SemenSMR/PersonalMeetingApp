package com.example.personalmietingapp;

import java.io.Serializable;
import java.time.LocalDateTime;


public class Meeting implements Serializable {
    private static final long serialVersionUID = 1L;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int reminderMinutesBefore;

    @Override
    public String toString() {
        return String.format("Title: %s, Start Time: %s, End Time: %s, Reminder: %d minutes before",
                title, startTime, endTime, reminderMinutesBefore);
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public int getReminderMinutesBefore() {
        return reminderMinutesBefore;
    }

    public Meeting(String title, LocalDateTime startTime, LocalDateTime endTime, int reminderMinutesBefore) {
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.reminderMinutesBefore = reminderMinutesBefore;
    }
}
