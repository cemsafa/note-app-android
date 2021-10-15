package com.cemsafa.note_crrk_android;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.cemsafa.note_crrk_android.Model.Folder;
import com.cemsafa.note_crrk_android.Model.Note;
import com.cemsafa.note_crrk_android.Model.NoteViewModel;

public class AddEditActivity extends AppCompatActivity {

    public static final String TITLE_REPLY = "title_reply";
    public static final String CONTENT_REPLY = "content_reply";

    private EditText etTitle, etContent;

    private boolean isEditing = false;
    private long noteId = 0;
    private Note noteToUpdate;

    private NoteViewModel noteViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit);

        noteViewModel = new ViewModelProvider.AndroidViewModelFactory(this.getApplication()).create(NoteViewModel.class);

        etTitle = findViewById(R.id.titleET);
        etContent = findViewById(R.id.contentET);

        Button addEditButton = findViewById(R.id.saveBtn);

        addEditButton.setOnClickListener(v -> {
            addEditNote();
        });

        if (getIntent().hasExtra(NoteActivity.NOTE_ID)) {
            noteId = getIntent().getLongExtra(NoteActivity.NOTE_ID, 0);

            noteViewModel.getNote(noteId).observe(this, note -> {
                if (note != null) {
                    etTitle.setText(note.getTitle());
                    etContent.setText(note.getContent());
                    noteToUpdate = note;
                }
            });
            isEditing = true;
        }
    }

    private void addEditNote() {
        String title = etTitle.getText().toString().trim();
        String content = etContent.getText().toString().trim();

        if (title.isEmpty()) {
            etTitle.setError("Note must have a title");
            etTitle.requestFocus();
            return;
        }

        if (content.isEmpty()) {
            etContent.setError("Note must have a content");
            etContent.requestFocus();
            return;
        }

        if (isEditing) {
            Folder folder = new Folder();
            Note note = new Note();

            folder.setId(noteToUpdate.getFolder_id());
            folder.setName(noteToUpdate.getFolder_name());
            note.setId(note.getId());
            note.setTitle(title);
            note.setContent(content);
            noteViewModel.updateNoteInFolder(folder, note);
        } else {
            Intent intent = new Intent();
            intent.putExtra(TITLE_REPLY, title);
            intent.putExtra(CONTENT_REPLY, content);
            setResult(RESULT_OK, intent);
        }
        finish();
    }
}