package com.mobilprogramlama.Odev_4.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract EventDao eventDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, "event_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries() // Geçici olarak UI thread'de çalıştırıyoruz (ileri düzeyde bunu değiştirebiliriz)
                    .build();
        }
        return instance;
    }
}

