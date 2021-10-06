package com.example.android.popularmovies.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

import java.util.List;

import kotlin.jvm.Transient;

public class Movie implements Parcelable {

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

    // Omit fields that shouldn’t be included in JSON
    @Transient
    private boolean fav = false;

    public Movie(String backdropPath, int id, String originalTitle, String overview, String posterPath, String releaseDate, double voteAverage, boolean fav) {
        this.backdropPath = backdropPath;
        this.id = id;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.fav = fav;
    }

    protected Movie(Parcel in) {
        adult = in.readByte() != 0;
        backdropPath = in.readString();
        id = in.readInt();
        originalLanguage = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        popularity = in.readDouble();
        posterPath = in.readString();
        releaseDate = in.readString();
        title = in.readString();
        video = in.readByte() != 0;
        voteAverage = in.readDouble();
        voteCount = in.readInt();
        fav = in.readByte() != 0;
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

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

    public String getTitle() {
        return title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public double getVoteAverage() {
        return voteAverage;
    }

    public boolean isFav() {
        return fav;
    }

    public void setFav(boolean fav) {
        this.fav = fav;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte((byte) (adult ? 1 : 0));
        dest.writeString(backdropPath);
        dest.writeInt(id);
        dest.writeString(originalLanguage);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeDouble(popularity);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeString(title);
        dest.writeByte((byte) (video ? 1 : 0));
        dest.writeDouble(voteAverage);
        dest.writeInt(voteCount);
        dest.writeByte((byte) (fav ? 1 : 0));
    }
}
