package com.cemsafa.note_crrk_android.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cemsafa.note_crrk_android.R;
import com.cemsafa.note_crrk_android.model.NoteViewModel;

public class AddNoteActivity extends AppCompatActivity {

    public static final String NOTE_REPLY = "note_reply";
    public static final String TITLE_REPLY = "title_reply";
    public static final String ENTRY_REPLY = "entry_reply";
    private NoteViewModel noteViewModel;
    private long noteId = 0;
    private EditText editText;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(NoteViewModel.class);

        EditText editText = findViewById(R.id.et_add_note);
        Button buttonAdd = findViewById(R.id.btn_add_note);
        buttonAdd.setOnClickListener(v -> {

            buttonAdd();

        });
        if(getIntent().hasExtra(NotesActivity.NOTE_ID)){
            noteId = getIntent().getLongExtra(NotesActivity.NOTE_ID, 0);
            noteViewModel.getNote(noteId).observe(this, note -> {
                if( note != null) {
                    editText.setText(note.getTitle());
                }


            });


        }

    }
    private void buttonAdd () {
        String note = editText.getText().toString().trim();
        Intent intent = new Intent();
        intent.putExtra(NOTE_REPLY, note);
        setResult(RESULT_OK, intent);

    }

}
