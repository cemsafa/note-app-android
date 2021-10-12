package com.cemsafa.note_crrk_android.Model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FolderWithNotes {

    @Embedded
    public Folder folder;

    @Relation(parentColumn = "id", entityColumn = "folder_id")
    public List<Note> notes;

    public FolderWithNotes(Folder folder, List<Note> notes) {
        this.folder = folder;
        this.notes = notes;
    }

    public Folder getFolder() {
        return folder;
    }

    public List<Note> getNotes() {
        return notes;
    }
}
