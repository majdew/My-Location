package com.example.mylocation;

import java.util.Date;

public class Location {
    private  double latitude;
    private  double longitude;
    private String title;
    private String locationDescription;
    private String visitingDate;

    public Location(double latitude, double longitude, String title, String locationDescription, String visitingDate) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.locationDescription = locationDescription;
        this.visitingDate = visitingDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocationDescription() {
        return locationDescription;
    }

    public void setLocationDescription(String locationDescription) {
        this.locationDescription = locationDescription;
    }

    public String getVisitingDate() {
        return visitingDate;
    }

    public void setVisitingDate(String visitingDate) {
        this.visitingDate = visitingDate;
    }
}
