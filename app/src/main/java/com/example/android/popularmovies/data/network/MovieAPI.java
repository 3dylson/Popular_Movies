package com.example.android.popularmovies.data.network;

import static com.example.android.popularmovies.data.network.ServerValues.POPULAR;
import static com.example.android.popularmovies.data.network.ServerValues.API_KEY;
import static com.example.android.popularmovies.data.network.ServerValues.TOP_RATED;


import com.example.android.popularmovies.data.detabase.entity.Movie;
import com.example.android.popularmovies.data.network.responsemodel.MovieResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface MovieAPI {

    @Headers("api_key: " + API_KEY)
    @GET(POPULAR)
    Call<MovieResponse> getPopularMoviesList();

    @Headers("api_key: " + API_KEY)
    @GET(TOP_RATED)
    Call<MovieResponse> getTopRatedMoviesList();
}
