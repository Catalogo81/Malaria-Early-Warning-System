package com.example.malariaearlywarningsystemmews.classes;

import android.media.Image;

import java.util.Date;

public abstract class Indicators
{
    private String ikDescription;
    //private Image ikImage;
    private String ikLocation;
    //private Date ikDate;
    private String ikDate;
    private String ikSeason;
    private String ikSignificance;
    private String ikRank;

    public Indicators(){}

    //public Indicators(String ikDescription) {
//        this.ikDescription = ikDescription;
//    }
        public Indicators(String ikDescription/*, Image ikImage*/, String ikLocation, String ikDate,
        String ikSeason, String ikSignificance, String ikRank) {
        this.ikDescription = ikDescription;
        //this.ikImage = ikImage;
        this.ikLocation = ikLocation;
        this.ikDate = ikDate;
        this.ikSeason = ikSeason;
        this.ikSignificance = ikSignificance;
        this.ikRank = ikRank;
    }

    public String getIkDescription() {
        return ikDescription;
    }

    public void setIkDescription(String ikDescription) {
        this.ikDescription = ikDescription;
    }

    /*public Image getIkImage() {
        return ikImage;
    }

    public void setIkImage(Image ikImage) {
        this.ikImage = ikImage;
    }*/

    public String getIkLocation() {
        return ikLocation;
    }

    public void setIkLocation(String ikLocation) {
        this.ikLocation = ikLocation;
    }

//    public Date getIkDate() {
//        return ikDate;
//    }
//
//    public void setIkDate(Date ikDate) {
//        this.ikDate = ikDate;
//    }


    public String getIkDate() {
        return ikDate;
    }

    public void setIkDate(String ikDate) {
        this.ikDate = ikDate;
    }

    public String getIkSeason() {
        return ikSeason;
    }

    public void setIkSeason(String ikSeason) {
        this.ikSeason = ikSeason;
    }

    public String getIkSignificance() {
        return ikSignificance;
    }

    public void setIkSignificance(String ikSignificance) {
        this.ikSignificance = ikSignificance;
    }

    public String getIkRank() {
        return ikRank;
    }

    public void setIkRank(String ikRank) {
        this.ikRank = ikRank;
    }
}
