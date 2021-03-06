package com.cemsafa.note_crrk_android;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.widget.EditText;

import com.cemsafa.note_crrk_android.Model.Folder;
import com.cemsafa.note_crrk_android.Model.Note;
import com.cemsafa.note_crrk_android.Model.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class FolderActivity extends AppCompatActivity implements FolderRVAdapter.OnFolderClickListener {

    public static final String FOLDER_NAME = "folder_name";
    public static final String FOLDER_ID = "folder_id";

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private FolderRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_folder);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(NoteViewModel.class);

        recyclerView = findViewById(R.id.rvFolders);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteViewModel.getFolderWithNotes().observe(this, folderWithNotes -> {
            adapter = new FolderRVAdapter(folderWithNotes, this, this);
            recyclerView.setAdapter(adapter);
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(FolderActivity.this);
            builder.setTitle("Type in new folder name");

            final EditText input = new EditText(this);
            input.setInputType(InputType.TYPE_CLASS_TEXT);
            builder.setView(input);

            builder.setPositiveButton("OK", (dialog, which) -> {
                Folder folder = new Folder(input.getText().toString().trim());
                noteViewModel.insert(folder);
                adapter.notifyDataSetChanged();
            });

            builder.setNegativeButton("Cancel", (dialog, which) -> {
                dialog.cancel();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        noteViewModel.getFolderWithNotes().observe(this, folderWithNotes -> {
            adapter = new FolderRVAdapter(folderWithNotes, this, this);
            recyclerView.setAdapter(adapter);
        });
    }

    @Override
    public void onFolderClick(int position) {
        noteViewModel.getFolderWithNotes().observe(this, folderWithNotes -> {
            Intent intent = new Intent(FolderActivity.this, NoteActivity.class);
            intent.putExtra(FOLDER_NAME, folderWithNotes.get(position).getFolder().getName());
            intent.putExtra(FOLDER_ID, folderWithNotes.get(position).getFolder().getId());
            startActivity(intent);
        });
    }
}