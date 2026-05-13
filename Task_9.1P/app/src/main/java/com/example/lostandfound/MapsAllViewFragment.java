package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;

public class MapsAllViewFragment extends Fragment {

    GoogleMap map;

    private AdvertViewModel advertViewModel;

    PlacesClient placesClient;

    MainActivity mainActivity;

    SwitchMaterial radiusSearchSwitch;

    private Location lastKnownLocation;

    private static final int DEFAULT_ZOOM = 15;

    // Sydney, for default camera location
    private final LatLng defaultLocation = new LatLng(-33.8523341, 151.2106085);

    // https://mapsplatform.google.com/resources/blog/how-calculate-distances-map-maps-javascript-api/
    // Variable for use in the radius calculations - Radius of the earth in KILOMETRES
    Integer EARTH_RADIUS = 6371;

    // Variable for search radius - defaults to 5
    Integer SEARCH_RADIUS = 5;

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {
            map = googleMap;

            updateLocationUI();

            getDeviceLocation();

            map.getUiSettings().setZoomControlsEnabled(true);
        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View thisFragmentView = inflater.inflate(R.layout.fragment_maps, container, false);

        advertViewModel = new ViewModelProvider(this).get(AdvertViewModel.class);

        placesClient = Places.createClient(getActivity().getApplicationContext());

        mainActivity = (MainActivity) getActivity();

        radiusSearchSwitch = thisFragmentView.findViewById(R.id.radius_search_switch);

        // Populate map with ALL markers
        populateMap(false);

        radiusSearchSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastKnownLocation == null) {
                    radiusSearchSwitch.setEnabled(false);
                    Toast.makeText(thisFragmentView.getContext(), "Radius search only possible if" +
                            " location permissions enabled.", Toast.LENGTH_LONG).show();
                }
            }
        });

        radiusSearchSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            // If switch checked, run radius search. If unchecked, display all markers again.
            populateMap(isChecked);
        });

        return thisFragmentView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }
    }

    public void populateMap(boolean radiusRequest) {
        final List<Place.Field> placeFields =
                Arrays.asList(
                        Place.Field.ID,
                        Place.Field.DISPLAY_NAME,
                        Place.Field.FORMATTED_ADDRESS,
                        Place.Field.LOCATION
                );

        // Clear map before population - accounts for updates + radius filter
        if (map != null) {
            map.clear();
        }

        int markerCount = advertViewModel.getRowCount();

        if(markerCount > 0) {
            for(int i = 0; i < markerCount; i++) {
                // Get the place from the placeID saved in the RoomDB
                try {
                    AdvertEntity advert = advertViewModel.getAllAdvertsMapList().get(i);

                    final FetchPlaceRequest request =
                            FetchPlaceRequest.newInstance(advert.advertPlaceID, placeFields);

                    placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                        Place place = response.getPlace();
                        LatLng latLng = place.getLocation();

                        // If radius searching, and location known (could be null even w/ perms)
                        if(radiusRequest && lastKnownLocation != null) {

                            double distance = haversine_distance(lastKnownLocation, place);

                            // Only add marker if w/i SEARCH_RADIUS
                            if (distance < SEARCH_RADIUS) {
                                map.addMarker(new MarkerOptions().position(latLng).title(advert.advertTitle).snippet(advert.advertDescription));
                            }

                        }
                        // if not radius searching, or if location not known, add ALL markers
                        else {
                            map.addMarker(new MarkerOptions().position(latLng).title(advert.advertTitle).snippet(advert.advertDescription));
                        }
                    }).addOnFailureListener((exception) -> {
                        Log.e("MAP", "Place not found: " + exception.getMessage());
                    });
                } catch (Exception e) {
                    Log.e("MAP", "No entry at position: " + i + " - " + e);
                }

            }
        }
    }

    // https://developers.google.com/maps/documentation/android-sdk/current-place-tutorial
    // Enables ui button for resetting camera to current location (if location permissions enabled)
    private void updateLocationUI() {
        if (map == null) {
            return;
        }
        try {
            if (mainActivity.getLocationPermissions()) {
                map.setMyLocationEnabled(true);
                map.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                map.setMyLocationEnabled(false);
                map.getUiSettings().setMyLocationButtonEnabled(false);
                lastKnownLocation = null;
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    // Get most recent location from device and use it to set the camera
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mainActivity.getLocationPermissions()) {
                // Using the fusedLocationClient initialised in mainActivity, which persists
                // between fragments better and should not return null pointer
                Task<Location> locationResult = mainActivity.mFusedLocationClient.getLastLocation();
                locationResult.addOnCompleteListener(getActivity(), new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            lastKnownLocation = task.getResult();
                            if (lastKnownLocation != null) {
                                LatLng currentLocation = new LatLng(lastKnownLocation.getLatitude(),
                                        lastKnownLocation.getLongitude());

                                map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                        currentLocation, DEFAULT_ZOOM));

                            }
                        } else {
                            Log.e("MAP", "Exception: %s", task.getException());
                            map.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                            map.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            } else {
                // Just set the default camera location if no permissions
                map.moveCamera(CameraUpdateFactory
                        .newLatLngZoom(defaultLocation, DEFAULT_ZOOM));
                map.getUiSettings().setMyLocationButtonEnabled(false);
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage(), e);
        }
    }

    // https://mapsplatform.google.com/resources/blog/how-calculate-distances-map-maps-javascript-api/
    // Calculates the distance between current location and marker place in KM
    double haversine_distance(Location mk1, Place mk2) {
        var rlat1 = mk1.getLatitude() * (Math.PI/180); // Convert degrees to radians
        var rlat2 = mk2.getLocation().latitude * (Math.PI/180); // Convert degrees to radians
        var difflat = rlat2-rlat1; // Radian difference (latitudes)
        var difflon = (mk2.getLocation().longitude-mk1.getLongitude()) * (Math.PI/180); // Radian difference
        // (longitudes)
        var d = 2 * EARTH_RADIUS * Math.asin(Math.sqrt(Math.sin(difflat/2)*Math.sin(difflat/2)+Math.cos(rlat1)*Math.cos(rlat2)*Math.sin(difflon/2)*Math.sin(difflon/2)));
        return d;
    }
}