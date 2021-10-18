package com.cemsafa.note_crrk_android.data;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.cemsafa.note_crrk_android.model.Folder;
import com.cemsafa.note_crrk_android.model.FolderWithNotes;
import com.cemsafa.note_crrk_android.model.Note;

import java.util.List;

@Dao
public abstract class NoteDao {

    @Insert(onConflict = IGNORE)
    public abstract void insert(Note note);

    @Query("DELETE FROM note")
    public abstract void deleteAll();

    @Query("SELECT * FROM note ORDER BY category_name ASC")
    public abstract LiveData<List<Note>> getAllNotes();

    @Query("SELECT * FROM note WHERE id = :id")
    public abstract LiveData<Note> getNote(long id);

    @Update
    public abstract void update(Note note);

    @Delete
    public abstract void delete(Note note);

    @Insert(onConflict = IGNORE)
    public abstract long insert(Folder folder);

    @Update
    public abstract void update(Folder folder);

    @Delete
    public abstract void delete(Folder folder);

    @Query("SELECT * FROM note WHERE category_name = :category_name")
    public abstract LiveData<List<Note>> getNotesInFolder(String category_name);

    @Query("SELECT * FROM note ORDER BY CASE WHEN :isAsc = 1 THEN title END ASC, CASE WHEN :isAsc = 0 THEN title END DESC" )
    public abstract LiveData<List<Note>> sortNotes (boolean isAsc);

    @Query("SELECT * FROM note WHERE title LIKE :searchQuery OR note_entry LIKE :searchQuery")
    public abstract LiveData<List<Note>> searchNotes (String searchQuery);


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


}
