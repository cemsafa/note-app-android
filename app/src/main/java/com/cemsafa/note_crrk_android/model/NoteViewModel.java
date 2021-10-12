package com.cemsafa.note_crrk_android.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.cemsafa.note_crrk_android.data.NoteRepository;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;
    private final

    public NoteViewModel(@NonNull Application application) {
        super(application);
    }
}
