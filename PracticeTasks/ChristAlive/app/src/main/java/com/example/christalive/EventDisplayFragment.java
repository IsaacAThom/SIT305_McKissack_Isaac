package com.example.christalive;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventDisplayFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventDisplayFragment extends Fragment {

    public EventDisplayFragment() {
        // Required empty public constructor
    }

    private WordViewModel wordViewModel;
    private EventViewModel eventViewModel;

    RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_event_display, container, false);

        /*

        wordViewModel = new ViewModelProvider(this).get(WordViewModel.class);

        recyclerView = rootView.findViewById(R.id.event_list_recycler);
        final WordListAdapter adapter = new WordListAdapter(new WordListAdapter.WordDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        wordViewModel.getAllWords().observe(this, words -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(words);
        });

         */

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        recyclerView = rootView.findViewById(R.id.event_list_recycler);
        final EventListAdapter adapter = new EventListAdapter(new EventListAdapter.EventDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        eventViewModel.getAllEvents().observe(this, events -> {
            // Update the cached copy of the words in the adapter.
            adapter.submitList(events);
        });

        return rootView;
    }
}