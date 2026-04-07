package com.example.personaleventplanner;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class EventViewModel extends AndroidViewModel {
    private EventRepository eventRepository;

    private final LiveData<List<EventEntity>> allEvents;

    public EventViewModel(@NonNull Application application) {
        super(application);
        eventRepository = new EventRepository(application);
        allEvents = eventRepository.getAllEvents();
    }

    LiveData<List<EventEntity>> getAllEvents() { return allEvents; }

    public void insert(EventEntity event) { eventRepository.insert(event);}
}
