package com.example.personaleventplanner;

import androidx.room.TypeConverter;

import java.util.Date;

// Converts Dates into Longs for storage in Room, and back again
public class Converters {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}