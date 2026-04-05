package com.example.personaleventplanner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.viewholder> {

    // These will later be Room based, I'd hope
    ArrayList<String> arrayListtitles = new ArrayList<>();
    ArrayList<String> arrayListdates = new ArrayList<>();
    ArrayList<String> arrayListlocation = new ArrayList<>();
    ArrayList<String> arrayListcategories = new ArrayList<>();

    public RVAdapter(ArrayList<String> arrayListtitles, ArrayList<String> arrayListdates,
                     ArrayList<String> arrayListlocation, ArrayList<String> arrayListcategories) {
        this.arrayListtitles = arrayListtitles;
        this.arrayListdates = arrayListdates;
        this.arrayListlocation = arrayListlocation;
        this.arrayListcategories = arrayListcategories;
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.child_event, parent,
                false);
        return new viewholder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.tv_event_title.setText(arrayListtitles.get(position));
        holder.tv_event_date.setText(arrayListdates.get(position));
        holder.tv_event_location.setText(arrayListlocation.get(position));
        holder.tv_event_category.setText(arrayListcategories.get(position));
    }

    @Override
    public int getItemCount() {
        return arrayListtitles.size();
    }

    public class viewholder extends RecyclerView.ViewHolder {

        TextView tv_event_title, tv_event_date, tv_event_location, tv_event_category;

        public viewholder(@NonNull View itemView) {
            super(itemView);

            tv_event_title = itemView.findViewById(R.id.tv_event_title);
            tv_event_date = itemView.findViewById(R.id.tv_event_date);
            tv_event_location = itemView.findViewById(R.id.tv_event_location);
            tv_event_category = itemView.findViewById(R.id.tv_event_category);
        }
    }

}
