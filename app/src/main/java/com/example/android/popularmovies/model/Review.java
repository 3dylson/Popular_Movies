package com.example.android.popularmovies.model;

import com.squareup.moshi.Json;

public class Review {

    @Json(name = "author")
    public String author;
    @Json(name = "author_details")
    public AuthorDetails authorDetails;
    @Json(name = "content")
    public String content;
    @Json(name = "created_at")
    public String createdAt;
    @Json(name = "id")
    public String id;
    @Json(name = "updated_at")
    public String updatedAt;
    @Json(name = "url")
    public String url;

    public String getAuthor() {
        return author;
    }

    public AuthorDetails getAuthorDetails() {
        return authorDetails;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public String getId() {
        return id;
    }
}
