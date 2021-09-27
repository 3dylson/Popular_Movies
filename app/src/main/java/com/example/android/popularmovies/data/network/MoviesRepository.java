package com.example.android.popularmovies.data.network;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.detabase.dao.MovieDao;
import com.example.android.popularmovies.model.MoviePersisted;

import java.util.List;

public class MoviesRepository {

    private MovieDao movieDao;
    private List<MoviePersisted> movies;


    public MoviesRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }


    private void getMovies() {
        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(() -> onLoaded(movieDao.getMovies()));
    }

    private void onLoaded(List<MoviePersisted> movies) {
        this.movies = movies;
    }


}
