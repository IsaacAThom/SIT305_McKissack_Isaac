package com.example.lostandfound;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

public class AdvertListAdapter extends ListAdapter<AdvertEntity, AdvertViewHolder> {

    public AdvertListAdapter(@NonNull DiffUtil.ItemCallback<AdvertEntity> diffCallback) {
        super(diffCallback);
    }

    @Override
    public AdvertViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return AdvertViewHolder.create(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull AdvertViewHolder holder, int position) {
        AdvertEntity current = getItem(position);
        holder.bind(current);
    }

    static class EventDiff extends DiffUtil.ItemCallback<AdvertEntity> {

        @Override
        public boolean areItemsTheSame(@NonNull AdvertEntity oldItem, @NonNull AdvertEntity newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull AdvertEntity oldItem, @NonNull AdvertEntity newItem) {
            return oldItem.advertTitle.equals(newItem.advertTitle);
        }
    }
}
