package com.mobilprogramlama.Odev_4.database;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "events")
public class Event {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String name;
    public String date;
    public String time;
    public String category; // Kategori özelliği eklendi

    public Event(String name, String date, String time, String category) {
        this.name = name;
        this.date = date;
        this.time = time;
        this.category = category;
    }
}
