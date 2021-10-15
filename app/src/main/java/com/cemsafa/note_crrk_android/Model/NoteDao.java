package com.cemsafa.note_crrk_android.Model;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public abstract class NoteDao {

    @Insert(onConflict = IGNORE)
    public abstract void insert(Note note);

    @Query("DELETE FROM note")
    public abstract void deleteAll();

    @Query("SELECT * FROM note ORDER BY title ASC")
    public abstract LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM note WHERE id = :id")
    public abstract LiveData<Note> getNote(long id);

    @Update
    public abstract void update(Note note);

    @Delete
    public abstract void delete(Note note);

    @Query("SELECT * FROM folder ORDER BY name ASC")
    public abstract LiveData<List<Folder>> getAllFolders();

    @Query("SELECT * FROM folder WHERE id = :id")
    public abstract LiveData<Folder> getFolder(long id);

    @Insert(onConflict = IGNORE)
    public abstract long insert(Folder folder);

    @Update
    public abstract void update(Folder folder);

    @Delete
    public abstract void delete(Folder folder);

    @Query("SELECT * FROM note WHERE folder_name = :folder_name")
    public abstract LiveData<List<Note>> getNotesInFolder(String folder_name);

    @Transaction
    @Query("SELECT * FROM folder")
    public abstract LiveData<List<FolderWithNotes>> getFolderWithNotes();

    @Transaction
    public void insert(Folder folder, Note note) {
        final long folder_id = insert(folder);
        note.setFolder_id(folder_id);
        insert(note);
    }

    @Transaction
    public void updateNoteInFolder(Folder folder, Note note) {
        update(folder);
        update(note);
    }

    @Query("SELECT * FROM note WHERE title LIKE :searchQuery OR content LIKE :searchQuery")
    public abstract LiveData<List<Note>> searchInNotes(String searchQuery);

    @Query("SELECT * FROM note ORDER BY CASE WHEN :isAsc = 1 THEN title END ASC, CASE WHEN :isAsc = 0 THEN title END DESC")
    public abstract LiveData<List<Note>> sortNotes(boolean isAsc);

    @Query("SELECT * FROM note ORDER BY CASE WHEN :isAsc = 1 THEN createdDate END ASC, CASE WHEN :isAsc = 0 THEN createdDate END DESC")
    public abstract LiveData<List<Note>> sortByDate(boolean isAsc);

    @Transaction
    public void insertNoteInFolder(Folder folder, Note note) {
        note.setFolder_id(folder.getId());
        insert(note);
    }
}
