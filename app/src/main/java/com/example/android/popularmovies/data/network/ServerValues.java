package com.example.android.popularmovies.data.network;

import com.example.android.popularmovies.BuildConfig;

public final class ServerValues {

    static final String BASE_URL = "https://api.themoviedb.org/3/movie/";
    static final String API_KEY = BuildConfig.API_KEY;

    // Endpoints
    static final String POPULAR = "popular?api_key=" + API_KEY;
    static final String TOP_RATED = "top_rated?api_key=" + API_KEY;
    static final String MOVIE_TRAILERS = "{movie_id}/videos?api_key=" + API_KEY;
    static final String MOVIE_REVIEWS = "{movie_id}/reviews?api_key=" + API_KEY;
    static final String PAGE = "page";

    // Paging
    public static final Integer FAILED = -1;
    public static final Integer SUCCESS = 1;
    public static final Integer ONGOING = 0;


    // Ensures this class is never instantiated
    private ServerValues() {}
}
