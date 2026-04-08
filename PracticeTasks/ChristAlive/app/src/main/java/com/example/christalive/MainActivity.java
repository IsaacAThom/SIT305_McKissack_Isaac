package com.example.christalive;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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