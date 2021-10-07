package com.example.android.popularmovies.presentation.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.data.network.MoviesRepository;
import com.example.android.popularmovies.data.network.RetrofitClient;
import com.example.android.popularmovies.data.network.cb.DataRetrieved;
import com.example.android.popularmovies.data.network.responsemodel.MovieResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class MoviesViewModel extends AndroidViewModel implements DataRetrieved {

    private static final String TAG = MoviesViewModel.class.getSimpleName();

    private final MoviesRepository repository;
    private PopMoviesDatabase database;
    private MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
    private MutableLiveData<List<Movie>> favMoviesLiveData = new MutableLiveData<>();
    private List<Movie> moviesLoaded = new ArrayList<>();
    private MutableLiveData<String> listFilterFlag = new MutableLiveData<>();

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        database = PopMoviesDatabase.getInstance(application);
        repository = MoviesRepository.getInstance(database.movieDao());
        listFilterFlag.postValue("popular");
    }


    public MutableLiveData<List<Movie>> getMoviesLiveData() {
        return moviesLiveData;
    }

    public LiveData<String> getListFilterFlag() {
        return listFilterFlag;
    }

    public void loadPopMovies(String page) {
        RetrofitClient.getListOfPopularMovies(this,page);
    }

    public void loadTopRatedMovies(String page) {
        RetrofitClient.getListOfTopRatedMovies(this, page);
    }

    public void loadMyFav() {
        AtomicReference<List<MoviePersisted>> favMovies = new AtomicReference<>();

        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(()-> {
                    List<Movie> convertedMovies = new ArrayList<>();
                    favMovies.set(repository.getMovies());
                    favMovies.get().forEach(moviePersisted -> {
                        Movie movie = new Movie(
                                moviePersisted.getBackdropPath(),
                                moviePersisted.getId(),
                                moviePersisted.getOriginalTitle(),
                                moviePersisted.getOverview(),
                                moviePersisted.getPosterPath(),
                                moviePersisted.getReleaseDate(),
                                moviePersisted.getVoteAverage(),
                                true
                        );
                        convertedMovies.add(movie);
                    });
                    moviesLoaded = convertedMovies;
                    moviesLiveData.postValue(moviesLoaded);
                });
    }

    public void setListFilterFlag(String value){
        listFilterFlag.postValue(value);
    }


    @Override
    public void onDataFetchedSuccess(MovieResponse response) {
        Log.d(TAG, "onDataFetched Success | "+ response.getTotalResults() +" new movies");
        if (moviesLoaded.isEmpty()) {
            moviesLoaded = response.getMovies();
        }
        else {
            moviesLoaded.addAll(response.getMovies());
        }
        moviesLiveData.postValue(moviesLoaded);

    }

    @Override
    public void onDataFetchedFailed() {
        moviesLiveData.postValue(Collections.emptyList());
    }

}
