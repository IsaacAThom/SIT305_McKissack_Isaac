package com.example.christalive;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.Calendar;
import java.util.List;

public class EventViewModel extends AndroidViewModel {

    private EventRepository eventRepository;

    private final LiveData<List<EventEntity>> allEvents;

    public EventViewModel (Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        allEvents = eventRepository.getAllEvents();
    }

    LiveData<List<EventEntity>> getAllEvents() { return allEvents; }

    public void insert(EventEntity event) {
        eventRepository.insert(event);
        Log.d("ViewModel ", event.eventDate.toString());
    }

    public void deleteEvent(int id) {
        eventRepository.deleteEvent(id);
    }
}
