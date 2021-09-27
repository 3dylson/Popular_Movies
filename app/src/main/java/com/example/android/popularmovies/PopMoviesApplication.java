package com.example.android.popularmovies;

import android.app.Application;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.network.MoviesRepository;

public class PopMoviesApplication extends Application {

    PopMoviesDatabase database() {
        return PopMoviesDatabase.getInstance(this);
    }

    MoviesRepository repository() {
        return new MoviesRepository(database().movieDao());
    }
}
