package com.cemsafa.note_crrk_android.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cemsafa.note_crrk_android.model.Note;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    public NoteRepository(Application application) {


    }
}
