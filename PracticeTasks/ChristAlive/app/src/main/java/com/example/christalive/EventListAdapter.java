package com.example.christalive;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class EventListAdapter extends ListAdapter<EventEntity, EventViewHolder> {

    public EventListAdapter(@NonNull DiffUtil.ItemCallback<EventEntity> diffCallback) {
        super(diffCallback);
    }

    @Override
    public EventViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return EventViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(EventViewHolder holder, int position) {
        EventEntity current = getItem(position);
//        holder.bind(current.eventTitle, current.eventDate, current.eventLocation, current.eventCategory);
        holder.bind(current);
    }

    static class EventDiff extends DiffUtil.ItemCallback<EventEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull EventEntity oldItem, @NonNull EventEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull EventEntity oldItem, @NonNull EventEntity newItem) {
            return oldItem.eventTitle.equals(newItem.eventTitle);
        }
    }
}
