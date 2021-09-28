package com.example.android.popularmovies.data.network;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.detabase.dao.MovieDao;
import com.example.android.popularmovies.model.MoviePersisted;

import java.util.List;

public class MoviesRepository {

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MoviesRepository INSTANCE;
    private final MovieDao movieDao;
    private List<MoviePersisted> movies;


    public MoviesRepository(MovieDao movieDao) {
        this.movieDao = movieDao;
    }

    public synchronized static MoviesRepository getInstance(MovieDao movieDao) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = new MoviesRepository(movieDao);
            }
        }
        return INSTANCE;
    }


    public List<MoviePersisted> getMovies() {
        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(() -> onLoaded(movieDao.getMovies()));
        return this.movies;
    }

    private void onLoaded(List<MoviePersisted> movies) {
        this.movies=movies;
    }


}
