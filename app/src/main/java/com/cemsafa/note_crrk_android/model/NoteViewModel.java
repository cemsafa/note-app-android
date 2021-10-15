package com.cemsafa.note_crrk_android.model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.cemsafa.note_crrk_android.data.NoteRepository;

import java.util.List;

public class NoteViewModel extends AndroidViewModel {

    private NoteRepository repository;

    private final LiveData<List<Note>> allNotes;

    public NoteViewModel(@NonNull Application application) {
        super(application);

        repository = new NoteRepository(application);
        allNotes = repository.getAllNotes();
    }

    public void insert(Note note) {

        repository.insert(note);
    }

    public LiveData<List<Note>> getAllNotes() {

        return allNotes;
    }

    public LiveData<Note> getNote(long id) {
        return repository.getNote(id);
    }

    public void update(Note note) {
        repository.update(note);
    }

    public void delete(Note note) {
        repository.delete(note);
    }

    public void update(Folder folder) {
        repository.update(folder);
    }

    public void delete(Folder folder) {
        repository.delete(folder);
    }

    public LiveData<List<Note>> getNotesInFolder(String folder_name) {
        return repository.getNotesInFolder(folder_name);
    }

    public LiveData<List<FolderWithNotes>> getFolderWithNotes() {
        return repository.getFolderWithNotes();
    }

    public void insert(Folder folder, Note note) {


        repository.insert(folder, note);
    }

    public void updateNoteInFolder(Folder folder, Note note) {
        repository.updateNoteInFolder(folder, note);
    }

    public LiveData<List<Note>> sortNotes(boolean isAsc) {
        return repository.sortNotes(isAsc);
    }
}
