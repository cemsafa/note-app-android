package com.cemsafa.note_crrk_android.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "note")
public class Note {

    @PrimaryKey
    @ColumnInfo(name = "id" )
    private int id;

    @NonNull
    @ColumnInfo(name = "category")
    private String category;

    @NonNull
    @ColumnInfo(name = "title")
    private String title;

    @NonNull
    @ColumnInfo(name = "entry_date")
    private String entry_date;

    @Ignore
    public Note () {

    }


    //constructor
    public Note(@NonNull String category, @NonNull String title, @NonNull String entry_date) {
        this.category = category;
        this.title = title;
        this.entry_date = entry_date;
    }

    // getters
    public int getId() {
        return id;
    }

    @NonNull
    public String getCategory() {
        return category;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    @NonNull
    public String getEntry_date() {
        return entry_date;
    }

    // setters
    public void setId(int id) {
        this.id = id;
    }

    public void setCategory(@NonNull String category) {
        this.category = category;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public void setEntry_date(@NonNull String entry_date) {
        this.entry_date = entry_date;
    }
}

