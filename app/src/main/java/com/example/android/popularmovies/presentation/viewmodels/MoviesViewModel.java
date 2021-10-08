package com.example.android.popularmovies.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PagedList;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;
import com.example.android.popularmovies.data.network.MovieAPI;
import com.example.android.popularmovies.data.network.MoviesRepository;
import com.example.android.popularmovies.data.network.RetrofitClient;
import com.example.android.popularmovies.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MoviesViewModel extends AndroidViewModel {

    private static final String TAG = MoviesViewModel.class.getSimpleName();

    private final MoviesRepository repository;
    private PopMoviesDatabase database;
    private final LiveData<PagedList<Movie>> pagedListPopMovie;
    private final LiveData<PagedList<Movie>> pagedListTopRatedMovie;
    private final MutableLiveData<List<MoviePersisted>> favMovies = new MutableLiveData<>();

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        MovieAPI movieAPI = RetrofitClient.apiMovie();
        database = PopMoviesDatabase.getInstance(application);
        repository = MoviesRepository.getInstance(database.movieDao(), movieAPI);
        pagedListPopMovie = repository.getPopMoviePagedList();
        pagedListTopRatedMovie = repository.getTopRatedPagedList();
        loadMyFav();
    }

    public LiveData<PagedList<Movie>> getPagedListPopMovie() {
        return pagedListPopMovie;
    }

    public LiveData<PagedList<Movie>> getPagedListTopRatedMovie() {
        return pagedListTopRatedMovie;
    }

    public MutableLiveData<List<MoviePersisted>> getFavMovies() {
        return favMovies;
    }

    public LiveData<Integer> getLoadState() {
        return repository.getPopMoviesLoadState();
    }

    public void loadMyFav() {
        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(()-> {
                   favMovies.postValue(repository.getFavMoviesList());
                });
    }


}
