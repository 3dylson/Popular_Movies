package com.example.android.popularmovies.data.network.responsemodel;

import com.example.android.popularmovies.model.Review;
import com.squareup.moshi.Json;

import java.util.List;

public class ReviewResponse {

    @Json(name = "id")
    public int id;
    @Json(name = "page")
    public int page;
    @Json(name = "results")
    public List<Review> reviews = null;
    @Json(name = "total_pages")
    public int totalPages;
    @Json(name = "total_results")
    public int totalResults;

    public int getId() {
        return id;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public int getTotalResults() {
        return totalResults;
    }
}
