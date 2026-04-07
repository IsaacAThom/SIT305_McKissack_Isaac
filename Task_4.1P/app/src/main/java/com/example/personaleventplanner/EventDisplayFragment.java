package com.example.personaleventplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class EventDisplayFragment extends Fragment {

    public EventDisplayFragment() {
        // Required empty public constructor
    }

    private EventViewModel eventViewModel;

    RecyclerView recyclerView;

    RVAdapter adapter;

    // Room DB?
    // https://developer.android.com/codelabs/android-room-with-a-view#0 < SEE THIS. FOLLOW THAT!

    // These will later be Room based, I'd hope
    ArrayList<String> arrayListtitles = new ArrayList<>();
    ArrayList<String> arrayListdates = new ArrayList<>();
    ArrayList<String> arrayListlocation = new ArrayList<>();
    ArrayList<String> arrayListcategories = new ArrayList<>();

    Date dummyDate = new Date();
    String myString = DateFormat.getDateInstance().format(dummyDate);

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_event_display, container, false);

        //recycler_view = recycler_view.findViewById(R.id.event_list_recycler);

//        recycler_view = rootView.findViewById(R.id.event_list_recycler);
//
//        adapter = new RVAdapter(arrayListtitles, arrayListdates, arrayListlocation,
//                arrayListcategories);
//
//        recycler_view.setAdapter(adapter);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
//                LinearLayoutManager.VERTICAL, false);
//        recycler_view.setLayoutManager(layoutManager);

        // Instantiate evm
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        recyclerView = rootView.findViewById(R.id.event_list_recycler);
        final EventListAdapter adapter = new EventListAdapter(new EventListAdapter.EventDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


//        // Create dummy data for testing
//        for (int i = 1; i <= 20; i++) {
//            arrayListtitles.add("Title " + i);
//            arrayListdates.add(myString + " " + i);
//            arrayListlocation.add("Location " + i);
//            arrayListcategories.add("Category " + i);
//            adapter.notifyDataSetChanged();
//        }

        // debug mode ENGAGE
        Button testButton = rootView.findViewById(R.id.test_button_bravo);

        //String testGo = eventViewModel.getAllEvents().getValue()).toString();

        testButton.setOnClickListener( view -> {

                    EventEntity newEvent = new EventEntity();
                    newEvent.eventTitle = "Test Name";
                    newEvent.eventDate = Calendar.getInstance();
                    newEvent.eventLocation = "Test Location";
                    newEvent.eventCategory = "Test Category";

                    eventViewModel.insert(newEvent);

                if (eventViewModel.getAllEvents().getValue() == null) {
                    Toast.makeText(rootView.getContext(), "fuck", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(rootView.getContext(), "cool???", Toast.LENGTH_SHORT).show();
                }
        }
        );

        return rootView;
            }
}