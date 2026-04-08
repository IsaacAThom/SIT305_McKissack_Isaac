package com.example.personaleventplanner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class EventDisplayFragment extends Fragment {

    public EventDisplayFragment() {
        // Required empty public constructor
    }

    private EventViewModel eventViewModel;

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event_display, container, false);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        recyclerView = rootView.findViewById(R.id.event_list_recycler);
        final EventListAdapter adapter = new EventListAdapter(new EventListAdapter.EventDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        eventViewModel.getAllEvents().observe(getViewLifecycleOwner(), events -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(events);
        });

        return rootView;
    }
}