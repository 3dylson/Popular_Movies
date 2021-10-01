package com.example.android.popularmovies.model;

import com.squareup.moshi.Json;

public class AuthorDetails {

    @Json(name = "name")
    public String name;
    @Json(name = "username")
    public String username;
    @Json(name = "avatar_path")
    public String avatarPath;
    @Json(name = "rating")
    public int rating = 0;

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public int getRating() {
        return rating;
    }
}
