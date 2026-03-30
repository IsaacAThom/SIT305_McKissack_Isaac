package com.example.personaleventplanner;

import java.util.Date;

public class Event {

    // This is where the info for the Events goes, to be used in Event Card and other shit probably
    // Name of Event

    private String eventName;
    // Date of Event
    private String eventDate;
    // Category of Event
    private String eventCategory;
    // Location of Event
    private String eventLocation;
    // id
    private int id;

    public Event(int id, String eventName, String eventDate, String eventCategory,
                 String eventLocation) {
        this.id = id;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventCategory = eventCategory;
        this.eventLocation = eventLocation;
    }

    public int getId() {
        return id;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventCategory() {
        return eventCategory;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEventName(String name) {
        this.eventName = name;
    }

    public void setEventDate(String date) {
        this.eventDate = date;
    }

    public void setEventCategory(String category) {
        this.eventCategory = category;
    }

    public void setEventLocation(String location) {
        this.eventLocation = location;
    }
}
