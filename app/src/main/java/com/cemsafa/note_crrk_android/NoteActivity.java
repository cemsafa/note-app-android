package com.cemsafa.note_crrk_android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.widget.SearchView;

import com.cemsafa.note_crrk_android.Model.Folder;
import com.cemsafa.note_crrk_android.Model.Note;
import com.cemsafa.note_crrk_android.Model.NoteViewModel;
import com.nambimobile.widgets.efab.ExpandableFabLayout;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity implements NoteRVAdapter.OnNoteClickListener, View.OnClickListener, SearchView.OnQueryTextListener {

    public static final String NOTE_ID = "note_id";

    private NoteViewModel noteViewModel;
    private RecyclerView recyclerView;
    private NoteRVAdapter adapter;

    private String folderName;
    private long folderId = 0;
    private boolean isAsc;

    private List<Note> noteList;

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
            folderId = getIntent().getLongExtra(FolderActivity.FOLDER_ID, 0);
            noteViewModel.getNotesInFolder(folderName).observe(this, notes -> {
                adapter = new NoteRVAdapter(notes, this, this);
                recyclerView.setAdapter(adapter);
            });
        }

        ExpandableFabLayout fabLayout = findViewById(R.id.fab_layout);
        fabLayout.getPortraitConfiguration().getFabOptions().forEach(fabOption -> {
            fabOption.setOnClickListener(this);
        });
    }

    @Override
    public void onNoteClick(int position) {
        noteViewModel.getAllNotes().observe(this, notes -> {
            Intent intent = new Intent(NoteActivity.this, AddEditActivity.class);
            intent.putExtra(NOTE_ID, notes.get(position).getId());
            startActivity(intent);
        });
    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            String titleReply = data.getStringExtra(AddEditActivity.TITLE_REPLY);
            String contentReply = data.getStringExtra(AddEditActivity.CONTENT_REPLY);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
            String createdDate = simpleDateFormat.format(calendar.getTime());

            Folder folder = new Folder();
            folder.setId(folderId);
            folder.setName(folderName);
            Note note = new Note(folderName, titleReply, contentReply, createdDate, 0.0, 0.0, null, null);
            noteViewModel.insertNoteInFolder(folder, note);
        }
    });

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fabAddOption:
                Intent intent = new Intent(NoteActivity.this, AddEditActivity.class);
                launcher.launch(intent);
            case R.id.fabDeleteOption:
            case R.id.fabMoveOption:
            case R.id.fabSortOption:
                isAsc = !isAsc;
                noteViewModel.sortNotes(isAsc).observe(this, notes -> {
                    adapter = new NoteRVAdapter(notes, this, this);
                    recyclerView.setAdapter(adapter);
                });
                adapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.note_menu, menu);
        MenuItem search = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(this);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        if (s != null) {
            search(s);
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        if (s != null) {
            search(s);
        }
        return true;
    }

    private void search(String query) {
        adapter.getFilter().filter(query);
    }
}