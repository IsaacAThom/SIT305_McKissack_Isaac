package com.example.lostandfound;

import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "advert_table")
public class AdvertEntity {
    // Primary key to fetch entities from db
    @PrimaryKey(autoGenerate = true)
    public int uid;

    public String advertTitle;

    public String advertPhone;

    public String advertCategory;

    public String advertDescription;

    public Date advertDate;

    public String advertLocation;

    public String advertImage;

    public String advertType;

    public AdvertEntity(@NonNull String advertTitle, @NonNull String advertPhone,
                        @NonNull String advertCategory, @NonNull String advertDescription,
                        @NonNull Date advertDate, @NonNull String advertLocation,
                        @NonNull String advertImage, @NonNull String advertType) {
        this.advertTitle = advertTitle;
        this.advertPhone = advertPhone;
        this.advertCategory = advertCategory;
        this.advertDescription = advertDescription;
        this.advertDate = advertDate;
        this.advertLocation = advertLocation;
        this.advertImage = advertImage;
        this.advertType = advertType;
    }
}
