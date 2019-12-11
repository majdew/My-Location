package com.example.mylocation;

import java.util.ArrayList;


public class LocationObject {
    private  double latitude;
    private  double longitude;
    private String title;
    private String locationDescription;
    private ArrayList<String> visitingDate;
    private int id ;
    private byte [] locationImageBytes;

    public LocationObject(int id , double latitude, double longitude, String title, String locationDescription , byte [] locationImageBytes) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.locationDescription = locationDescription;
        this.locationImageBytes = locationImageBytes;
    }
    public LocationObject( double latitude, double longitude, String title, String locationDescription , byte [] locationImageBytes){
        this.latitude = latitude;
        this.longitude = longitude;
        this.title = title;
        this.locationDescription = locationDescription;
        this.locationImageBytes = locationImageBytes;
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

    public ArrayList<String> getVisitingDate() {
        return visitingDate;
    }

    public void setVisitingDate(ArrayList <String> visitingDate) {
        this.visitingDate = visitingDate;
    }

    public int getId() {
        return id;
    }

    public byte[] getLocationImageBytes() {
        return locationImageBytes;
    }

    public void setLocationImageBytes(byte[] locationImageBytes) {
        this.locationImageBytes = locationImageBytes;
    }
}
