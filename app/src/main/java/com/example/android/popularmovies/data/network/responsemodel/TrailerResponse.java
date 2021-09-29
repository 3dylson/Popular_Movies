package com.example.android.popularmovies.data.network.responsemodel;

import com.example.android.popularmovies.data.detabase.entity.Trailer;
import com.squareup.moshi.Json;

import java.util.List;

public class TrailerResponse {

    @Json(name = "id")
    public int id;
    @Json(name = "results")
    public List<Trailer> trailers = null;

    public List<Trailer> getTrailers() {
        return trailers;
    }
}
