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
    List<MoviePersisted> getFavMovies();

    @Insert(onConflict = IGNORE)
    void insertFavMovie(MoviePersisted persistedMovie);

    @Query("SELECT * FROM Movie WHERE id= :id")
    LiveData<MoviePersisted> findFavMovieById(String id);

    @Query("DELETE FROM Movie WHERE id = :id")
    void deleteFavMovieById(String id);

    @Query("DELETE FROM Movie")
    void deleteAllFavMovie();


}
