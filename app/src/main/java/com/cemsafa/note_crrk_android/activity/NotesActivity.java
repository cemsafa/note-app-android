package com.cemsafa.note_crrk_android.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.cemsafa.note_crrk_android.CategoryAdapter;
import com.cemsafa.note_crrk_android.NoteRVAdapter;
import com.cemsafa.note_crrk_android.R;
import com.cemsafa.note_crrk_android.model.Folder;
import com.cemsafa.note_crrk_android.model.Note;
import com.cemsafa.note_crrk_android.model.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity  {

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private SearchView searchNote;
    private NoteRVAdapter noteAdapter;

    private List<Note> noteList;

    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(NoteViewModel.class);

        recyclerView = findViewById(R.id.notes_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

      
        noteViewModel.getNotesInFolder(folder_name).observe(this, notes -> {
            noteAdapter = new NoteRVAdapter(notes, this, this);
            recyclerView.setAdapter(noteAdapter);


        });

        fab = findViewById(R.id.fab_add_note);
        fab.setOnClickListener(v ->  {
                Intent intent = new Intent(NotesActivity.this, AddNoteActivity.class);
                startActivity(intent);

        });

        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                String note = data.getStringExtra(AddNoteActivity.NOTE_REPLY);
                String title = data.getStringExtra(AddNoteActivity.TITLE_REPLY);
                String entry = data.getStringExtra(AddNoteActivity.ENTRY_REPLY);

                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
                String createdDate = simpleDateFormat.format(calendar.getTime());

                Folder folder = new Folder();
                folder.setId(folder.getId());
                folder.setFolderName(folder.getFolderName());
                Note note1 = new Note(folder.getFolderName(), title, entry, createdDate);
                noteViewModel.insert(folder);

            }


        });




    }


}