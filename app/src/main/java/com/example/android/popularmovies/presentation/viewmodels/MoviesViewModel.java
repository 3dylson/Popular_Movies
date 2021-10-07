package com.example.android.popularmovies.presentation.viewmodels;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.data.network.MoviesRepository;
import com.example.android.popularmovies.data.network.RetrofitClient;
import com.example.android.popularmovies.data.network.cb.DataRetrieved;
import com.example.android.popularmovies.data.network.responsemodel.MovieResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

public class MoviesViewModel extends AndroidViewModel implements DataRetrieved {

    private static final String TAG = MoviesViewModel.class.getSimpleName();

    private final MoviesRepository repository;
    private PopMoviesDatabase database;
    private MutableLiveData<List<Movie>> moviesLiveData = new MutableLiveData<>();
    private List<Movie> popularMoviesLoaded = new ArrayList<>();
    private int popPage = 1;
    private int topRatedPage = 1;
    private List<Movie> topRatedMoviesLoaded = new ArrayList<>();
    private List<Movie> favMoviesLoaded = new ArrayList<>();
    private MutableLiveData<String> listFilterFlag = new MutableLiveData<>();

    public MoviesViewModel(@NonNull Application application) {
        super(application);
        database = PopMoviesDatabase.getInstance(application);
        repository = MoviesRepository.getInstance(database.movieDao());
        listFilterFlag.setValue("popular");
    }

    public int getPopPage() {
        return popPage;
    }

    public int getTopRatedPage() {
        return topRatedPage;
    }

    public MutableLiveData<List<Movie>> getMoviesLiveData() {
        return moviesLiveData;
    }

    public LiveData<String> getListFilterFlag() {
        return listFilterFlag;
    }

    public void loadPopMovies(String page) {
        RetrofitClient.getListOfPopularMovies(this,page);
    }

    public void loadTopRatedMovies(String page) {
        RetrofitClient.getListOfTopRatedMovies(this, page);
    }

    public void loadMyFav() {
        AtomicReference<List<MoviePersisted>> favMovies = new AtomicReference<>();

        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(()-> {
                    List<Movie> convertedMovies = new ArrayList<>();
                    favMovies.set(repository.getMovies());
                    favMovies.get().forEach(moviePersisted -> {
                        Movie movie = new Movie(
                                moviePersisted.getBackdropPath(),
                                moviePersisted.getId(),
                                moviePersisted.getOriginalTitle(),
                                moviePersisted.getOverview(),
                                moviePersisted.getPosterPath(),
                                moviePersisted.getReleaseDate(),
                                moviePersisted.getVoteAverage(),
                                true
                        );
                        convertedMovies.add(movie);
                    });
                    favMoviesLoaded = convertedMovies;
                    moviesLiveData.postValue(favMoviesLoaded);
                });
    }

    public void setListFilterFlag(String value){
        listFilterFlag.postValue(value);
    }


    @Override
    public void onDataFetchedSuccess(MovieResponse response) {
        Log.d(TAG, "onDataFetched Success | "+ response.getMovies().size() +" new movies");
        List<Movie> moviesLoaded = new ArrayList<>();
        if (Objects.equals(listFilterFlag.getValue(), "popular")) {
            if (popularMoviesLoaded.isEmpty()) {
                popularMoviesLoaded = response.getMovies();
            }
            else {
                popularMoviesLoaded.addAll(response.getMovies());
            }
            popPage++;
            moviesLoaded = popularMoviesLoaded;
        }
       if (Objects.equals(listFilterFlag.getValue(), "top_rated")) {
           if (topRatedMoviesLoaded.isEmpty()) {
               topRatedMoviesLoaded = response.getMovies();
           }
           else {
               topRatedMoviesLoaded.addAll(response.getMovies());
           }
           topRatedPage++;
           moviesLoaded = topRatedMoviesLoaded;
       }
       moviesLiveData.postValue(moviesLoaded);
    }

    @Override
    public void onDataFetchedFailed() {
        moviesLiveData.postValue(Collections.emptyList());
    }

}
