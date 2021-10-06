package com.example.android.popularmovies.data.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.detabase.dao.MovieDao;
import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;

import java.util.List;

public class MoviesRepository {

    private static final String TAG = MoviesRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MoviesRepository INSTANCE;
    private final MovieDao movieDao;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private MutableLiveData<Integer> removeResult = new MutableLiveData<>();
    private MoviePersisted fetchedMovie;


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

    public MutableLiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public MutableLiveData<Integer> getRemoveResult() {
        return removeResult;
    }

    public List<MoviePersisted> getMovies() {
        return movieDao.getMovies();
    }

    /*public void updateMovies() {
        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(() -> onLoaded(movieDao.getMovies()));
    }*/

    public void deleteById(String id) {
        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(() -> {
                    try {
                        movieDao.deleteById(id);
                        removeResult.postValue(1);
                        Log.d(TAG,"Movie deleted as Fav");
                    } catch (Exception e) {
                        removeResult.postValue(0);
                    }
                });
    }

    public void insert(MoviePersisted movie) {
        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(() -> {
                    try {
                        movieDao.insert(movie);
                        insertResult.postValue(1);
                        Log.d(TAG,"Movie marked as Fav");
                    } catch (Exception e) {
                        insertResult.postValue(0);
                    }
                });
    }

    public MoviePersisted findByID(String id) {
        return movieDao.findPersistedMovieById(id).getValue();
    }


    /*private void onLoaded(List<MoviePersisted> movies) {
        this.movies.postValue(movies);
    }*/

}
