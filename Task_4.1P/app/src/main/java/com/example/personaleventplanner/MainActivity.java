package com.example.personaleventplanner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // Create events with Title, Category, Location, Date/Time
        // Categories - Work, Social, Travel
    // View upcoming events in dashboard, sorted by date
    // Update existing events
    // Delete existing events

    // Data must be stored locally, and persist when app closed/restarted
        // Use Room Database

    // Use Jetpack Navigation component (?????)
    // Nav bar to switch between Event Dashboard and Add Event
        // Use Fragments for smoother experience

    // Validation
        // Title and Date must not be empty
        // Date must be in the future
    // Use Snackbars/Toasts to confirm deletions or notify of errors
}