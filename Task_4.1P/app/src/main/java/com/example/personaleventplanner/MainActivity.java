package com.example.personaleventplanner;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    List<Event> eventList;

    //Temp data for the event list
    String[] nameList = {"Picnic", "Study", "Holiday"};
    String[] dateList = {"Monday", "Sunday", "February 17th"};

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

        // Initialise Recycler
        recyclerView = findViewById(R.id.eventRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewAdapter = new RecyclerViewAdapter(eventList, this);
        recyclerView.setAdapter(recyclerViewAdapter);

        // Something has gone Horrifically Wrong. Implement the recycler stuff as shown in the
        // API reference and then go from there!

        // Set layout for recycler
        //recyclerView.setLayoutManager(new LinearLayoutManager(this));

//        for (int i = 0; i < nameList.length ; i++) {
//            Event event = new Event(i, nameList[i], dateList[i], "0", "0");
//            eventList.add(event);
//        }
    }

    // Create events with Title, Category, Location, Date/Time
        // Categories - Work, Social, Travel
    // View upcoming events in dashboard, sorted by date
        // Utilise a RecyclerView for storing the events? (5.2)
    // Update existing events
    // Delete existing events

    // Data must be stored locally, and persist when app closed/restarted
        // Use Room Database
        // https://developer.android.com/training/data-storage/room

    // Use Jetpack Navigation component (?????)
    // Nav bar to switch between Event Dashboard and Add Event
        // Use Fragments for smoother experience

    // Validation
        // Title and Date must not be empty
        // Date must be in the future
    // Use Snackbars/Toasts to confirm deletions or notify of errors
}