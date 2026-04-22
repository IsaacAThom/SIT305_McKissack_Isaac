package com.example.lostandfound;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class LostFoundListFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    public LostFoundListFragment() {
        // Required empty public constructor
    }

    private AdvertViewModel advertViewModel;

    RecyclerView recyclerView;

    //Category spinner
    Spinner advertCategory;
    //Category selection
    int advertPos;

    Button applyFilterButton;

    Button clearFilterButton;

    final AdvertListAdapter listAdapter =
            new AdvertListAdapter(new AdvertListAdapter.EventDiff());

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View thisFragmentView = inflater.inflate(R.layout.fragment_lost_found_list, container,
                false);

        // Set up model and recylcer view to display database list
        advertViewModel = new ViewModelProvider(this).get(AdvertViewModel.class);

        recyclerView = thisFragmentView.findViewById(R.id.lost_found_list_recycler);
        recyclerView.setAdapter(listAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        advertViewModel.getAllAdverts().observe(getViewLifecycleOwner(), adverts -> {
            listAdapter.submitList(adverts);
        });

        // Set up spinner for category filtering
        // Probably need a button for clearing filter, and some layout stuff too
        advertCategory = thisFragmentView.findViewById(R.id.form_category_filter);

        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(
                thisFragmentView.getContext(), R.array.advert_category_array,
                android.R.layout.simple_spinner_item
        );
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        advertCategory.setAdapter(arrayAdapter);

        advertCategory.setOnItemSelectedListener(this);

        // Find buttons
        applyFilterButton = thisFragmentView.findViewById(R.id.form_filter_button);
        clearFilterButton = thisFragmentView.findViewById(R.id.form_clear_filter_button);

        // Listen for button clicks, apply/clear filters
        applyFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Swap the display based on pos
                switch (advertPos) {
                    // Electronics
                    case 0:
                        advertViewModel.getFilteredAdverts("Electronics").observe(getViewLifecycleOwner(),
                                adverts -> {
                                    listAdapter.submitList(adverts);
                                });
                        break;
                    // Pet
                    case 1:
                        advertViewModel.getFilteredAdverts("Pet").observe(getViewLifecycleOwner(),
                                adverts -> {
                                    listAdapter.submitList(adverts);
                                });
                        break;
                    // Wallet
                    case 2:
                        advertViewModel.getFilteredAdverts("Wallet").observe(getViewLifecycleOwner(),
                                adverts -> {
                                    listAdapter.submitList(adverts);
                                });
                        break;
                    // Keys
                    case 3:
                        advertViewModel.getFilteredAdverts("Keys").observe(getViewLifecycleOwner(),
                                adverts -> {
                                    listAdapter.submitList(adverts);
                                });
                        break;
                    // Valuables
                    case 4:
                        advertViewModel.getFilteredAdverts("Valuables").observe(getViewLifecycleOwner(),
                                adverts -> {
                                    listAdapter.submitList(adverts);
                                });
                        break;
                    // Other
                    case 5:
                        advertViewModel.getFilteredAdverts("Other").observe(getViewLifecycleOwner(),
                                adverts -> {
                                    listAdapter.submitList(adverts);
                                });
                        break;
                    default:
                        advertViewModel.getAllAdverts().observe(getViewLifecycleOwner(), adverts -> {
                            listAdapter.submitList(adverts);
                        });
                        break;
                }
            }
        });

        clearFilterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advertViewModel.getAllAdverts().observe(getViewLifecycleOwner(), adverts -> {
                    listAdapter.submitList(adverts);
                });
            }
        });

        // Inflate the layout for this fragment
        return thisFragmentView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        advertPos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}