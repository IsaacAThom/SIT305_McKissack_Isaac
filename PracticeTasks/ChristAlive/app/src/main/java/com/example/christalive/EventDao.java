package com.example.christalive;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.Calendar;
import java.util.List;

@Dao
public interface EventDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EventEntity event);

    @Query("DELETE FROM event_table")
    void deleteAll();

    @Query("DELETE FROM event_table WHERE uid = :id")
    void deleteEvent(int id);


    @Query("SELECT * FROM event_table ORDER BY eventDate ASC")
    LiveData<List<EventEntity>> getOrderedEvents();
}
