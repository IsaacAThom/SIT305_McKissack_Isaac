package com.example.lostandfound;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class NewAdvertFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    //Category spinner
    Spinner advertCategory;
    //Category selection
    int advertPos;


    public NewAdvertFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View thisFragmentView = inflater.inflate(R.layout.fragment_new_advert, container, false);

        advertCategory = thisFragmentView.findViewById(R.id.form_category_select);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                thisFragmentView.getContext(), R.array.advert_category_array,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        advertCategory.setAdapter(adapter);

        advertCategory.setOnItemSelectedListener(this);

        // You also need to get the image upload working. I believe in you bud.
        // You will also need a converter from bitmap to byte and back, as well as a date
        // converter possibly
        // https://stackoverflow.com/questions/9107900/how-to-upload-image-from-gallery-in-android
        // https://stackoverflow.com/questions/46337519/how-insert-image-in-room-persistence
        // Basically hte upload img button should trigger a oopup to the gallery, select an
        // image, yeehaw. i dont have any images on this "phone" so we'll see what happens

        return thisFragmentView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        advertPos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}