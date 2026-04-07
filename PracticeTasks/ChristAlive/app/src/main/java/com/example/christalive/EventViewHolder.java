package com.example.christalive;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

class EventViewHolder extends RecyclerView.ViewHolder {
    private final TextView eventTitleView, eventDateView, eventLocationView, eventCategoryView;

    // Format for displaying dates correctly for Users
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");

    // Hands all the data to the items
    private EventViewHolder(View itemView) {
        super(itemView);
        eventTitleView = itemView.findViewById(R.id.tv_event_title);
        eventDateView = itemView.findViewById(R.id.tv_event_date);
        eventLocationView = itemView.findViewById(R.id.tv_event_location);
        eventCategoryView = itemView.findViewById(R.id.tv_event_category);
    }

    public void bind(String title, Date date, String location, String category) {
        // Converting date input to something readable
        String dateString = sdf.format(date.getTime());

        eventTitleView.setText(title);
        eventDateView.setText(dateString);
        eventLocationView.setText(location);
        eventCategoryView.setText(category);
    }

    static EventViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.event_item, parent, false);
        return new EventViewHolder(view);
    }
}
