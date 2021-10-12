package com.cemsafa.note_crrk_android.util;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.cemsafa.note_crrk_android.data.NoteDao;
import com.cemsafa.note_crrk_android.model.Note;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteRoomDB extends RoomDatabase {

    private static final String DB_NAME = "room_note_db";

    public abstract NoteDao noteDao();

    private static volatile NoteRoomDB INSTANCE;

    private static final int NUMBER_OF_THREADS = 4;

    //executor service that helps work in the background thread
    public static final ExecutorService databaseWriteExecutor
            = Executors.newFixedThreadPool(NUMBER_OF_THREADS);


    public static NoteRoomDB getInstance(final Context context) {
        if (INSTANCE == null) {
            synchronized (NoteRoomDB.class) {
                if (INSTANCE == null) {

                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(), NoteRoomDB.class, DB_NAME)
                            .addCallback(roomDatabaseCallBack)
                            .build();
                }
            }

        }
        return  INSTANCE;
    }

    private static final RoomDatabase.Callback roomDatabaseCallBack = new RoomDatabase.Callback() {

        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            databaseWriteExecutor.execute(() -> {

                NoteDao noteDao = INSTANCE.noteDao();
                noteDao.deleteAll();
            });
        }
    };

}
