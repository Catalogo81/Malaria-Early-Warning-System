package com.example.malariaearlywarningsystemmews.classes;

import android.media.Image;

import java.util.Date;

public class Indicators
{
    private String ikDescription;
    //private Image ikImage;
    private String ikLocation;
    //private Date ikDate;
    private String ikDate;

    public Indicators(){}

    //public Indicators(String ikDescription) {
//        this.ikDescription = ikDescription;
//    }
        public Indicators(String ikDescription/*, Image ikImage*/, String ikLocation, String ikDate) {
        this.ikDescription = ikDescription;
        //this.ikImage = ikImage;
        this.ikLocation = ikLocation;
        this.ikDate = ikDate;
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
}
