package com.cs407.badgertee;

public class Hole {
    private int holeNumber;
    private double latitude;
    private double longitude;

    public Hole(int holeNumber, double latitude, double longitude) {
        this.holeNumber = holeNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public int getHoleNumber() {
        return holeNumber;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }
}

