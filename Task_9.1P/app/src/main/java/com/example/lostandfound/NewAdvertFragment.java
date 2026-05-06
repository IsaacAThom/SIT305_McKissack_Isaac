package com.example.lostandfound;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.api.net.PlacesClient;
import com.google.android.libraries.places.widget.AutocompleteSupportFragment;
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

public class NewAdvertFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    // Category spinner
    Spinner advertCategory;
    // Category selection
    int advertPos;

    // Activity Result Code for image selection
    int SELECT_PICTURE = 200;

    // Image Uri
    Uri selectedImageUri;

    ImageView advertImagePreview;

    private AdvertViewModel advertViewModel;

    AdvertEntity newAdvert;

    String advertType;

    PlacesClient placesClient;

    Place selectedPlace;

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

        // AdvertViewModel in
        advertViewModel = new ViewModelProvider(this).get(AdvertViewModel.class);

        // Date management
        DatePicker datePicker = thisFragmentView.findViewById(R.id.form_date_edit);

        // Initialise today's date
        Calendar today = Calendar.getInstance();

        // Collecting user input for Date here - initialise as today as well.
        Calendar advertDate = Calendar.getInstance();

        // Initialize DatePicker with the current date
        datePicker.init(
                today.get(Calendar.YEAR),
                today.get(Calendar.MONTH),
                today.get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int month, int day) {
                        // Update eventDate
                        advertDate.set(year, month, day);

                    }
                }
        );

        // Image acquisition
        Button uploadPhoto = thisFragmentView.findViewById(R.id.form_image_upload);
        uploadPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imageChooser();
            }
        });

        // Form variables
        EditText editAdvertTitle = thisFragmentView.findViewById(R.id.form_name_edit);
        EditText editAdvertPhone = thisFragmentView.findViewById(R.id.form_phone_edit);
        EditText editAdvertDescription = thisFragmentView.findViewById(R.id.form_description_edit);

        advertImagePreview = thisFragmentView.findViewById(R.id.form_image_preview);

        // Radio Group handling
        RadioButton radioLost = thisFragmentView.findViewById(R.id.radio_lost);
        RadioButton radioFound = thisFragmentView.findViewById(R.id.radio_found);

        radioLost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advertType = "Lost";
            }
        });

        radioFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                advertType = "Found";
            }
        });

        // Location Situation
        // Initialise place if somehow it hasn't been
        if (!Places.isInitialized()) {
            Places.initialize(getActivity().getApplicationContext(), getString(R.string.google_maps_api));
        }

        // Create the client for access to API
        placesClient = Places.createClient(getActivity().getApplicationContext());

        // Initialize AutocompleteSupportFragment
        AutocompleteSupportFragment autocompleteFragment =
                (AutocompleteSupportFragment) getChildFragmentManager().findFragmentById(R.id.form_autocomplete_location);

        // Set the fields for display when autocompleting
        assert autocompleteFragment != null;
        autocompleteFragment.setPlaceFields(Arrays.asList(Place.Field.ID,
                Place.Field.DISPLAY_NAME, Place.Field.FORMATTED_ADDRESS));

        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(@NonNull Place place) {
                Log.i("AUTOCOMPLETE", "Place: " +  place.getDisplayName() + ", " + place.getId());
                selectedPlace = place;
            }

            @Override
            public void onError(@NonNull Status status) {
                Log.e("AUTOCOMPLETE", "An error occurred: " + status);
            }
        });

        Button currentLocationButton = thisFragmentView.findViewById(R.id.form_location_current_button);

        Button saveAdvert = thisFragmentView.findViewById(R.id.advert_save_button);
        saveAdvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // All text fields filled
                if (TextUtils.isEmpty(editAdvertTitle.getText()) || TextUtils.isEmpty(editAdvertPhone.getText()) || TextUtils.isEmpty(editAdvertDescription.getText()) ) {
                    Toast.makeText(thisFragmentView.getContext(), "Error: Advert Not Fully Filled" +
                                    " In",
                            Toast.LENGTH_LONG).show();
                }
                // Date must be today or earlier
                else if(advertDate.after(today)) {
                    Toast.makeText(thisFragmentView.getContext(), "Error: Advert Date is in the future",
                            Toast.LENGTH_LONG).show();
                }
                // Check that a location has been entered
                else if(selectedPlace == null) {
                    Toast.makeText(thisFragmentView.getContext(), "Error: No Location Selected",
                            Toast.LENGTH_LONG).show();
                }
                // Image uploaded check
                else if(selectedImageUri == null) {
                    Toast.makeText(thisFragmentView.getContext(), "Error: No Image Selected",
                            Toast.LENGTH_LONG).show();
                }
                // One of the radios must be selected
                else if(!radioLost.isChecked() && !radioFound.isChecked()) {
                    Toast.makeText(thisFragmentView.getContext(), "Error: Must Select if Advert " +
                            "is for Lost or Found Item", Toast.LENGTH_LONG).show();
                }
                // All clear!
                else {
                    String advertTitle = String.valueOf(editAdvertTitle.getText());
                    String advertPhone = String.valueOf(editAdvertPhone.getText());
                    String advertDescription = String.valueOf(editAdvertDescription.getText());
                    String advertLocation = selectedPlace.getDisplayName();

                    // Convert to Date for storage
                    Date setDate = advertDate.getTime();

                    // Get category
                    String advertCategory = categoryResult(advertPos);

                    String advertImage = selectedImageUri.toString();

                    // Get Place ID
                    String advertPlaceID = selectedPlace.getId();

                    newAdvert = new AdvertEntity(advertTitle, advertPhone, advertCategory,
                            advertDescription, setDate, advertLocation, advertImage, advertType,
                            advertPlaceID);

                    advertViewModel.insert(newAdvert);

                    Toast.makeText(thisFragmentView.getContext(), "Advert Added",
                            Toast.LENGTH_LONG).show();

                    Log.d("NEW ADVERT",
                            "Advert Added: " + newAdvert.advertPlaceID);
                }
            }
        });

        return thisFragmentView;
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        advertPos = position;
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    // Select image - this is all a nightmare
    // Derived from this tutorial - https://www.geeksforgeeks.org/android/how-to-select-an-image-from-gallery-in-android/
    void imageChooser() {
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass constant to compare with request code
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            // compare resultCode with SELECT_PICTURE
            if (requestCode == SELECT_PICTURE) {
                // get url from the image
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    advertImagePreview.setImageURI(selectedImageUri);
                    // This is necessary for making it possible to read the URI across boots
                    getContext().getContentResolver().takePersistableUriPermission(data.getData(),
                            Intent.FLAG_GRANT_READ_URI_PERMISSION);
                }
            }
        }
    }

    public String categoryResult(int pos) {
        switch (pos) {
            case 0:
                return "Electronics";
            case 1:
                return "Pet";
            case 2:
                return "Wallet";
            case 3:
                return "Keys";
            case 4:
                return "Valuables";
            case 5:
                return "Other";
            default:
                return "Other";
        }
    }




}