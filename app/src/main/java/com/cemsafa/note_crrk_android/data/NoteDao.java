package com.cemsafa.note_crrk_android.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.cemsafa.note_crrk_android.model.Note;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Update
    void update (Note note);

    @Delete
    void delete (Note note);

    @Query("DELETE FROM note")
    void deleteAll();

    @Query("SELECT * FROM note ORDER BY category ASC")
    LiveData<List<Note>> getAllCategories();


    @Query("SELECT * FROM note ORDER BY title ASC")
    LiveData<List<Note>> getAllTitles();

    @Query("SELECT * FROM note WHERE id == :id")
    LiveData<Note> getNote (int id);


}
