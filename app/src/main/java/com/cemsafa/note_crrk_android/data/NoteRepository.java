package com.cemsafa.note_crrk_android.data;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.cemsafa.note_crrk_android.model.Note;
import com.cemsafa.note_crrk_android.util.NoteRoomDB;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<Note>> allCategories;

    public NoteRepository (Application application) {

        NoteRoomDB noteRoomDB = NoteRoomDB.getInstance(application);
        noteDao = noteRoomDB.noteDao();
        allNotes = noteDao.getAllTitles();
        allCategories = noteDao.getAllCategories();

    }

    //public API

    public void insert(Note note) {
        NoteRoomDB.databaseWriteExecutor.execute(() -> noteDao.insert(note));

    }

    public void update(Note note) {
        NoteRoomDB.databaseWriteExecutor.execute(() -> noteDao.update(note));
    }

    public void delete (Note note) {
        NoteRoomDB.databaseWriteExecutor.execute(() -> noteDao.delete(note));
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<List<Note>> getAllCategories() {
        return allCategories;
    }

    public LiveData<Note> getNote(int id) {
        return noteDao.getNote(id);
    }
}
