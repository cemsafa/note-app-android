package com.cemsafa.note_crrk_android.activity;

import androidx.appcompat.app.AppCompatActivity;

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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        et_category = (EditText) findViewById(R.id.et_add_category);


    }
}