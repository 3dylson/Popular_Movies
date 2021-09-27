package com.example.android.popularmovies.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "Movie")
public class MoviePersisted {

    @PrimaryKey()
    private int id;
    @ColumnInfo(name = "original_title")
    private String originalTitle;
    @ColumnInfo(name = "overview")
    private String overview;
    @ColumnInfo(name = "poster_path")
    private String posterPath;
    @ColumnInfo(name = "release_date")
    private String releaseDate;
    @ColumnInfo(name = "vote_average")
    private int voteAverage;

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

    public int getVoteAverage() {
        return voteAverage;
    }
}
