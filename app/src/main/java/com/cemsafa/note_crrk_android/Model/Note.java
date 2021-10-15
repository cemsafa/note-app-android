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

    private String folder_name;

    @NonNull
    private String title;

    private String createdDate;

    // constructors
    public Note(String folder_name, @NonNull String title, String createdDate) {
        this.folder_name = folder_name;
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

    public String getFolder_name() {
        return folder_name;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public String getCreatedDate() {
        return createdDate;
    }


    //setter
    public void setId(long id) {
        this.id = id;
    }

    public void setFolder_id(long folder_id) {
        this.folder_id = folder_id;
    }

    public void setFolder_name(String folder_name) {
        this.folder_name = folder_name;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }
}
