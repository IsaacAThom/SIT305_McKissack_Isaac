package com.example.personaleventplanner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class EventDisplayFragment extends Fragment {

    public EventDisplayFragment() {
        // Required empty public constructor
    }

    RecyclerView recycler_view;

    RVAdapter adapter;

    // These will later be Room based, I'd hope
    ArrayList<String> arrayListtitles = new ArrayList<>();
    ArrayList<String> arrayListdates = new ArrayList<>();
    ArrayList<String> arrayListlocation = new ArrayList<>();
    ArrayList<String> arrayListcategories = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_event_display, container, false);

        //recycler_view = recycler_view.findViewById(R.id.event_list_recycler);

        recycler_view = (RecyclerView) rootView.findViewById(R.id.event_list_recycler);

        adapter = new RVAdapter(arrayListtitles, arrayListdates, arrayListlocation,
                arrayListcategories);

        recycler_view.setAdapter(adapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(),
                LinearLayoutManager.VERTICAL, false);
        recycler_view.setLayoutManager(layoutManager);


        // Create dummy data for testing
        for (int i = 1; i <= 20; i++) {
            arrayListtitles.add("Title " + i);
            arrayListdates.add("Monday " + i);
            arrayListlocation.add("Location " + i);
            arrayListcategories.add("Category " + i);
            adapter.notifyDataSetChanged();
        }

        return rootView;
            }
}