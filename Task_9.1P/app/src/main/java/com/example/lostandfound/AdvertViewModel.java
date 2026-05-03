package com.example.lostandfound;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class AdvertViewModel extends AndroidViewModel {

    private AdvertRepository advertRepository;

    private final LiveData<List<AdvertEntity>> allAdverts;

    public AdvertViewModel (Application application) {
        super(application);
        advertRepository = new AdvertRepository(application);
        allAdverts = advertRepository.getAllAdverts();
    }

    LiveData<List<AdvertEntity>> getAllAdverts() { return allAdverts; }

    public void insert(AdvertEntity advert) {
        advertRepository.insertAdvert(advert);
    }

    public AdvertEntity getAdvert(int id) { return advertRepository.getAdvert(id); }

    public void deleteAdvert(int id) { advertRepository.deleteAdvert(id); }

    LiveData<List<AdvertEntity>> getFilteredAdverts(String category) { return advertRepository.getFilteredAdverts(category); }

}
