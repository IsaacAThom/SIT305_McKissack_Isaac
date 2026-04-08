package com.example.christalive;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import java.util.Date;

//Entity storing the information of each event

@Entity(tableName = "event_table")
public class EventEntity {
    // Primary Key - used to fetch entities from the database
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String eventTitle;

    public Date eventDate;
    public String eventLocation;

    public String eventCategory;

    public EventEntity(@NonNull String eventTitle, @NonNull Date eventDate,
                       @NonNull String eventLocation,
                       @NonNull String eventCategory) {
        this.eventTitle = eventTitle;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventCategory = eventCategory;
    }

}
