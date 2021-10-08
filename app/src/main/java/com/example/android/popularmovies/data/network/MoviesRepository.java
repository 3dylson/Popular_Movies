package com.example.android.popularmovies.data.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.detabase.dao.MovieDao;
import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.datasources.PopMoviesDataSource;
import com.example.android.popularmovies.model.datasources.PopMoviesDataSourceFactory;
import com.example.android.popularmovies.model.datasources.TopRatedDataSource;
import com.example.android.popularmovies.model.datasources.TopRatedDataSourceFactory;

import java.util.List;
import java.util.concurrent.Executors;

public class MoviesRepository {

    private static final String TAG = MoviesRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MoviesRepository INSTANCE;
    private final MovieDao movieDao;
    private final PopMoviesDataSourceFactory popMoviesDataSourceFactory;
    private final TopRatedDataSourceFactory topRatedDataSourceFactory;
    private final LiveData<Integer> topRatedLoadState;
    private final LiveData<Integer> popMoviesLoadState;


    public MoviesRepository(MovieDao movieDao, MovieAPI movieAPI ) {
        this.movieDao = movieDao;

        popMoviesDataSourceFactory = new PopMoviesDataSourceFactory(movieAPI);
        topRatedDataSourceFactory = new TopRatedDataSourceFactory(movieAPI);

        //since we can only access PopMoviesDataSourceFactory which has no method
        //to access the loading state present within PopMoviesDataSource
        //we use this Transformations API which helps us to get the load state livedata from PopMoviesDataSource
        //which we can let other classes to access it from here by creating a getter
        popMoviesLoadState = Transformations.switchMap(popMoviesDataSourceFactory.getMoviesDataSourceMutableLiveData(), PopMoviesDataSource::getPopMovieLoadState);
        topRatedLoadState = Transformations.switchMap(topRatedDataSourceFactory.getTopRatedDataSourceMutableLiveData(), TopRatedDataSource::getTopRatedLoadState);
    }

    public synchronized static MoviesRepository getInstance(MovieDao movieDao, MovieAPI movieAPI) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = new MoviesRepository(movieDao, movieAPI);
            }
        }
        return INSTANCE;
    }

    public LiveData<PagedList<Movie>> getPopMoviePagedList() {
        //There are some parameters that we can config according to our use case
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20) //Configures how many data items in one page to be supplied to recycler view
                .setPrefetchDistance(2) // in first call how many pages to load
                .setMaxSize(24) //Maximum PagedList size must be at least pageSize + 2*prefetchDist
                .build();

        return new LivePagedListBuilder<>(popMoviesDataSourceFactory,config)
                .setFetchExecutor(Executors.newFixedThreadPool(5)) //Use five threads to do the fetching operations
                .build();
    }

    public LiveData<PagedList<Movie>> getTopRatedPagedList() {
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20)
                .setPrefetchDistance(2)
                .setMaxSize(24)
                .build();

        return new LivePagedListBuilder<>(topRatedDataSourceFactory,config)
                .setFetchExecutor(Executors.newFixedThreadPool(5))
                .build();
    }

    public LiveData<Integer> getTopRatedLoadState() {
        return topRatedLoadState;
    }

    public LiveData<Integer> getPopMoviesLoadState() {
        return popMoviesLoadState;
    }

    public List<MoviePersisted> getFavMoviesList() {
        return movieDao.getFavMovies();
    }


    public void deleteFavMovie(String id) {
        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(() -> {
                    try {
                        movieDao.deleteFavMovieById(id);
                        Log.d(TAG,"Movie deleted as Fav");
                    } catch (Exception e) {
                        Log.d(TAG,e.getMessage());
                    }
                });
    }

    public void addFavMovie(MoviePersisted movie) {
        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(() -> {
                    try {
                        movieDao.insertFavMovie(movie);
                        Log.d(TAG,"Movie marked as Fav");
                    } catch (Exception e) {
                        Log.d(TAG,e.getMessage());
                    }
                });
    }

    public MoviePersisted findByID(String id) {
        return movieDao.findFavMovieById(id).getValue();
    }

}
