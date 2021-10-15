package com.cemsafa.note_crrk_android.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.cemsafa.note_crrk_android.R;

public class AddNoteActivity extends AppCompatActivity {

    public static final String NOTE_REPLY = "note_reply";
    public static final String TITLE_REPLY = "title_reply";
    public static final String ENTRY_REPLY = "entry_reply";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
    }
}