package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.FetchPlaceRequest;
import com.google.android.libraries.places.api.net.PlacesClient;

import java.util.Arrays;
import java.util.List;

public class MapsAllViewFragment extends Fragment {

    GoogleMap map;

    private AdvertViewModel advertViewModel;

    PlacesClient placesClient;

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

//            final List<Place.Field> placeFields =
//                Arrays.asList(
//                        Place.Field.ID,
//                        Place.Field.DISPLAY_NAME,
//                        Place.Field.FORMATTED_ADDRESS,
//                        Place.Field.LOCATION
//                );
//
//                int markerCount = advertViewModel.getRowCount();
//                Log.d("MAP", "Marker Count: " + String.valueOf(markerCount));
//
//                // maybe I need an observer? which will be annoying, trust
//                // https://www.youtube.com/watch?v=r-OoaF9tJCg&list=PL7NYbSE8uaBCSkZum6Z88RvjiXrTBpjT2&index=3
//
//                // Attempt to invoke get on null object reference.
//                if(markerCount > 0) {
//                for(int i = 0; i < markerCount; i++) {
//                    // Get the place from the placeID saved in the RoomDB
//                    try {
//                        AdvertEntity advert = advertViewModel.getAllAdvertsMapList().get(i);
//
//                        final FetchPlaceRequest request =
//                                FetchPlaceRequest.newInstance(advert.advertPlaceID, placeFields);
//
//                        placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
//                            Place place = response.getPlace();
//                            Log.d("MAP", "Place found: " + place.getDisplayName());
//                            LatLng latLng = place.getLocation();
//                            Log.d("MAP", "Lat: " + latLng.latitude + " Long: " + latLng.longitude);
//                            map.addMarker(new MarkerOptions().position(latLng).title(advert.advertTitle));
//                        }).addOnFailureListener((exception) -> {
//                            Log.e("MAP", "Place not found: " + exception.getMessage());
//                        });
//                    } catch (Exception e) {
//                        Log.d("MAP", "No entry at position: " + i + " - " + e);
//                    }
//
//                }
//            }

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

        // NOW!! in theory. we can alter populateMap() to take a list of adverts as a variable
        // which it will then iterate through, with a for loop, in order to get The Shit?
        // I don't know if we need to filter the markers through the room db or through the api.
        // probably the API right?
        populateMap();

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

    public void populateMap() {
        final List<Place.Field> placeFields =
                Arrays.asList(
                        Place.Field.ID,
                        Place.Field.DISPLAY_NAME,
                        Place.Field.FORMATTED_ADDRESS,
                        Place.Field.LOCATION
                );

        int markerCount = advertViewModel.getRowCount();
        Log.d("MAP", "Marker Count: " + String.valueOf(markerCount));

        if(markerCount > 0) {
            for(int i = 0; i < markerCount; i++) {
                // Get the place from the placeID saved in the RoomDB
                try {
                    AdvertEntity advert = advertViewModel.getAllAdvertsMapList().get(i);

                    final FetchPlaceRequest request =
                            FetchPlaceRequest.newInstance(advert.advertPlaceID, placeFields);

                    placesClient.fetchPlace(request).addOnSuccessListener((response) -> {
                        Place place = response.getPlace();
                        Log.d("MAP", "Place found: " + place.getDisplayName());
                        LatLng latLng = place.getLocation();
                        Log.d("MAP", "Lat: " + latLng.latitude + " Long: " + latLng.longitude);

                        // Adding the marker
                        map.addMarker(new MarkerOptions().position(latLng).title(advert.advertTitle).snippet(advert.advertDescription));
                    }).addOnFailureListener((exception) -> {
                        Log.e("MAP", "Place not found: " + exception.getMessage());
                    });
                } catch (Exception e) {
                    Log.d("MAP", "No entry at position: " + i + " - " + e);
                }

            }
        }
    }
}

// I now have to figure out how to import the LatLngs from the db (stored as two DECIMALS?)
// And get that to populate. boy howdy. we can only imagine.
// In theory its just in OnMapReady doing a... for loop? for the length of the whole db right
// luckily the uid and the index are diff things lmao
// Basically for loop, extract the decimals, convert to LatLng, add marker, continue until the
// whole array is done
// and then uh. Fuck around!!! Find out!!
// I do also have to figure out how to uh. get this bitch to WORK right
// inputting the LatLng. in theory i can import a fragment into the scrollview of NewAdvertFragment
// and then extract the LatLng from it. select. store.
// sep from the Location which is a text summary. oh i gotta put the maps in the damn views too
// christ theres 15 maps
// This one (MapsAllView), new advert (MapsAdd) and view fragment (MapSingleView)