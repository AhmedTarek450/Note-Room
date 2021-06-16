package com.a.tarek.roomnote.RoomDataBase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = NoteModel.class,version = 1)
public abstract class NoteRoomDB extends RoomDatabase {

    private static NoteRoomDB instance;

    //Singlton

    public static synchronized NoteRoomDB getInstance(Context context){

        if (instance == null){
            instance = Room.databaseBuilder(context,NoteRoomDB.class,"note-table")
                    .build();
        }
        return instance;
    }
    public abstract NoteDao noteDao();
}
