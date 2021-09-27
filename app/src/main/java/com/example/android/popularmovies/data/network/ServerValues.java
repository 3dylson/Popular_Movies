package com.example.android.popularmovies.data.network;

import com.example.android.popularmovies.BuildConfig;

final class ServerValues {

    static final String BASE_URL = "http://api.themoviedb.org/3/movie/";
    static final String API_KEY = BuildConfig.API_KEY;

    // Endpoints
    static final String POPULAR = "popular";
    static final String TOP_RATED = "top_rated";






    // Ensures this class is never instantiated
    private ServerValues() {}
}
