package com.cemsafa.note_crrk_android.Model;

import static androidx.room.ForeignKey.CASCADE;
import static androidx.room.ForeignKey.NO_ACTION;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "note", foreignKeys = @ForeignKey(entity = Folder.class, parentColumns = "id", childColumns = "folder_id", onDelete = CASCADE, onUpdate = NO_ACTION))
public class Note implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private long folder_id;

    private String folder_name;

    @NonNull
    private String title;

    private String content;

    private String createdDate;

    private double latitude;

    private double longitude;

    private String audio;

    private String photo;

    @Ignore
    public Note() {}

    public Note(String folder_name, @NonNull String title, String content, String createdDate, double latitude, double longitude, String audio, String photo) {
        this.folder_name = folder_name;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.latitude = latitude;
        this.longitude = longitude;
        this.audio = audio;
        this.photo = photo;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getFolder_id() {
        return folder_id;
    }

    public void setFolder_id(long folder_id) {
        this.folder_id = folder_id;
    }

    public String getFolder_name() {
        return folder_name;
    }

    public void setFolder_name(String folder_name) {
        this.folder_name = folder_name;
    }

    @NonNull
    public String getTitle() {
        return title;
    }

    public void setTitle(@NonNull String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
