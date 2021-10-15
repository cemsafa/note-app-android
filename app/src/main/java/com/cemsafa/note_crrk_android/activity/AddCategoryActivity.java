package com.cemsafa.note_crrk_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cemsafa.note_crrk_android.CategoryAdapter;
import com.cemsafa.note_crrk_android.R;
import com.cemsafa.note_crrk_android.model.Folder;
import com.cemsafa.note_crrk_android.model.FolderWithNotes;
import com.cemsafa.note_crrk_android.model.Note;
import com.cemsafa.note_crrk_android.model.NoteViewModel;

public class AddCategoryActivity extends AppCompatActivity {

    public static final String CATEGORY_REPLY = "category_reply";
    private NoteViewModel noteViewModel;
    private Note note;
    private Folder folder;
    private FolderWithNotes folderWN;
    private EditText et_category;
    private long folderId = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(NoteViewModel.class);

        et_category = (EditText) findViewById(R.id.et_add_category);
        Button addBtn = findViewById(R.id.btn_add_category);
        addBtn.setOnClickListener(v -> {

        });
        if(getIntent().hasExtra(CategoryActivity.FOLDER_ID)){
            folderId = getIntent().getLongExtra(CategoryActivity.FOLDER_ID, 0);
            noteViewModel.getFolderWithNotes().observe(this, folderWithNotes -> {

                et_category.setText(folder.getFolderName());
            });


        }

    }

    private void addBtn() {
        String category = et_category.getText().toString().trim();
        Intent intent = new Intent();
        intent.putExtra(CATEGORY_REPLY, category);
        setResult(RESULT_OK, intent);
    }
}