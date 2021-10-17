package com.cemsafa.note_crrk_android;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import com.cemsafa.note_crrk_android.Model.Folder;
import com.cemsafa.note_crrk_android.Model.Note;
import com.cemsafa.note_crrk_android.Model.NoteViewModel;

import java.io.IOException;
import java.io.Serializable;

public class AddEditActivity extends AppCompatActivity {

    public static final String TITLE_REPLY = "title_reply";
    public static final String CONTENT_REPLY = "content_reply";
    public static final String IMAGE_REPLY = "image_reply";
    public static final String AUDIO_REPLY = "audio_reply";

    private EditText etTitle, etContent;

    private boolean isEditing = false;
    private long noteId = 0;
    private Note noteToUpdate;

    private String image;
    private String audio;

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
            Note note = noteToUpdate;

            folder.setId(noteToUpdate.getFolder_id());
            folder.setName(noteToUpdate.getFolder_name());
            note.setId(note.getId());
            note.setTitle(title);
            note.setContent(content);
            note.setCreatedDate(noteToUpdate.getCreatedDate());
            note.setLatitude(noteToUpdate.getLatitude());
            note.setLongitude(noteToUpdate.getLongitude());
            note.setPhoto(image);
            note.setAudio(audio);
            noteViewModel.updateNoteInFolder(folder, note);
        } else {
            Intent intent = new Intent();
            intent.putExtra(TITLE_REPLY, title);
            intent.putExtra(CONTENT_REPLY, content);
            intent.putExtra(IMAGE_REPLY, image);
            intent.putExtra(AUDIO_REPLY, audio);
            setResult(RESULT_OK, intent);
        }
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_takePhoto:
                Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                takePhotoLauncher.launch(takePhoto);
                return false;
            case R.id.menu_pickPhoto:
                Intent pickPhoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                pickPhotoLauncher.launch(pickPhoto);
                return false;
            case R.id.menu_addAudio:
                Intent audioIntent = new Intent(getApplicationContext(), RecordPlayActivity.class);
                audioLauncher.launch(audioIntent);
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    ActivityResultLauncher<Intent> takePhotoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            Bitmap bitmap = data.getParcelableExtra("data");
            ProxyBitmap proxyBitmap = new ProxyBitmap(bitmap);
            try {
                image = ObjectSerializer.serialize((Serializable) proxyBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    ActivityResultLauncher<Intent> pickPhotoLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
            Uri uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                Bitmap compressed = resizeImage(bitmap);
                ProxyBitmap proxyBitmap = new ProxyBitmap(compressed);
                image = ObjectSerializer.serialize((Serializable) proxyBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    });

    ActivityResultLauncher<Intent> audioLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
        if (result.getResultCode() == Activity.RESULT_OK) {
            Intent data = result.getData();
        }
    });

    private Bitmap resizeImage(Bitmap bitmap) {

        int scaledWidth = bitmap.getWidth() / 10;
        int scaledHeight = bitmap.getHeight() / 10;

        if (bitmap.getByteCount() <= 1000000) {
            return bitmap;
        } else {
            return Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, false);
        }
    }
}