package com.example.android.popularmovies.data.network;

import static com.example.android.popularmovies.data.network.ServerValues.MOVIE_REVIEWS;
import static com.example.android.popularmovies.data.network.ServerValues.MOVIE_TRAILERS;
import static com.example.android.popularmovies.data.network.ServerValues.PAGE;
import static com.example.android.popularmovies.data.network.ServerValues.POPULAR;
import static com.example.android.popularmovies.data.network.ServerValues.TOP_RATED;


import com.example.android.popularmovies.data.network.responsemodel.MovieResponse;
import com.example.android.popularmovies.data.network.responsemodel.ReviewResponse;
import com.example.android.popularmovies.data.network.responsemodel.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieAPI {

    @GET(POPULAR)
    Call<MovieResponse> getPopularMoviesList(@Query(PAGE) String page);

    @GET(TOP_RATED)
    Call<MovieResponse> getTopRatedMoviesList(@Query(PAGE) String page);

    @GET(MOVIE_TRAILERS)
    Call<TrailerResponse> getMovieTrailers(
            @Path("movie_id") String movie_id);

    @GET(MOVIE_REVIEWS)
    Call<ReviewResponse> getMovieReviews(
            @Path("movie_id") String movie_id);

}
