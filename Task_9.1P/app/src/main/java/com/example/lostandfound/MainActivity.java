package com.example.lostandfound;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.net.PlacesClient;

public class MainActivity extends AppCompatActivity {

    String apiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        apiKey = getString(R.string.google_maps_api);

        if (!Places.isInitialized()) {
            // Initialize the SDK
            Places.initializeWithNewPlacesApiEnabled(getApplicationContext(), apiKey);
        }

        // Create new Places client instance
        PlacesClient placesClient = Places.createClient(this);
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

    // Will need a new button to summon the Map View (god have mercy)
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
}