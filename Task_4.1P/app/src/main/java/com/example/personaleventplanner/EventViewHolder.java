package com.example.personaleventplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Calendar;

// Contains the display of the Events
public class EventViewHolder extends RecyclerView.ViewHolder {
    private final TextView eventTitleView, eventDateView, eventLocationView, eventCategoryView;

    private EventViewHolder(View itemView) {
        super(itemView);
        eventTitleView = itemView.findViewById(R.id.tv_event_title);
        eventDateView = itemView.findViewById(R.id.tv_event_date);
        eventLocationView = itemView.findViewById(R.id.tv_event_location);
        eventCategoryView = itemView.findViewById(R.id.tv_event_category);
    }

    public void bind(String title, Calendar date, String location, String category) {
        eventTitleView.setText(title);
        eventDateView.setText(date.toString());
        eventLocationView.setText(location);
        eventCategoryView.setText(category);
    }

    static EventViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_event, parent,
                false);
        return new EventViewHolder(view);
    }


//    public EventViewHolder(@NonNull View itemView) {
//        super(itemView);
//    }
}
