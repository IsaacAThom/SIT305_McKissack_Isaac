package com.example.christalive;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class EditEventFragment extends Fragment {

    int position;

    private EventViewModel eventViewModel;
    private EventEntity eventEntity;


    public EditEventFragment(int position) {
        // Required empty public constructor
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View thisFragmentView = inflater.inflate(R.layout.fragment_add_event, container, false);

        // EventViewModel IN!!!
        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);

        // Date management
        DatePicker datePicker = thisFragmentView.findViewById(R.id.edit_event_date);

        // Today's date for data validation
        Calendar today = Calendar.getInstance();

        // Fetch data from the event to be updated
        eventEntity = eventViewModel.getEvent(position);

        // Pass current data to the textviews
        EditText editEventTitle = thisFragmentView.findViewById(R.id.edit_event_title);
        editEventTitle.setText(eventEntity.eventTitle);
        EditText editEventLocation = thisFragmentView.findViewById(R.id.edit_event_location);
        editEventLocation.setText(eventEntity.eventLocation);
        EditText editEventCategory = thisFragmentView.findViewById(R.id.edit_event_category);
        editEventCategory.setText(eventEntity.eventCategory);
            // Calendar separate because ough
        Calendar editEventDate = Calendar.getInstance();
        editEventDate.setTime(eventEntity.eventDate);
        datePicker.init(
                editEventDate.get(Calendar.YEAR),
                editEventDate.get(Calendar.MONTH),
                editEventDate.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int month, int day) {
                        // Update eventDate
                        editEventDate.set(year, month, day);
                    }
                }
        );

        Button saveEvent = thisFragmentView.findViewById(R.id.save_event);
        saveEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // If any of the text fields are empty, error pass
                if (TextUtils.isEmpty(editEventTitle.getText()) || TextUtils.isEmpty(editEventLocation.getText()) || TextUtils.isEmpty(editEventCategory.getText())) {
                    Toast.makeText(thisFragmentView.getContext(), "Error: Event Not Complete",
                            Toast.LENGTH_LONG).show();
                } else if(editEventDate.before(today)) {
                    Toast.makeText(thisFragmentView.getContext(), "Error: Event Date is in the past",
                            Toast.LENGTH_LONG).show();
                }
                else {
                    // Retrieve all data from form
                    String eventTitle = String.valueOf(editEventTitle.getText());
                    String eventLocation = String.valueOf(editEventLocation.getText());
                    String eventCategory = String.valueOf(editEventCategory.getText());

                    // Convert from Calendar to Date for storage
                    Date setDate = editEventDate.getTime();

                    // Update event!
                    eventEntity.eventTitle = eventTitle;
                    eventEntity.eventDate = setDate;
                    eventEntity.eventLocation = eventLocation;
                    eventEntity.eventCategory = eventCategory;

                    eventViewModel.update(eventEntity);

                    Toast.makeText(thisFragmentView.getContext(), "Event Updated",
                            Toast.LENGTH_LONG).show();

                }
            }
        });

        return thisFragmentView;
    }
}