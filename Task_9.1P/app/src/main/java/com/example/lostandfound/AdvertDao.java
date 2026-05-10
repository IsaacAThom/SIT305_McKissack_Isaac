package com.example.lostandfound;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AdvertDao {

    // Add advert to db
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertAdvert(AdvertEntity advert);

    // Delete advert
    @Query("DELETE FROM advert_table WHERE uid = :id")
    void deleteAdvert(int id);

    // Fetch all events, order DESC ("most recent")
    @Query("SELECT * FROM advert_table ORDER BY advertDate DESC")
    LiveData<List<AdvertEntity>> getAllAdverts();

    // Filter events by category
    @Query("SELECT * FROM advert_table WHERE advertCategory = :category ORDER BY advertDate DESC")
    LiveData<List<AdvertEntity>> getFilteredAdverts(String category);

    // Retrieve specific event for viewing
    @Query("SELECT * FROM advert_table WHERE uid = :id")
    AdvertEntity getAdvert(int id);

    // Retrieve row count
    @Query("SELECT COUNT(*) FROM advert_table")
    Integer getRowCount();

    // Fetch all events, order not-important, as a list for map purposes
    @Query("SELECT * FROM advert_table")
    List<AdvertEntity> getAllAdvertsMapList();
}
