package com.example.personaleventplanner;

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

        // Collecting user input for Date here - initialise as today as well.
        Calendar eventDate = Calendar.getInstance();

        // Initialize DatePicker with the current date
        datePicker.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int month, int day) {
                        // Update eventDate
                        eventDate.set(year, month, day);

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
                else if(eventDate.before(today)) {
                    Toast.makeText(thisFragmentView.getContext(), "Error: Event Date is in the past",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    // Retrieve all data from form
                    String eventTitle = String.valueOf(editEventTitle.getText());
                    String eventLocation = String.valueOf(editEventLocation.getText());
                    String eventCategory = String.valueOf(editEventCategory.getText());

                    // Convert from Calendar to Date for storage
                    Date setDate = eventDate.getTime();

                    newEvent = new EventEntity(eventTitle, setDate, eventLocation, eventCategory);

                    eventViewModel.insert(newEvent);

                    Toast.makeText(thisFragmentView.getContext(), "Event Added",
                            Toast.LENGTH_LONG).show();
                }
            }
        });

        return thisFragmentView;
    }
}