package com.cs407.badgertee;

public class Hole {
    private int holeNumber;
    private int holePar;
    private double latitude;
    private double longitude;

    public Hole(int holeNumber, double latitude, double longitude, int holePar) {
        this.holeNumber = holeNumber;
        this.latitude = latitude;
        this.longitude = longitude;
        this.holePar = holePar;
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
    public int getHolePar() {
        return holePar;
    }
}

