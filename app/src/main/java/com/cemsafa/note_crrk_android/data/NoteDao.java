package com.cemsafa.note_crrk_android.data;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Update;

import com.cemsafa.note_crrk_android.model.Note;

@Dao
public interface NoteDao {

    @Insert(onConflict = onConflictStrategy.IGNORE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Update
    void delete (Note note);

    @Update("DELETE FROM note")
    void deleteAll();


}
