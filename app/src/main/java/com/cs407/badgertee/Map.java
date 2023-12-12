package com.cs407.badgertee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class Map extends AppCompatActivity {

    private final LatLng mDestinationLatLng = new LatLng(43.087755, -89.543044);
    private GoogleMap mMap;

    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION =12;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_map);

        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;

            displayMyLocation();
        });
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        //float zoom = 2;

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDestinationLatLng, zoom));

    }

    private void displayMyLocation(){
        int permission = ActivityCompat.checkSelfPermission(this.getApplicationContext(), Manifest.permission.ACCESS_FINE_LOCATION);
        if(permission == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }

        else{
            mFusedLocationProviderClient.getLastLocation()
                    .addOnCompleteListener(this, task -> {
                        Location mLastKnownLocation = task.getResult();
                        if(mLastKnownLocation != null){
                            moveCameraToCurrentLocation(new LatLng(43.087755, -89.543044));
                            double lastLat = mLastKnownLocation.getLatitude();
                            double lastLong = mLastKnownLocation.getLongitude();
                            LatLng lastLocation = new LatLng(lastLat, lastLong);
                            mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                            mMap.getUiSettings().setZoomControlsEnabled(true);
                            mMap.addMarker((new MarkerOptions().position(lastLocation).title("Current Location")));

                            //mMap.addMarker(43.087574, -89.540833);

                        }

            });
        }
    }


    private void moveCameraToCurrentLocation(LatLng latLng){
        float zoom = 15f;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, zoom));
    }
    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions,
                                           int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                displayMyLocation();
            }
        }
    }
}