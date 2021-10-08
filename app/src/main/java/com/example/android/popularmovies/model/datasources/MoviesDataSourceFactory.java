package com.example.android.popularmovies.model.datasources;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.android.popularmovies.data.network.MovieAPI;
import com.example.android.popularmovies.model.Movie;

public class MoviesDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    //To perform network calls
    private MovieAPI movieAPI;

    //A livedata to hold the data source instance
    private MutableLiveData<MoviesDataSource> moviesDataSourceMutableLiveData;

    public MoviesDataSourceFactory(MovieAPI movieAPI) {
        this.movieAPI = movieAPI;
        moviesDataSourceMutableLiveData = new MutableLiveData<>();
    }

    //Factory method pattern implemented below
    //Where a create method does the job of initializing the objects for client
    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        MoviesDataSource moviesDataSource = new MoviesDataSource(movieAPI);
        moviesDataSourceMutableLiveData.postValue(moviesDataSource);
        return moviesDataSource;
    }

    public MutableLiveData<MoviesDataSource> getMoviesDataSourceMutableLiveData() {
        return moviesDataSourceMutableLiveData;
    }
}
