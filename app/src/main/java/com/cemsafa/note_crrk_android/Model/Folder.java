package com.cemsafa.note_crrk_android.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "folder")
public class Folder {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @NonNull
    @ColumnInfo (name = "folder_name")
    private String folderName;

    @Ignore
    public Folder() {}

    //constructor
    public Folder(@NonNull String folderName) {

        this.folderName = folderName;
    }

    // getter
    public long getId() {
        return id;
    }

    @NonNull
    public String getFolderName() {
        return folderName;
    }

    //setter
    public void setId(long id) {
        this.id = id;
    }

    public void setFolderName(@NonNull String folderName) {
        this.folderName = folderName;
    }
}
