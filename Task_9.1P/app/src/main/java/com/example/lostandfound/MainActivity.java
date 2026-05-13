package com.example.lostandfound;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationRequest;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class MainActivity extends AppCompatActivity {

    String apiKey;

    // Permission for location services
    int PERMISSION_ID = 44;

    FusedLocationProviderClient mFusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiKey = getString(R.string.google_maps_api);

        if (!Places.isInitialized()) {
            // Initialize the SDK
            Places.initializeWithNewPlacesApiEnabled(getApplicationContext(), apiKey);
        }

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        getLocationPermissions();
    }

    public void showNewAdvert() {
        showNewFragmentInMainFragmentContainerView(new NewAdvertFragment());
    }

    public void showLostAndFoundList() {
        showNewFragmentInMainFragmentContainerView(new LostFoundListFragment());
    }

    // Has to pass position of entity for fetching
    public void showViewAdvert(int position) {
        showNewFragmentInMainFragmentContainerView(new ViewAdvertFragment(position));
    }

    // Will need a new button to summon the Map View
    public void showMapView() {
        showNewFragmentInMainFragmentContainerView(new MapsAllViewFragment());
    }

    // Pushes Fragments into the main display, and adds them to the backstack appropriately
    private void showNewFragmentInMainFragmentContainerView(Fragment newFragmentInstance) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Remove + add new fragment
        fragmentTransaction.replace(R.id.main_fragment_container, newFragmentInstance);
        // Adds previous fragment to backStack
        fragmentTransaction.addToBackStack(null);
        // Commit
        fragmentTransaction.commit();
    }

    // https://www.youtube.com/watch?v=mwzKYIB9cQs
    // Return true/false for use in other fragments
    boolean getLocationPermissions() {
        if(checkPermissions()) {
            if(isLocationEnabled()) {
                return true;
            }
            else {
                // Open settings to enable location
                Toast.makeText(this, "Turn on Location", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        }
        else {
            // Request permission

            requestPermission();
        }
        return false;
    }

    // Check if location permissions have been granted
    private boolean checkPermissions() {
        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this,
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    // Requests permissions
    private void requestPermission() {
        ActivityCompat.requestPermissions(
                this,
                new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                PERMISSION_ID
        );
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, int deviceId) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults, deviceId);

        if(requestCode == PERMISSION_ID) {
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Location Permissions Granted", Toast.LENGTH_SHORT).show();
                getLocationPermissions();
            }
            else {
                Toast.makeText(this, "Location Permissions Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}
