package com.example.lostandfound;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class AdvertRepository {

    private AdvertDao advertDao;
    private LiveData<List<AdvertEntity>> allAdverts;

    AdvertRepository(Application application) {
        AdvertRoomDatabase db = AdvertRoomDatabase.getDatabase(application);
        advertDao = db.advertDao();
        allAdverts = advertDao.getAllAdverts();
    }

    // Get all adverts
    LiveData<List<AdvertEntity>> getAllAdverts() { return allAdverts; }

    // Adds to db
    void insertAdvert(AdvertEntity advert) {
        AdvertRoomDatabase.databaseWriteExecutor.execute(() -> {
            advertDao.insertAdvert(advert);
        });
    }

    // Deletes advert by id
    void deleteAdvert(int id) {
        AdvertRoomDatabase.databaseWriteExecutor.execute(() -> {
            advertDao.deleteAdvert(id);
        });
    }

    AdvertEntity getAdvert(int id) { return advertDao.getAdvert(id); }

    // Get filtered adverts
    LiveData<List<AdvertEntity>> getFilteredAdverts(String category) { return advertDao.getFilteredAdverts(category);}
}
