package com.example.malariaearlywarningsystemmews.classes;

import android.media.Image;

import java.util.Date;

public class ExtremeEvents {

    private String eventDescription;
//    private Image eventImage;
//    private String eventLocation;
//    private Date eventDate;

    public ExtremeEvents() {
    }

    public ExtremeEvents(String eventDescription/*, Image eventImage, String eventLocation, Date eventDate*/) {
        this.eventDescription = eventDescription;
//        this.eventImage = eventImage;
//        this.eventLocation = eventLocation;
//        this.eventDate = eventDate;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

//    public Image getEventImage() {
//        return eventImage;
//    }
//
//    public void setEventImage(Image eventImage) {
//        this.eventImage = eventImage;
//    }
//
//    public String getEventLocation() {
//        return eventLocation;
//    }
//
//    public void setEventLocation(String eventLocation) {
//        this.eventLocation = eventLocation;
//    }
//
//    public Date getEventDate() {
//        return eventDate;
//    }
//
//    public void setEventDate(Date eventDate) {
//        this.eventDate = eventDate;
//    }
}
