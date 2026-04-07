package com.example.personaleventplanner;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.core.content.pm.ApplicationInfoBuilder;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@RunWith(AndroidJUnit4.class)
public class DatabaseTest {
    private EventDao eventDao;
    private AppDatabase db;

    private EventRepository eventRepository;
    private EventViewModel eventVM;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        eventDao = db.eventDao();
    }

    @After
    public void closeDb() throws IOException {
        db.close();
    }

    @Test
    public void writeEventAndReadInList() throws Exception {
        System.out.println(eventDao.getAll().size());
        EventEntity event = new EventEntity();
        event.eventTitle = "geourge";
        EventEntity event2 = new EventEntity();
        event2.eventTitle = "john";
        event2.eventDate = Calendar.getInstance();
        event2.eventLocation = "wow!";
        event2.eventCategory = "gym";
        eventDao.insertEvent(event);
        eventDao.insertEvent(event2);
        System.out.println(eventDao.getAll().size());
        System.out.println(event.eventTitle);

        EventEntity goGirl = eventDao.getSpecificEvent("geourge");
        EventEntity goGirl2 = eventDao.getSpecificEvent("john");

        String dateTime;
        Calendar calendar;
        SimpleDateFormat simpleDateFormat;

        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss aaa z");
        dateTime = simpleDateFormat.format(calendar.getTime()).toString();


        System.out.println(goGirl.eventTitle + " on date " + goGirl.eventDate + " with id " + goGirl.uid);
        System.out.println(goGirl2.uid + " " + goGirl2.eventTitle + " " + goGirl2.eventCategory + " " + goGirl2.eventLocation + " and now, the movies " + dateTime);

        System.out.println(simpleDateFormat.format(goGirl2.eventDate.getTime()));

        eventDao.deleteEvents(goGirl);
        System.out.println(eventDao.getAll().size());

        goGirl2.eventTitle = "simon";

        EventEntity goGirl3 = eventDao.getSpecificEvent("john");

        System.out.println(goGirl3.eventTitle + " " + goGirl3.uid);

        eventDao.updateEvent(goGirl2);

        goGirl3 = eventDao.getSpecificEvent("simon");

        System.out.println(goGirl3.eventTitle + " " + goGirl3.uid);
    }

    // this might need to be a *unit* test, which, wowzers, right. damn.
    @Test
    public void Testing2() throws Exception {
        EventEntity event = new EventEntity();
        event.eventTitle = "Buy Groceries";
        EventEntity event2 = new EventEntity();
        event2.eventTitle = "Visit Simon";
        event2.eventDate = Calendar.getInstance();
        event2.eventLocation = "Canterbury";
        event2.eventCategory = "Leisure";
        eventVM.insert(event);
        eventVM.insert(event2);
        System.out.println(eventVM.getAllEvents().getValue());


    }
}
