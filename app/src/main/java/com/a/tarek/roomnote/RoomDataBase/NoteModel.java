package com.a.tarek.roomnote.RoomDataBase;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "NoteTable")
public class NoteModel {

    @PrimaryKey(autoGenerate = true)
    int id;
    String title;
    String description;

    @Ignore
    public NoteModel(int id, String title, String description) {
        this.id = id;
        this.title = title;
        this.description = description;
    }

    public NoteModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }
}
