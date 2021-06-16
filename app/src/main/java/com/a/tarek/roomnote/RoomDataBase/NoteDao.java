package com.a.tarek.roomnote.RoomDataBase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void insert(NoteModel insertDaoNoteModel);

    @Update
    void update(NoteModel updateDaoNoteModel);

    @Delete
    void delete(NoteModel deleteDaoNoteModel);

    @Query("SELECT * FROM notetable")
    LiveData<List<NoteModel>> getAllNotes();
}
