package com.cemsafa.note_crrk_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cemsafa.note_crrk_android.Model.Note;
import com.cemsafa.note_crrk_android.Model.NoteViewModel;

import java.util.List;

public class NoteActivity extends AppCompatActivity implements NoteRVAdapter.OnNoteClickListener {

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteRVAdapter adapter;

    private String folderName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(NoteViewModel.class);

        recyclerView = findViewById(R.id.rvNotes);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().hasExtra(FolderActivity.FOLDER_NAME)) {
            folderName = getIntent().getStringExtra(FolderActivity.FOLDER_NAME);
            noteViewModel.getNotesInFolder(folderName).observe(this, notes -> {
                adapter = new NoteRVAdapter(notes, this, this);
                recyclerView.setAdapter(adapter);
            });
        }
    }

    @Override
    public void onNoteClick(int position) {

    }
}