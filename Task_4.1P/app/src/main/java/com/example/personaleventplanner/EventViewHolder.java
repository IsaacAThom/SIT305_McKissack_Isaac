package com.example.personaleventplanner;

import android.app.Application;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;

// This one is a mess
class EventViewHolder extends RecyclerView.ViewHolder implements RecyclerViewInterface {
    private final TextView eventTitleView, eventDateView, eventLocationView, eventCategoryView;
    private final ImageButton deleteEvent, editEvent;

    private EventRepository eventRepository;

    private MainActivity mainActivity;

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

        mainActivity = (MainActivity) itemView.getContext();
    }

    public void bind(EventEntity eventEntity) {

        // Converting date input to something readable
        String dateString = sdf.format(eventEntity.eventDate.getTime());

        // Pass all info from the event to the display
        eventTitleView.setText(eventEntity.eventTitle);
        eventDateView.setText(dateString);
        eventLocationView.setText(eventEntity.eventLocation);
        eventCategoryView.setText(eventEntity.eventCategory);

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

    // OnClickListeners incorporating the methods from RecyclerViewInterface
    @Override
    public void deleteEventOnClick(int position) {
        eventRepository.deleteEvent(position);
        Toast.makeText(itemView.getContext(), "Event Deleted",Toast.LENGTH_LONG).show();
    }

    @Override
    public void editEventOnClick(int position) {
        // Calling function from mainActivity to show Edit Page in main fragmentView
        mainActivity.showEditEvent(position);
    }
}
