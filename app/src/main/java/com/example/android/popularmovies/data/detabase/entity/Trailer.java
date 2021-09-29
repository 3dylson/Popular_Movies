package com.example.android.popularmovies.data.detabase.entity;

import com.squareup.moshi.Json;

public class Trailer {

    @Json(name = "iso_639_1")
    public String iso6391;
    @Json(name = "iso_3166_1")
    public String iso31661;
    @Json(name = "name")
    public String name;
    @Json(name = "key")
    public String key;
    @Json(name = "site")
    public String site;
    @Json(name = "size")
    public int size;
    @Json(name = "type")
    public String type;
    @Json(name = "official")
    public boolean official;
    @Json(name = "published_at")
    public String publishedAt;
    @Json(name = "id")
    public String id;

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getId() {
        return id;
    }
}
