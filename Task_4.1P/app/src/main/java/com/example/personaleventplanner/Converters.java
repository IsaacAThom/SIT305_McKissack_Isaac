package com.example.personaleventplanner;

import androidx.room.TypeConverter;

import java.util.Calendar;

// For the purpose of converting Calendars into a usable format by Room (whyyyyy)
public class Converters {
    @TypeConverter
    public static Calendar fromTimestamp(Long value) {
        return value == null ? null : Calendar.getInstance();
    }

    @TypeConverter
    public static Long calendarToTimestamp(Calendar calendar) {
        return calendar == null ? null : Calendar.getInstance().getTimeInMillis();
    }
}
