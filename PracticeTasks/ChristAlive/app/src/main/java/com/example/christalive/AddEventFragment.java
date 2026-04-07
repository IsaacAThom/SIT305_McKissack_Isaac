package com.example.christalive;

import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class AddEventFragment extends Fragment {

    public AddEventFragment() {
        // Required empty public constructor
    }

    private EventViewModel eventViewModel;

    private WordDao eventDao;
    EventEntity newEvent;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View thisFragmentView = inflater.inflate(R.layout.fragment_add_event, container, false);

        // EventViewModel IN!!!
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        // Date management
        DatePicker datePicker = thisFragmentView.findViewById(R.id.edit_event_date);

        // Initialise today's date
        Calendar today = Calendar.getInstance();

        // Initialize DatePicker with the current date
        datePicker.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                // Debug content to ensure dates are selecting properly
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int month, int day) {
                        // Display selected date in Toast message
                        String msg = "You Selected: " + day + "/" + (month + 1) + "/" + year;
                        Toast.makeText(thisFragmentView.getContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Other Data collected from the form
        EditText editEventTitle = thisFragmentView.findViewById(R.id.edit_event_title);
        EditText editEventLocation = thisFragmentView.findViewById(R.id.edit_event_location);
        EditText editEventCategory = thisFragmentView.findViewById(R.id.edit_event_category);

        Button saveEvent = thisFragmentView.findViewById(R.id.save_event);
        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If any of the text fields are empty, error pass
                if (TextUtils.isEmpty(editEventTitle.getText()) || TextUtils.isEmpty(editEventLocation.getText()) || TextUtils.isEmpty(editEventCategory.getText())) {
                    Toast.makeText(thisFragmentView.getContext(), "Error: Event Not Complete",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    // Retrieve all data from form
                    String eventTitle = String.valueOf(editEventTitle.getText());
                    // Calendar Set Up
                    int day = datePicker.getDayOfMonth();
                    int month = datePicker.getMonth();
                    int year = datePicker.getYear();
                    Calendar eventDate = Calendar.getInstance();
                    eventDate.set(year, month, day);
                    String eventLocation = String.valueOf(editEventLocation.getText());
                    String eventCategory = String.valueOf(editEventCategory.getText());

                    newEvent = new EventEntity(eventTitle, eventDate, eventLocation, eventCategory);

                    // Debug text
                    Toast.makeText(thisFragmentView.getContext(),
                            newEvent.eventTitle + " " + newEvent.eventLocation + " " + newEvent.eventCategory + " " + datePicker.getDayOfMonth() + " " + datePicker.getMonth() + " " + datePicker.getYear() + " " + newEvent.eventDate,
                            Toast.LENGTH_LONG).show();

                    eventViewModel.insert(newEvent);
                }
            }
        });

        return thisFragmentView;
    }
}