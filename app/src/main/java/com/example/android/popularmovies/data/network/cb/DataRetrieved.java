package com.example.android.popularmovies.data.network.cb;

import com.example.android.popularmovies.data.network.responsemodel.MovieResponse;

public interface DataRetrieved {

    void onDataFetchedSuccess(MovieResponse movies);

    void onDataFetchedFailed();
}
