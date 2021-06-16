package com.a.tarek.roomnote.UI;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.a.tarek.roomnote.R;
import com.a.tarek.roomnote.RecycelerView.Adapter;
import com.a.tarek.roomnote.RoomDataBase.Constants;
import com.a.tarek.roomnote.RoomDataBase.NoteModel;

import java.util.ArrayList;
import java.util.Random;


public class MainActivity extends AppCompatActivity implements Adapter.OnItemClickListener {

    private RecyclerView recyclerView;
    private Adapter adapter;
    private NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("All Notes");

        recyclerView = findViewById(R.id.recycler_view);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(this.getApplication())).get(NoteViewModel.class);

        viewModel = new ViewModelProvider(this).get(NoteViewModel.class);
        viewModel.getAllNotes().observe(this, noteModels -> {
            View view = findViewById(R.id.layout_no_notes);
            if (noteModels.size() == 0)
                view.setVisibility(View.VISIBLE);
            else {
                view.setVisibility(View.INVISIBLE);
                adapter = new Adapter(MainActivity.this, noteModels);
                recyclerView.setAdapter(adapter);
                adapter.setOnItemClickListener(MainActivity.this);
            }
        });

        swipeToDelete();
    }

    private void swipeToDelete() {
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0
                , ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView
                    , @NonNull RecyclerView.ViewHolder viewHolder
                    , @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder
                    , int direction) {
                int position = viewHolder.getAdapterPosition();
                NoteModel currentNote = adapter.getNotes().get(position);
                showDeleteDialog(position, currentNote);
            }
        };
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);
        touchHelper.attachToRecyclerView(recyclerView);
    }

    private void showDeleteDialog(int position, NoteModel currentNote) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder
                .setTitle("Are you sure you want to delete this note?")
                .setMessage("This action cannot be undone")
                .setPositiveButton("OK", (dialog, which) -> {
                    viewModel.deleteNote(currentNote);
                })
                .setNegativeButton("Cancel", (dialog, which) -> {
                    adapter.notifyItemChanged(position);
                })
                .setCancelable(false)
                .show();
    }

    public void openAddNoteActivity(View view) {
        Intent i = new Intent(this, NewNoteDetails.class);
        startActivity(i);
    }

    @Override
    public void onItemClick(int position, NoteModel note) {
        Intent i = new Intent(this, NewNoteDetails.class);
        i.putExtra(Constants.ID, note.getId());
        i.putExtra(Constants.TITLE, note.getTitle());
        i.putExtra(Constants.DESCRIPTION, note.getDescription());
        startActivity(i);
    }

}