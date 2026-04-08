package com.example.personaleventplanner;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface EventDao {

    // Add event to database
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(EventEntity event);

    // Used to delete a specific event
    @Query("DELETE FROM event_table WHERE uid = :id")
    void deleteEvent(int id);

    // Fetching a list of all events, earliest dates first
    @Query("SELECT * FROM event_table ORDER BY eventDate ASC")
    LiveData<List<EventEntity>> getOrderedEvents();

    // Fetches event (for use in updating, primarily)
    @Query("SELECT * FROM event_table WHERE uid = :id")
    EventEntity getEvent(int id);

    // Pushes update to event
    @Update
    void update(EventEntity event);
}
