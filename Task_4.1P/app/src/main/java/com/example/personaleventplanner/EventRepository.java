package com.example.personaleventplanner;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventRepository {

    private EventDao eventDao;
    private LiveData<List<EventEntity>> allEvents;

    EventRepository(Application application) {
        EventRoomDatabase db = EventRoomDatabase.getDatabase(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getOrderedEvents();
    }

    // Gets all events
    LiveData<List<EventEntity>> getAllEvents() {
        return allEvents;
    }

    // Adds to database
    void insert(EventEntity event) {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.insert(event);
        });
    }

    // Deletes event by id
    void deleteEvent(int id) {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.deleteEvent(id);
        });
    }

    // Fetching events for editing
    EventEntity getEvent(int id) {
        return eventDao.getEvent(id);
    }

    // Pushing update to edited event
    void update(EventEntity event) {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.update(event);
        });
    }
}
