package com.example.android.popularmovies.presentation.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.data.network.MoviesRepository;
import com.example.android.popularmovies.data.network.RetrofitClient;
import com.example.android.popularmovies.data.network.cb.DataRetrieved;
import com.example.android.popularmovies.data.network.responsemodel.MovieResponse;

import java.util.Collections;
import java.util.List;

public class MoviesViewModel extends AndroidViewModel implements DataRetrieved {

    private static final String TAG = MoviesViewModel.class.getSimpleName();

    private final MoviesRepository repository;
    private MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
    private List<Movie> moviesLoaded;
    private Boolean filterPopMovieFlag = true;

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        PopMoviesDatabase database = PopMoviesDatabase.getInstance(application);
        repository = MoviesRepository.getInstance(database.movieDao());
    }


    public MutableLiveData<List<Movie>> getMoviesLiveData() {
        return moviesLiveData;
    }

    public Boolean getFilterPopMovieFlag() {
        return filterPopMovieFlag;
    }

    public void setFilterPopMovieFlag(Boolean filterPopMovieFlag) {
        this.filterPopMovieFlag = filterPopMovieFlag;
    }

    public void loadPopMovies() {
        RetrofitClient.getListOfPopularMovies(this);
    }

    public void loadTopRatedMovies() {
        RetrofitClient.getListOfTopRatedMovies(this);
    }


    @Override
    public void onDataFetchedSuccess(MovieResponse response) {
        Log.d(TAG, "onDataFetched Success | "+ response.getTotalResults() +" new movies");
        moviesLoaded = response.getMovies();
        moviesLiveData.postValue(moviesLoaded);

    }

    @Override
    public void onDataFetchedFailed() {
        moviesLiveData.postValue(Collections.emptyList());
    }

}
