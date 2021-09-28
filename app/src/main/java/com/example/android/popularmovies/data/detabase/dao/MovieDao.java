package com.example.android.popularmovies.data.detabase.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.example.android.popularmovies.model.MoviePersisted;

import java.util.List;

@Dao
public interface MovieDao {


    @Query("SELECT * FROM Movie")
    List<MoviePersisted> getMovies();


}
