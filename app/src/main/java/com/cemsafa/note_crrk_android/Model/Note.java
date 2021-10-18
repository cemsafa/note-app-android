package com.cemsafa.note_crrk_android.model;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.NO_ACTION;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

@Entity(tableName = "note", foreignKeys = @ForeignKey(entity = Folder.class, parentColumns = "id", childColumns = "folder_id", onDelete = CASCADE, onUpdate = NO_ACTION))
public class Note {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    private long folder_id;

    private String category_name;

    private String note_entry;

    @NonNull
    private String title;

    private String createdDate;

    public Note(long folder_id, String category_name, String note_entry, @NonNull String title, String createdDate) {
        this.folder_id = folder_id;
        this.category_name = category_name;
        this.note_entry = note_entry;
        this.title = title;
        this.createdDate = createdDate;
    }

    // getter

    public long getId() {
        return id;
    }

    public long getFolder_id() {
        return folder_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getNote_entry() {
        return note_entry;
    }



    //setter
    public void setId(long id) {
        this.id = id;
    }

    public void setFolder_id(long folder_id) {
        this.folder_id = folder_id;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setNote_entry(String note_entry) {
        this.note_entry = note_entry;
    }
}
