package com.cemsafa.note_crrk_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cemsafa.note_crrk_android.CategoryAdapter;
import com.cemsafa.note_crrk_android.NoteRVAdapter;
import com.cemsafa.note_crrk_android.R;
import com.cemsafa.note_crrk_android.model.NoteViewModel;

public class NotesActivity extends AppCompatActivity {

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private SearchView searchNote;
    private NoteRVAdapter noteAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(NoteViewModel.class);

        recyclerView = (RecyclerView) findViewById(R.id.notes_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteViewModel.getFolderWithNotes().observe(this, folderWithNotes -> {

//            noteAdapter = new NoteRVAdapter(note, this, this);
//            recyclerView.setAdapter(noteAdapter);

        });



    }
}