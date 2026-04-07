package com.example.personaleventplanner;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EventRepository {
    private EventDao eventDao;
    private LiveData<List<EventEntity>> allEvents;

    EventRepository(Application application) {
        AppDatabase db = AppDatabase.getDatabase(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getOrderedEvents();
    }

    LiveData<List<EventEntity>> getAllEvents() {
        return allEvents;
    }

    void insert(EventEntity event) {
        AppDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.insertEvent(event);
        });
    }

}
