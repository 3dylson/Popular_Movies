package com.example.android.popularmovies.model.datasources;


import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.android.popularmovies.data.network.MovieAPI;
import com.example.android.popularmovies.model.Movie;

public class PopMoviesDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    //To perform network calls
    private MovieAPI movieAPI;

    //A livedata to hold the data source instance
    private MutableLiveData<PopMoviesDataSource> moviesDataSourceMutableLiveData;

    public PopMoviesDataSourceFactory(MovieAPI movieAPI) {
        this.movieAPI = movieAPI;
        moviesDataSourceMutableLiveData = new MutableLiveData<>();
    }

    //Factory method pattern implemented below
    //Where a create method does the job of initializing the objects for client
    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        PopMoviesDataSource popMoviesDataSource = new PopMoviesDataSource(movieAPI);
        moviesDataSourceMutableLiveData.postValue(popMoviesDataSource);
        return popMoviesDataSource;
    }

    public MutableLiveData<PopMoviesDataSource> getMoviesDataSourceMutableLiveData() {
        return moviesDataSourceMutableLiveData;
    }
}
