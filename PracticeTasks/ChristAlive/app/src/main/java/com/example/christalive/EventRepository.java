package com.example.christalive;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.Calendar;
import java.util.List;

public class EventRepository {

    private EventDao eventDao;
    private LiveData<List<EventEntity>> allEvents;

    EventRepository(Application application) {
        EventRoomDatabase db = EventRoomDatabase.getDatabase(application);
        eventDao = db.eventDao();
        allEvents = eventDao.getOrderedEvents();
    }

    LiveData<List<EventEntity>> getAllEvents() {
        return allEvents;
    }

    void insert(EventEntity event) {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.insert(event);
            Log.d("Repository ", event.eventDate.toString());
        });
    }

    void deleteEvent(int id) {
        EventRoomDatabase.databaseWriteExecutor.execute(() -> {
            eventDao.deleteEvent(id);
        });
    }
}
