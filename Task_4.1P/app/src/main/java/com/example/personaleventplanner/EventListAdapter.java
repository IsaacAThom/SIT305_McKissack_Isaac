package com.example.personaleventplanner;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import java.util.Objects;

public class EventListAdapter extends ListAdapter<EventEntity, EventViewHolder> {

    protected EventListAdapter(@NonNull DiffUtil.ItemCallback<EventEntity> diffCallback) {
        super(diffCallback);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return EventViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        EventEntity current = getItem(position);
        // vv that might be dodgy, the tutorial did NOT cover multi-variable entities
        holder.bind(current.eventTitle, current.eventDate, current.eventLocation, current.eventCategory);
    }

    static class EventDiff extends DiffUtil.ItemCallback<EventEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull EventEntity oldItem, @NonNull EventEntity newItem) {
            return oldItem.uid == newItem.uid;
        }

        @Override
        public boolean areContentsTheSame(@NonNull EventEntity oldItem, @NonNull EventEntity newItem) {
            return Objects.equals(oldItem.uid, newItem.uid);
        }
    }
}
