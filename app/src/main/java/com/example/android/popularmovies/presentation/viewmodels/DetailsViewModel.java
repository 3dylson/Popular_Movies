package com.example.android.popularmovies.presentation.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;
import com.example.android.popularmovies.data.network.MovieAPI;
import com.example.android.popularmovies.data.network.MoviesRepository;
import com.example.android.popularmovies.data.network.RetrofitClient;
import com.example.android.popularmovies.data.detabase.FavoriteAction;
import com.example.android.popularmovies.model.Movie;

public class DetailsViewModel extends AndroidViewModel {

    private final MoviesRepository repository;

    public DetailsViewModel(@NonNull Application application) {
        super(application);
        MovieAPI movieAPI = RetrofitClient.apiMovie();
        PopMoviesDatabase database = PopMoviesDatabase.getInstance(application);
        repository = MoviesRepository.getInstance(database.movieDao(), movieAPI);
    }

    public void favMovie(Movie movie, FavoriteAction result) {
        MoviePersisted persistedMovie = movieToPersistedMovie(movie);
        repository.addFavMovie(persistedMovie,result);
    }

    public void favMoviePersisted(MoviePersisted movie, FavoriteAction result) {
        repository.addFavMovie(movie,result);
    }

    public void unFavMovie(int id, FavoriteAction result) {
        repository.deleteFavMovie(String.valueOf(id),result);
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
