package com.example.personaleventplanner;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event_table")
    List<EventEntity> getAll();

    //Sorts Events by Date order (god i hope this works)
    @Query("SELECT * FROM event_table ORDER BY eventDate ASC")
    LiveData<List<EventEntity>> getOrderedEvents();

    // Testing function
    @Query("SELECT * FROM event_table WHERE eventTitle LIKE :name")
    EventEntity getSpecificEvent(String name);

    @Insert
    void insertEvent(EventEntity... eventEntities);

    @Update
    void updateEvent(EventEntity... eventEntities);

    @Delete
    void deleteEvents(EventEntity... eventEntities);
}
