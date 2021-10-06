package com.example.android.popularmovies.data.detabase.dao;

import static androidx.room.OnConflictStrategy.IGNORE;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;

import java.util.List;

@Dao
public interface MovieDao {


    @Query("SELECT * FROM Movie")
    LiveData<List<MoviePersisted>> getMovies();

    @Insert(onConflict = IGNORE)
    void insert(MoviePersisted persistedMovie);

    @Query("SELECT * FROM Movie WHERE id= :id")
    LiveData<MoviePersisted> findPersistedMovieById(String id);

    @Query("DELETE FROM Movie WHERE id = :id")
    void deleteById(String id);

    @Query("DELETE FROM Movie")
    void deleteAll();


}
