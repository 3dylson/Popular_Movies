package com.example.android.popularmovies.data.detabase.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "Movie")
public class MoviePersisted implements Parcelable {

    @PrimaryKey()
    private int id;
    @ColumnInfo(name = "backdrop_path")
    private final String backdropPath;
    @ColumnInfo(name = "original_title")
    private final String originalTitle;
    @ColumnInfo(name = "overview")
    private final String overview;
    @ColumnInfo(name = "poster_path")
    private final String posterPath;
    @ColumnInfo(name = "release_date")
    private final String releaseDate;
    @ColumnInfo(name = "vote_average")
    private final double voteAverage;

    public MoviePersisted(int id, String backdropPath, String originalTitle, String overview, String posterPath, String releaseDate, double voteAverage) {
        this.id = id;
        this.backdropPath = backdropPath;
        this.originalTitle = originalTitle;
        this.overview = overview;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
    }

    @Ignore
    protected MoviePersisted(Parcel in) {
        id = in.readInt();
        backdropPath = in.readString();
        originalTitle = in.readString();
        overview = in.readString();
        posterPath = in.readString();
        releaseDate = in.readString();
        voteAverage = in.readDouble();
    }

    public static final Creator<MoviePersisted> CREATOR = new Creator<MoviePersisted>() {
        @Override
        public MoviePersisted createFromParcel(Parcel in) {
            return new MoviePersisted(in);
        }

        @Override
        public MoviePersisted[] newArray(int size) {
            return new MoviePersisted[size];
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

    public double getVoteAverage() {
        return voteAverage;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(backdropPath);
        dest.writeString(originalTitle);
        dest.writeString(overview);
        dest.writeString(posterPath);
        dest.writeString(releaseDate);
        dest.writeDouble(voteAverage);
    }
}
