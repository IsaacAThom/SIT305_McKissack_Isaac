package com.example.personaleventplanner;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Calendar;
import java.util.Date;

//Entity storing the information of each event

@Entity(tableName = "event_table")
public class EventEntity {
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String eventTitle;

    public Calendar eventDate;

    public String eventLocation;

    public String eventCategory;

}
