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
import android.view.Menu;
import android.view.MenuInflater;
import com.cemsafa.note_crrk_android.NoteRVAdapter;
import com.cemsafa.note_crrk_android.R;
import com.cemsafa.note_crrk_android.model.Folder;
import com.cemsafa.note_crrk_android.model.Note;
import com.cemsafa.note_crrk_android.model.NoteViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class NotesActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {

    public final static String NOTE_ID = "note_id";

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteRVAdapter noteAdapter;
    private String categoryName;
    private long folderId = 0;
    FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(NoteViewModel.class);

        recyclerView = findViewById(R.id.notes_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        if (getIntent().hasExtra(CategoryActivity.CATEGORY_NAME));
        categoryName = getIntent().getStringExtra(CategoryActivity.CATEGORY_NAME);
        folderId = getIntent().getLongExtra(CategoryActivity.FOLDER_ID, 0);
        noteViewModel.getNotesInFolder(categoryName).observe(this, notes -> {
            noteAdapter = new NoteRVAdapter(notes, this, this);
            recyclerView.setAdapter(noteAdapter);
        });

        fab = findViewById(R.id.fab_add_note);
        fab.setOnClickListener(v -> {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.note_search).getActionView();
        searchView.setOnQueryTextListener(this);
        return true;

    }


    @Override
    public boolean onQueryTextSubmit(String query) {
        if (query != null) {
            return true;
        } else
            return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        if (newText != null) {
            return true;
        } else
            return false;
    }
}