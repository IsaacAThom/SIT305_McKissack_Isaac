package com.example.lostandfound;

import android.app.Application;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.text.SimpleDateFormat;
import java.util.Objects;

class AdvertViewHolder extends RecyclerView.ViewHolder implements RecyclerViewInterface {

    private final TextView advertTitleView, advertDateView, advertLabelView;
    private final Button openAdvert;
    private AdvertRepository advertRepository;

    private MainActivity mainActivity;

    // Format for displaying dates correctly for Users
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy MMM dd");

    private AdvertViewHolder(View itemView) {
        super(itemView);

        advertTitleView = itemView.findViewById(R.id.advert_title);
        advertDateView = itemView.findViewById(R.id.advert_date);
        advertLabelView = itemView.findViewById(R.id.advert_label);

        openAdvert = itemView.findViewById(R.id.open_advert_button);

        advertRepository = new AdvertRepository(new Application());

        mainActivity = (MainActivity) itemView.getContext();

    }

    public void bind(AdvertEntity advertEntity) {
        // Converting date input to something readable
        String dateString = sdf.format(advertEntity.advertDate.getTime());

        // Pass relevant info to the recyler
        advertTitleView.setText(advertEntity.advertTitle);
        advertDateView.setText(dateString);

        // Change Item Label based on lost/found category
        advertLabelView.setText(advertEntity.advertType);

        openAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open Event in larger view
                openAdvertOnClick(advertEntity.uid);
            }
        });
    }

    static AdvertViewHolder create(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.advert_item, parent
                , false);
        return new AdvertViewHolder(view);
    }

    // Incorporating method from RecylcerViewInterface
    @Override
    public void openAdvertOnClick(int position) {
        // Calling function to open view advert
        mainActivity.showViewAdvert(position);
    }
}
