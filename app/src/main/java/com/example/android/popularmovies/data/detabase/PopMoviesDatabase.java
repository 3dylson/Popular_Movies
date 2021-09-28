package com.example.android.popularmovies.data.detabase;

import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.android.popularmovies.data.detabase.dao.MovieDao;
import com.example.android.popularmovies.model.MoviePersisted;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {MoviePersisted.class}, version = 1, exportSchema = false)
public abstract class PopMoviesDatabase extends RoomDatabase {

    private static final String LOG_TAG = PopMoviesDatabase.class.getSimpleName();
    private static final String DATABASE_NAME = "Movie";
    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static PopMoviesDatabase INSTANCE;


    public static PopMoviesDatabase getInstance(Context context) {
        Log.d(LOG_TAG, "Getting the database");
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        PopMoviesDatabase.class,
                        PopMoviesDatabase.DATABASE_NAME)
                        .build();
                Log.d(LOG_TAG, "Made new database");
            }
        }
        return INSTANCE;
    }

    // The associated DAOs for the database
    public abstract MovieDao movieDao();

    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(2);
}
