package com.example.android.popularmovies.model.datasources;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;

import com.example.android.popularmovies.data.network.MovieAPI;
import com.example.android.popularmovies.model.Movie;

public class TopRatedDataSourceFactory extends DataSource.Factory<Integer, Movie>{

    private MovieAPI movieAPI;

    private MutableLiveData<TopRatedDataSource> topRatedDataSourceMutableLiveData;

    public TopRatedDataSourceFactory(MovieAPI movieAPI) {
        this.movieAPI = movieAPI;
        topRatedDataSourceMutableLiveData = new MutableLiveData<>();
    }

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        TopRatedDataSource topRatedDataSource = new TopRatedDataSource(movieAPI);
        topRatedDataSourceMutableLiveData.postValue(topRatedDataSource);
        return topRatedDataSource;
    }

    public MutableLiveData<TopRatedDataSource> getTopRatedDataSourceMutableLiveData() {
        return topRatedDataSourceMutableLiveData;
    }
}
