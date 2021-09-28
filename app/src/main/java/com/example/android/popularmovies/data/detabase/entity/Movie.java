package com.example.android.popularmovies.data.detabase.entity;

import com.squareup.moshi.Json;

import java.util.List;

import kotlin.jvm.Transient;

public class Movie {

    @Json(name = "adult")
    private boolean adult;
    @Json(name = "backdrop_path")
    private String backdropPath;
    @Json(name = "genre_ids")
    private List<Integer> genreIds = null;
    @Json(name = "id")
    private int id;
    @Json(name = "original_language")
    private String originalLanguage;
    @Json(name = "original_title")
    private String originalTitle;
    @Json(name = "overview")
    private String overview;
    @Json(name = "popularity")
    private double popularity;
    @Json(name = "poster_path")
    private String posterPath;
    @Json(name = "release_date")
    private String releaseDate;
    @Json(name = "title")
    private String title;
    @Json(name = "video")
    private boolean video;
    @Json(name = "vote_average")
    private double voteAverage;
    @Json(name = "vote_count")
    private int voteCount;

    @Transient
    private boolean fav = false;

    public int getId() {
        return id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public String getOverview() {
        return overview;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public boolean isFav() {
        return fav;
    }
}
