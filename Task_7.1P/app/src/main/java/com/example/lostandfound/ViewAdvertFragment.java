package com.example.lostandfound;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ViewAdvertFragment extends Fragment {

    int position;

    private AdvertViewModel advertViewModel;
    private AdvertEntity advertEntity;

    public ViewAdvertFragment(int position) {
        // Required empty public constructor
        this.position = position;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View thisFragmentView = inflater.inflate(R.layout.fragment_view_advert, container, false);

        advertViewModel = new ViewModelProvider(this).get(AdvertViewModel.class);

        advertEntity = advertViewModel.getAdvert(position);

        TextView advertTitle = thisFragmentView.findViewById(R.id.advert_title);
        advertTitle.setText(advertEntity.advertTitle);
        TextView advertDate = thisFragmentView.findViewById(R.id.advert_date);
        advertDate.setText(advertEntity.advertDate.toString());
        TextView advertLocation = thisFragmentView.findViewById(R.id.advert_location);
        advertLocation.setText(advertEntity.advertLocation);
        TextView advertDescription = thisFragmentView.findViewById(R.id.advert_description);
        advertDescription.setText(advertEntity.advertDescription);

        // Inflate the layout for this fragment
        return thisFragmentView;
    }
}