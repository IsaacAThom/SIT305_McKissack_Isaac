package com.example.christalive;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class NavBottom extends Fragment {

    public MainActivity mainActivity;

    public NavBottom() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View thisFragmentView = inflater.inflate(R.layout.fragment_nav_bottom, container, false);

        mainActivity = (MainActivity) getActivity();

        // Buttons for each navigator
        Button eventListButton = thisFragmentView.findViewById(R.id.goToEventListButton);

        Button addEventButton = thisFragmentView.findViewById(R.id.goToAddListButton);

        // If eventListButton clicked, show EventListFragment
        eventListButton.setOnClickListener(
                view -> mainActivity.showEventList());

        addEventButton.setOnClickListener(view -> mainActivity.showAddEvent());

        return thisFragmentView;
    }
}