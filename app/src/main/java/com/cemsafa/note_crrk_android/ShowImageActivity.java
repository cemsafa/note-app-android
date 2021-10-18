package com.cemsafa.note_crrk_android;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;

import java.io.IOException;

public class ShowImageActivity extends AppCompatActivity {

    private ImageView imageView;

    private NoteRVAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_image);

        adapter = new NoteRVAdapter();

        imageView = findViewById(R.id.showPhotoIV);
        if (getIntent().hasExtra(adapter.NOTE_PHOTO)) {
            String receivedPhotoString = getIntent().getStringExtra(adapter.NOTE_PHOTO);
            try {
                ProxyBitmap bitmap = (ProxyBitmap) ObjectSerializer.deserialize(receivedPhotoString);
                Bitmap photo = bitmap.getBitmap();
                imageView.setImageBitmap(photo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}