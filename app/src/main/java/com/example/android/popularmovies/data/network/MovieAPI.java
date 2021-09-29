package com.example.android.popularmovies.data.network;

import static com.example.android.popularmovies.data.network.ServerValues.MOVIE_TRAILERS;
import static com.example.android.popularmovies.data.network.ServerValues.POPULAR;
import static com.example.android.popularmovies.data.network.ServerValues.API_KEY;
import static com.example.android.popularmovies.data.network.ServerValues.TOP_RATED;


import com.example.android.popularmovies.data.detabase.entity.Movie;
import com.example.android.popularmovies.data.network.responsemodel.MovieResponse;
import com.example.android.popularmovies.data.network.responsemodel.TrailerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface MovieAPI {

    @GET(POPULAR)
    Call<MovieResponse> getPopularMoviesList();

    @GET(TOP_RATED)
    Call<MovieResponse> getTopRatedMoviesList();

    @GET(MOVIE_TRAILERS)
    Call<TrailerResponse> getMovieTrailers(@Path("movie_id") String movie_id);
}
