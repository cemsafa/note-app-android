package com.cemsafa.note_crrk_android.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cemsafa.note_crrk_android.data.NoteDao;
import com.cemsafa.note_crrk_android.model.Folder;
import com.cemsafa.note_crrk_android.model.Note;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Note.class, Folder.class}, version = 1, exportSchema = false)
public abstract class NoteRoomDB extends RoomDatabase {

    private static final String DB_NAME = "noteDB";

    public abstract NoteDao noteDao();

    private static volatile NoteRoomDB INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    public static final ExecutorService dbWriteExec = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static NoteRoomDB getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NoteRoomDB.class, DB_NAME).addCallback(dbCallback).build();
                }
            }
        }
        return INSTANCE;
    }

    private static final RoomDatabase.Callback dbCallback = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            dbWriteExec.execute(() -> {
                NoteDao noteDao = INSTANCE.noteDao();
                noteDao.deleteAll();
            });
        }
    };
}
