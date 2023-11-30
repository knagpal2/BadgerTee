package com.cs407.badgertee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
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

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;


public class RangeFinder extends AppCompatActivity {

    private TextView holeNumber, yardage;
    private ImageView imgArrow;
    private Button btnNextHole;
    private int currentHole = 1;

    private LocationManager locationManager;

    private LocationListener locationListener;
    private Location userLocation;




    private Hole[] holes = new Hole[]{
            new Hole(1, 43.083703, -89.54575), // Replace with actual coordinates
            new Hole(2, 34.001234, -118.001567),
            new Hole(3, 34.001234, -118.001567),
            new Hole(4, 34.001234, -118.001567),
            new Hole(5, 34.001234, -118.001567),
            new Hole(6, 34.001234, -118.001567),
            new Hole(7, 34.001234, -118.001567),
            new Hole(8, 34.001234, -118.001567),
            new Hole(9, 34.001234, -118.001567),
    };

    public void startListening(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_finder);

        holeNumber = findViewById(R.id.holeNumber);
        yardage = findViewById(R.id.yardage);
        imgArrow = findViewById(R.id.imgArrow);
        btnNextHole = findViewById(R.id.btnNextHole);



        // Setup LocationManager

        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                updateLocationInfo(location);
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                LocationListener.super.onStatusChanged(provider, status, extras);
            }

            @Override
            public void onProviderEnabled(@NonNull String provider) {
                LocationListener.super.onProviderEnabled(provider);
            }

            @Override
            public void onProviderDisabled(@NonNull String provider) {
                LocationListener.super.onProviderDisabled(provider);
            }
        };

        if (Build.VERSION.SDK_INT < 23){
            startListening();
        } else {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null){
                    updateLocationInfo(location);
                }
            }
        }

        btnNextHole.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToNextHole();
            }
        });

        updateView();


    }
  //  @Override
    public void updateLocationInfo(Location location) {
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

        imgArrow.setRotation(bearing);
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

    private float calculateDistance() {
        Location holeLocation = new Location("");
        Hole currentHoleObj = holes[currentHole - 1];
        holeLocation.setLatitude(currentHoleObj.getLatitude());
        holeLocation.setLongitude(currentHoleObj.getLongitude());
        return userLocation.distanceTo(holeLocation);
    }
}
