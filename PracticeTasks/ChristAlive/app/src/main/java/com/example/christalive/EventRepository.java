package com.example.christalive;

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

    LiveData<List<EventEntity>> getAllWords() {
        return allEvents;
    }

    void insert(EventEntity event) {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.insert(event);
        });
    }
}
