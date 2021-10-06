package com.example.android.popularmovies.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;
import com.example.android.popularmovies.data.network.MoviesRepository;
import com.example.android.popularmovies.model.Movie;

public class DetailsViewModel extends AndroidViewModel {

    private final MoviesRepository repository;
    private Movie movie;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        PopMoviesDatabase database = PopMoviesDatabase.getInstance(application);
        repository = MoviesRepository.getInstance(database.movieDao());
    }

    public void favMovie(Movie movie) {
        this.movie = movie;
        MoviePersisted persistedMovie = movieToPersistedMovie(movie);
        repository.insert(persistedMovie);
    }

    public void unFavMovie(Movie movie) {
        repository.deleteById(String.valueOf(movie.getId()));
    }

    private MoviePersisted movieToPersistedMovie(Movie movie) {
        return new MoviePersisted(
                movie.getId(),
                movie.getBackdropPath(),
                movie.getOriginalTitle(),
                movie.getOverview(),
                movie.getPosterPath(),
                movie.getReleaseDate(),
                movie.getVoteAverage()
        );
    }


}
