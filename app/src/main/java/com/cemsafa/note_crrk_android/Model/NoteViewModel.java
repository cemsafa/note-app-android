package com.cemsafa.note_crrk_android.Model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.room.Query;

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

    public LiveData<List<Folder>> getAllFolders() {
        return repository.getAllFolders();
    }

    public LiveData<Folder> getFolder(long id) {
        return repository.getFolder(id);
    }

    public long insert(Folder folder) {
        repository.insert(folder);
        return folder.getId();
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

    public LiveData<List<Note>> searchInNotes(String searchQuery) {
        return repository.searchInNotes(searchQuery);
    }

    public LiveData<List<Note>> sortNotes(boolean isAsc) {
        return repository.sortNotes(isAsc);
    }

    public void insertNoteInFolder(Folder folder, Note note) {
        repository.insertNoteInFolder(folder, note);
    }
}
