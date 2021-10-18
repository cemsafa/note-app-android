package com.cemsafa.note_crrk_android.activity;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


import com.cemsafa.note_crrk_android.CategoryAdapter;
import com.cemsafa.note_crrk_android.NoteRVAdapter;
import com.cemsafa.note_crrk_android.R;


import com.cemsafa.note_crrk_android.model.Folder;
import com.cemsafa.note_crrk_android.model.FolderWithNotes;
import com.cemsafa.note_crrk_android.model.Note;
import com.cemsafa.note_crrk_android.model.NoteViewModel;
import com.nambimobile.widgets.efab.ExpandableFab;
import com.nambimobile.widgets.efab.ExpandableFabLayout;
import com.nambimobile.widgets.efab.FabOption;

import java.util.List;

public class CategoryActivity extends AppCompatActivity implements CategoryAdapter.onCategoryClickListener {

    public static final String CATEGORY_NAME = "category_name";
    public static final String FOLDER_ID = "folder_id";

    private NoteViewModel noteViewModel;
    private CategoryAdapter adapter;
    private RecyclerView recyclerView;
    private List<FolderWithNotes> folderWithNotes;
    private Note note;
    private Folder folder;
    ExpandableFabLayout exFab;
    FabOption exFabAdd;
    FabOption exFabDelete;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(NoteViewModel.class);

        recyclerView = findViewById(R.id.category_recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        noteViewModel.getFolderWithNotes().observe(this, folderWithNotes -> {

            adapter = new CategoryAdapter(folderWithNotes, this, this);
            recyclerView.setAdapter(adapter);


        });

        exFab = findViewById(R.id.exFab);
        exFabAdd = findViewById(R.id.exfab_add_category);
        exFabDelete = findViewById(R.id.exfab_delete_category);


        ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {

            if (result.getResultCode() == Activity.RESULT_OK) {
                Intent data = result.getData();
                String category = data.getStringExtra(AddCategoryActivity.CATEGORY_REPLY);
                String notes = data.getStringExtra(AddNoteActivity.NOTE_REPLY);

                Folder folder = new Folder(note.getCategory_name());
                noteViewModel.insert(folder, note);

            }


        });

    }

    @Override
    public void onCategoryClick(int position) {

        // code to transfer to AddNoteActivity (where the note list is) by clicking the category folder
        noteViewModel.getFolderWithNotes().observe(this, folderWithNotes -> {
            Intent intent = new Intent(CategoryActivity.this, NotesActivity.class);
            intent.putExtra(CATEGORY_NAME, folderWithNotes.get(position).getFolder().getFolderName());

        });


    }

    public void addCategoryClicked(View view) {
        exFabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CategoryActivity.this, AddCategoryActivity.class);

            }
        });


    }

}