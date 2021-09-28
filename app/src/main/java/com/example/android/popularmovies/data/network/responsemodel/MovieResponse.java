package com.example.android.popularmovies.data.network.responsemodel;

import com.example.android.popularmovies.data.detabase.entity.Movie;
import com.squareup.moshi.Json;

import java.util.List;

public class MovieResponse {

    @Json(name = "page")
    private int page;
    @Json(name = "results")
    private List<Movie> movies = null;
    @Json(name = "total_pages")
    private int totalPages;
    @Json(name = "total_results")
    private int totalResults;

    public List<Movie> getMovies() {
        return movies;
    }
}
