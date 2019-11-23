package com.example.mylocation;

import java.util.Date;

public class LocationObject {
    private  double latitude;
    private  double longitude;
    private String title;
    private String locationDescription;
    private String visitingDate;
    private int id;

    public LocationObject(double latitude, double longitude, String title, String locationDescription) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.locationDescription = locationDescription;
        id++;
    }

    public LocationObject() {
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

    public int getId() {
        return id;
    }
}
