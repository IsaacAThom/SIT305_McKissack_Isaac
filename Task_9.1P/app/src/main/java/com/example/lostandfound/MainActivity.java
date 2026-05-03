package com.example.lostandfound;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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