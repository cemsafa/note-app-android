package com.cemsafa.note_crrk_android.Model;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;
    private LiveData<List<FolderWithNotes>> foldersWithNotes;

    public NoteRepository(Application application) {
        NoteRoomDB noteRoomDB = NoteRoomDB.getInstance(application);
        noteDao = noteRoomDB.noteDao();
        allNotes = noteDao.getAllNotes();
        foldersWithNotes = noteDao.getFolderWithNotes();
    }

    public void insert(Note note) {
        NoteRoomDB.dbWriteExec.execute(() -> {
            noteDao.insert(note);
        });
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public LiveData<Note> getNote(long id) {
        return noteDao.getNote(id);
    }

    public void update(Note note) {
        NoteRoomDB.dbWriteExec.execute(() -> {
            noteDao.update(note);
        });
    }

    public void delete(Note note) {
        NoteRoomDB.dbWriteExec.execute(() -> {
            noteDao.delete(note);
        });
    }

    public LiveData<List<Folder>> getAllFolders() {
        return noteDao.getAllFolders();
    }

    public long insert(Folder folder) {
        NoteRoomDB.dbWriteExec.execute(() -> {
            noteDao.insert(folder);
        });
        return folder.getId();
    }

    public void update(Folder folder) {
        NoteRoomDB.dbWriteExec.execute(() -> {
            noteDao.update(folder);
        });
    }

    public void delete(Folder folder) {
        NoteRoomDB.dbWriteExec.execute(() -> {
            noteDao.delete(folder);
        });
    }

    public LiveData<List<Note>> getNotesInFolder(String folder_name) {
        return noteDao.getNotesInFolder(folder_name);
    }

    public LiveData<List<FolderWithNotes>> getFolderWithNotes() {
        return noteDao.getFolderWithNotes();
    }

    public void insert(Folder folder, Note note) {
        NoteRoomDB.dbWriteExec.execute(() -> {
            noteDao.insert(folder, note);
        });
    }

    public void updateNoteInFolder(Folder folder, Note note) {
        NoteRoomDB.dbWriteExec.execute(() -> {
            noteDao.updateNoteInFolder(folder, note);
        });
    }
}
