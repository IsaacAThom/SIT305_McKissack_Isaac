package com.example.personaleventplanner;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void showEventList() {
        showNewFragmentInMainFragmentContainerView(new EventDisplayFragment());
    }

    public void showAddEvent() {
        showNewFragmentInMainFragmentContainerView(new AddEventFragment());
    }

    // Has to pass position of entity for fetching
    public void showEditEvent(int position) {
        showNewFragmentInMainFragmentContainerView(new EditEventFragment(position));
    }

    // Pushes Fragments into the main display, and adds them to the backstack appropriately
    private void showNewFragmentInMainFragmentContainerView(Fragment newFragmentInstance) {
        FragmentManager fragmentManager = getSupportFragmentManager();

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        // Remove + add new fragment
        fragmentTransaction.replace(R.id.mainFragmentContainerView, newFragmentInstance);
        // Adds previous fragment to backStack
        fragmentTransaction.addToBackStack(null);
        // Commit
        fragmentTransaction.commit();
    }
}