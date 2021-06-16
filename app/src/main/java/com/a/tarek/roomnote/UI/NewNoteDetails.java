package com.a.tarek.roomnote.UI;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.a.tarek.roomnote.R;
import com.a.tarek.roomnote.RoomDataBase.Constants;
import com.a.tarek.roomnote.RoomDataBase.NoteModel;

public class NewNoteDetails extends AppCompatActivity {

    private EditText titleET;
    private EditText descET;
    private int receivedId;
    private boolean openedAsUpdate = false;
    private NoteViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note_details);

        titleET = findViewById(R.id.et_title);
        descET = findViewById(R.id.et_description);

        receivedId = getIntent().getIntExtra(Constants.ID, -1);
        if (receivedId != -1) {
            setTitle("Update Note");
            titleET.setText(getIntent().getStringExtra(Constants.TITLE));
            descET.setText(getIntent().getStringExtra(Constants.DESCRIPTION));
            Button updateBtn = findViewById(R.id.btn_update);
            updateBtn.setVisibility(View.VISIBLE);
            openedAsUpdate = true;
        } else
            setTitle(R.string.add_note);

        viewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory
                        .getInstance(this.getApplication())).get(NoteViewModel.class);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (openedAsUpdate)
            return false;
        else {
            getMenuInflater().inflate(R.menu.save_btn, menu);
            return true;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.item_save_note) {
            saveNote();
            return true;
        }
        return true;
    }

    private void saveNote() {
        String writtenTitle = titleET.getText().toString();
        String writtenDesc = descET.getText().toString();
        if (writtenTitle.isEmpty())
            titleET.setError("Required Field");
        else {
            NoteModel note = new NoteModel(writtenTitle, writtenDesc);
            viewModel.insertNote(note);
            Toast.makeText(this, "Note Saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void updateNote(View view) {
        String writtenTitle = titleET.getText().toString();
        String writtenDesc = descET.getText().toString();
        int noteID = getIntent().getIntExtra(Constants.ID, -1);
        NoteModel note = new NoteModel(noteID, writtenTitle, writtenDesc);
        viewModel.updateNote(note);
        Toast.makeText(this, "Note Updated", Toast.LENGTH_SHORT).show();
        finish();
    }

}