package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;

public class RangeFinder extends AppCompatActivity {

    private TextView holeNumber, yardage;
    private ImageView imgArrow;
    private Button btnNextHole;
    private int currentHole = 1;

    private LocationManager locationManager;
    private Location userLocation;

    private Hole[] holes = new Hole[]{
            new Hole(1, 34.000123, -118.000456), // Replace with actual coordinates
            new Hole(2, 34.001234, -118.001567),
            new Hole(3, 34.001234, -118.001567),
            new Hole(4, 34.001234, -118.001567),
            new Hole(5, 34.001234, -118.001567),
            new Hole(6, 34.001234, -118.001567),
            new Hole(7, 34.001234, -118.001567),
            new Hole(8, 34.001234, -118.001567),
            new Hole(9, 34.001234, -118.001567),
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_finder);

        holeNumber = findViewById(R.id.holeNumber);
        yardage = findViewById(R.id.yardage);
        imgArrow = findViewById(R.id.imgArrow);
        btnNextHole = findViewById(R.id.btnNextHole);

        // Setup LocationManager
/*
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        try {
         //   locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, this);
        } catch (SecurityException e) {
            // Handle the exception
        }

        btnNextHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextHole();
            }
        });

        updateView();

*/
    }
  //  @Override
    public void onLocationChanged(Location location) {
        userLocation = location;
        updateView();
    }

    private void goToNextHole() {
        currentHole++;
        if (currentHole > holes.length) {
            currentHole = 1; // Loop back to the first hole
        }
        updateView();
    }

    private void updateView() {
        // Update the hole number
        holeNumber.setText("Hole: " + currentHole);

        // Calculate bearing
        Hole currentHoleObj = holes[currentHole - 1];
        double userLat = userLocation.getLatitude() ;
        double userLng = userLocation.getLongitude();
        float bearing = calculateBearing(userLat, userLng, currentHoleObj.getLatitude(), currentHoleObj.getLongitude());

        yardage.setText("Distance: " + calculateDistance() + "m");

        //imgArrow.setRotation(bearing - userHeading);
    }

    private float calculateBearing(double userLat, double userLng, double holeLat, double holeLng) {
        double longitude1 = userLng;
        double longitude2 = holeLng;
        double latitude1 = Math.toRadians(userLat);
        double latitude2 = Math.toRadians(holeLat);
        double longDiff = Math.toRadians(longitude2 - longitude1);
        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

        return (float)(Math.toDegrees(Math.atan2(y, x)) + 360) % 360;
    }

    private int calculateDistance() {
        return 0; // Placeholder
    }
}
