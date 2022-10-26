package com.example.malariaearlywarningsystemmews.classes;

import java.util.Date;

public class ObservedIndicators {

    String id;
    String indicator;
    String location;
    String description;
    String image;
    String dateCaptured;
    String status;
    String email;

    public ObservedIndicators() {
    }

    public ObservedIndicators(String id, String indicator, String location, String description, String image, String dateCaptured, String status, String email) {
        this.id = id;
        this.indicator = indicator;
        this.location = location;
        this.description = description;
        this.image = image;
        this.dateCaptured = dateCaptured;
        this.status = status;
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIndicator() {
        return indicator;
    }

    public void setIndicator(String indicator) {
        this.indicator = indicator;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDateCaptured() {
        return dateCaptured;
    }

    public void setDateCaptured(String dateCaptured) {
        this.dateCaptured = dateCaptured;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
