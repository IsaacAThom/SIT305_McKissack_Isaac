package com.example.lostandfound;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {AdvertEntity.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
// Will need a type converter i think
public abstract class AdvertRoomDatabase extends RoomDatabase {
    public abstract AdvertDao advertDao();

    private static volatile AdvertRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // "Debug" code allows main thread queries. Good enough! Small database, anyway.
    static AdvertRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AdvertRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AdvertRoomDatabase.class, "advert_database").addCallback(sRoomDatabaseCallback).allowMainThreadQueries().build();
                }
            }
        }
        return INSTANCE;
    }

    // Creates the database
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            //Debug content - adds a default advert (no image tho, i have to figure that out)
            databaseWriteExecutor.execute(() -> {
                AdvertDao dao = INSTANCE.advertDao();

//                AdvertEntity advert = new AdvertEntity("Lost Phone", "8675309", "Electronics",
//                        "Lost my phone at the shops :(", new Date(), "The shops");
//                dao.insertAdvert(advert);
            });
        }
    };
}
