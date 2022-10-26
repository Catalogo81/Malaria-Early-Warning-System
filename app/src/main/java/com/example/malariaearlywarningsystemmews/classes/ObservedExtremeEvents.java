package com.example.malariaearlywarningsystemmews.classes;

import android.media.Image;

import java.util.Date;

public class ObservedExtremeEvents {

    private String eventDescription;
    private String eventImage;
    private String eventLocation;
    private String eventDate;
    private String eventLevel;
    private String eventUserEmail;

    public ObservedExtremeEvents() {
    }

    public ObservedExtremeEvents(String eventDescription, String eventImage, String eventLocation, String eventDate, String eventLevel, String eventUserEmail) {
        this.eventDescription = eventDescription;
        this.eventImage = eventImage;
        this.eventLocation = eventLocation;
        this.eventDate = eventDate;
        this.eventLevel = eventLevel;
        this.eventUserEmail = eventUserEmail;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }

    public String getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(String eventLocation) {
        this.eventLocation = eventLocation;
    }

    public String getEventDate() {
        return eventDate;
    }

    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }

    public String getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(String eventLevel) {
        this.eventLevel = eventLevel;
    }

    public String getEventUserEmail() {
        return eventUserEmail;
    }

    public void setEventUserEmail(String eventUserEmail) {
        this.eventUserEmail = eventUserEmail;
    }
}
