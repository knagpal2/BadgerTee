package com.cs407.badgertee;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Button;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Context;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;


public class RangeFinder extends AppCompatActivity {

    private TextView holeNumber, yardage, parText;
    private ImageView imgArrow;
    private Button btnNextHole;

    private int currentHole = 1;

    private LocationManager locationManager;

    private LocationListener locationListener;
    private Location userLocation;

    private Spinner spinnerItemCount;





    private Hole[] holes = new Hole[]{
            new Hole(1, 43.083703, -89.54575, 5), // Replace with actual coordinates
            new Hole(2, 43.082616, -89.544378, 3),
            new Hole(3, 43.082830, -89.549979, 5),
            new Hole(4, 43.085268, -89.550965, 4),
            new Hole(5, 43.086088, -89.549152, 3),
            new Hole(6, 43.087438, -89.546330, 4),
            new Hole(7, 43.086564, -89.550796, 4),
            new Hole(8, 43.087703, -89.549876,3),
            new Hole(9, 43.087919, -89.544376, 5),
    };

    private HashMap<String, ArrayList<Integer>> playerScores = new HashMap<>();


    public void startListening(){

        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_range_finder);

        Intent intent = getIntent();
        String selectedPlayerOption = intent.getStringExtra("selectedPlayerOption");
        String selectedGameTypeOption = intent.getStringExtra("selectedGameTypeOption");

        spinnerItemCount = findViewById(R.id.spinnerItemCount);
        setupItemCountSpinner(selectedPlayerOption);

        holeNumber = findViewById(R.id.holeNumber);
        yardage = findViewById(R.id.yardage);
        imgArrow = findViewById(R.id.imgArrow);
        btnNextHole = findViewById(R.id.btnNextHole);
        parText = findViewById(R.id.parText);



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

        parText.setText("Par " + currentHoleObj.getHolePar());
        yardage.setText("Distance: " + calculateDistance() + " yards");
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

    private int calculateDistance() {
        Location holeLocation = new Location("");
        Hole currentHoleObj = holes[currentHole - 1];
        holeLocation.setLatitude(currentHoleObj.getLatitude());
        holeLocation.setLongitude(currentHoleObj.getLongitude());
        float distanceInMeters = userLocation.distanceTo(holeLocation);
        return (int) (distanceInMeters * 1.09361);
    }


    private void setupItemCountSpinner(String selectedPlayerOption) {
        List<String> itemCountList = new ArrayList<>();
        int maxItems = Integer.parseInt(selectedPlayerOption);
        for (int i = 1; i <= maxItems; i++) {
            String playerName = "Player " + i;
            itemCountList.add(playerName);
            playerScores.put(playerName, new ArrayList<>());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_item,
                itemCountList
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerItemCount.setAdapter(adapter);
    }

    public void onScoreSubmit(View view) {
        String selectedPlayer = spinnerItemCount.getSelectedItem().toString();
        EditText scoreInput = findViewById(R.id.scoreInput);
        int score;
        try {
            score = Integer.parseInt(scoreInput.getText().toString());
        } catch (NumberFormatException e) {
            Toast.makeText(this, "Please enter a valid score", Toast.LENGTH_SHORT).show();
            return;
        }

        ArrayList<Integer> scores = playerScores.get(selectedPlayer);
        if (scores != null) {
            scores.add(score);
            playerScores.put(selectedPlayer, scores);
            Toast.makeText(this, "Score recorded!", Toast.LENGTH_SHORT).show();
            int nextPlayerPosition = spinnerItemCount.getSelectedItemPosition() + 1;
            if (nextPlayerPosition >= spinnerItemCount.getCount()) {
                nextPlayerPosition = 0;
            }
            spinnerItemCount.setSelection(nextPlayerPosition);

        }
        scoreInput.setText(""); // Clear the input field
        Log.i("Important", String.valueOf(playerScores));
    }

}
