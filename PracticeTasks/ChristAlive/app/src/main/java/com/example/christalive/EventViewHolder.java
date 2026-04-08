package com.example.christalive;

import android.app.Application;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

class EventViewHolder extends RecyclerView.ViewHolder implements RecyclerViewInterface {
    private final TextView eventTitleView, eventDateView, eventLocationView, eventCategoryView;
    private final ImageButton deleteEvent, editEvent;

    private EventRepository eventRepository;

    private final LiveData<List<EventEntity>> allEvents;

    // Format for displaying dates correctly for Users
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");

    // Hands all the data to the items
    private EventViewHolder(View itemView) {
        super(itemView);

        eventTitleView = itemView.findViewById(R.id.tv_event_title);
        eventDateView = itemView.findViewById(R.id.tv_event_date);
        eventLocationView = itemView.findViewById(R.id.tv_event_location);
        eventCategoryView = itemView.findViewById(R.id.tv_event_category);

        deleteEvent = itemView.findViewById(R.id.delete_event);
        editEvent = itemView.findViewById(R.id.edit_event);

        eventRepository = new EventRepository(new Application());
        allEvents = eventRepository.getAllEvents();
    }

    // public void bind(String title, Date date, String location, String category) {

    public void bind(EventEntity eventEntity) {
        // Converting date input to something readable
        String dateString = sdf.format(eventEntity.eventDate.getTime());

        eventTitleView.setText(eventEntity.eventTitle);
        eventDateView.setText(dateString);
        eventLocationView.setText(eventEntity.eventLocation);
        eventCategoryView.setText(eventEntity.eventCategory);

        //OnClick Listeners - can I pass this info forward to somewhere it can recieve the data?
        // Holder doesn't have access to entity data itself is the issue rn
        deleteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Delete Event
                Log.d("Holder", "Delete Event " + eventEntity.uid);
                deleteEventOnClick(eventEntity.uid);
            }
        });
        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Edit Event
                Log.d("Holder", "Edit Event" + eventEntity.uid);
                editEventOnClick(eventEntity.uid);
            }
        });
    }

    static EventViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void deleteEventOnClick(int position) {
        Log.d("Interface", "Delete Event " + position);
        eventRepository.deleteEvent(position);
    }

    @Override
    public void editEventOnClick(int position) {
        Log.d("Interface", "Edit Event " + position);

    }
}
