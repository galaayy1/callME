package com.example.callme;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import android.view.View;
import android.content.Intent;
import android.net.Uri;



public class MainActivity extends FragmentActivity implements OnMapReadyCallback {

    private MapView mapView;
    private GoogleMap gMap;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the MapView
        mapView = findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);

        // Initialize the Maps SDK
        MapsInitializer.initialize(this);

        Button actionButton = findViewById(R.id.actionButton);

        // Set listener for Action Button
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showBottomSheetDialog();
            }
        });
    }

    // Method to show BottomSheetDialog
    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);

        // Inflate the layout for the BottomSheet
        View bottomSheetView = LayoutInflater.from(getApplicationContext())
                .inflate(R.layout.bottom_sheet_layout, null);  // null instead of finding from root view

        // Set button actions
        Button btnCallContact = bottomSheetView.findViewById(R.id.btnCallContact);
        Button btnAmbulance = bottomSheetView.findViewById(R.id.btnAmbulance);
        Button btnPolice = bottomSheetView.findViewById(R.id.btnPolice);
        Button btnFireFighter = bottomSheetView.findViewById(R.id.btnFireFighter);

        btnCallContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String contactNumber = "+62818405440"; // Add the contact number here
                String message = "Hello, this is a test message!"; // Message to send

                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("https://api.whatsapp.com/send?phone=" + contactNumber + "&text=" + Uri.encode(message)));
                startActivity(intent);
            }
        });

        btnAmbulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your logic for Ambulance
            }
        });

        btnPolice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your logic for Police
            }
        });

        btnFireFighter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Add your logic for Fire Fighter
            }
        });

        // Set the content of the bottom sheet dialog and show it
        bottomSheetDialog.setContentView(bottomSheetView);
        bottomSheetDialog.show();
    }




    // Method to check if WhatsApp is installed
    private boolean isAppInstalled() {
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        // Check for location permissions
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Request location permissions
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
            return;
        }

        // Enable the blue dot (real-time location)
        gMap.setMyLocationEnabled(true);

        // Move the camera to a default location (optional)
        LatLng defaultLocation = new LatLng(-6.200000, 106.816666); // Example: Jakarta
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(defaultLocation) // Set the center of the map
                .zoom(12)                // Set the zoom level
                .build();
        gMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                        ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    gMap.setMyLocationEnabled(true); // Enable the blue dot if permissions granted
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }
}
