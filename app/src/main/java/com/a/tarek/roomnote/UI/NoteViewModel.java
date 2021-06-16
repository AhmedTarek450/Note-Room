package com.a.tarek.roomnote.UI;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;

import com.a.tarek.roomnote.RoomDataBase.NoteModel;
import com.a.tarek.roomnote.RoomDataBase.NoteRoomDB;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NoteViewModel extends AndroidViewModel {
    private NoteRoomDB noteRoomDB;
    private ExecutorService executorService;
    public NoteViewModel(@NonNull @org.jetbrains.annotations.NotNull Application application) {
        super(application);
        noteRoomDB = NoteRoomDB.getInstance(application);
        executorService = Executors.newFixedThreadPool(4);
    }

    public void insertNote(NoteModel note) {
        executorService.execute(() -> noteRoomDB.noteDao().insert(note));
    }

    public void updateNote(NoteModel note) {
        executorService.execute(() -> noteRoomDB.noteDao().update(note));
    }

    public void deleteNote(NoteModel note) {
        executorService.execute(() -> noteRoomDB.noteDao().delete(note));
    }

    public LiveData<List<NoteModel>> getAllNotes() {
        return noteRoomDB.noteDao().getAllNotes();
    }
}
