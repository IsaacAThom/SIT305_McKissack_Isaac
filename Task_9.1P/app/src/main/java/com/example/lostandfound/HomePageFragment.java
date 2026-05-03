package com.example.lostandfound;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment {

    public MainActivity mainActivity;

    public HomePageFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisFragmentView = inflater.inflate(R.layout.fragment_home_page, container, false);

        mainActivity = (MainActivity) getActivity();

        // Add buttons for each navigator
        Button addAdvertButton = thisFragmentView.findViewById(R.id.new_advert_button);

        Button lostAndFoundListButton = thisFragmentView.findViewById(R.id.show_list_button);

        addAdvertButton.setOnClickListener(view -> mainActivity.showNewAdvert());

        lostAndFoundListButton.setOnClickListener(view -> mainActivity.showLostAndFoundList());

        return thisFragmentView;
    }
}