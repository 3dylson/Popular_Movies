package com.example.android.popularmovies.data.network;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;

import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.detabase.dao.MovieDao;
import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.datasources.MoviesDataSource;
import com.example.android.popularmovies.model.datasources.MoviesDataSourceFactory;

import java.util.List;
import java.util.concurrent.Executors;

public class MoviesRepository {

    private static final String TAG = MoviesRepository.class.getSimpleName();

    // For Singleton instantiation
    private static final Object LOCK = new Object();
    private static MoviesRepository INSTANCE;
    private final MovieDao movieDao;
    private MutableLiveData<Integer> insertResult = new MutableLiveData<>();
    private MutableLiveData<Integer> removeResult = new MutableLiveData<>();
    private MoviePersisted fetchedMovie;
    private final MoviesDataSourceFactory moviesDataSourceFactory;
    private MovieAPI movieAPI;
    private LiveData<Integer> loadState;


    public MoviesRepository(MovieDao movieDao, MovieAPI movieAPI ) {
        this.movieDao = movieDao;
        this.movieAPI = movieAPI;

        moviesDataSourceFactory = new MoviesDataSourceFactory(movieAPI);

        //since we can only access MoviesDataSourceFactory which has no method
        //to access the loading state present within MoviesDataSource
        //we use this Transformations API which helps us to get the load state livedata from MoviesDataSource
        //which we can let other classes to access it from here by creating a getter
        loadState = Transformations.switchMap(moviesDataSourceFactory.getMoviesDataSourceMutableLiveData(), MoviesDataSource::getLoadState);
    }

    public synchronized static MoviesRepository getInstance(MovieDao movieDao, MovieAPI movieAPI) {
        if (INSTANCE == null) {
            synchronized (LOCK) {
                INSTANCE = new MoviesRepository(movieDao, movieAPI);
            }
        }
        return INSTANCE;
    }

    public void setFilterFlag(Integer flag) {

    }

    public LiveData<PagedList<Movie>> getPagedList() {
        //There are some parameters that we can config according to our use case
        PagedList.Config config = new PagedList.Config.Builder()
                .setEnablePlaceholders(false)
                .setPageSize(20) //Configures how many data items in one page to be supplied to recycler view
                .setPrefetchDistance(2) // in first call how many pages to load
                .setMaxSize(24) //Maximum PagedList size must be at least pageSize + 2*prefetchDist
                .build();

        return new LivePagedListBuilder<>(moviesDataSourceFactory,config)
                .setFetchExecutor(Executors.newFixedThreadPool(5)) //Use five threads to do the fetching operations
                .build();
    }

    public LiveData<Integer> getLoadState() {
        return loadState;
    }

    public MutableLiveData<Integer> getInsertResult() {
        return insertResult;
    }

    public MutableLiveData<Integer> getRemoveResult() {
        return removeResult;
    }

    public List<MoviePersisted> getMovies() {
        return movieDao.getMovies();
    }

    /*public void updateMovies() {
        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(() -> onLoaded(movieDao.getMovies()));
    }*/

    public void deleteById(String id) {
        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(() -> {
                    try {
                        movieDao.deleteById(id);
                        removeResult.postValue(1);
                        Log.d(TAG,"Movie deleted as Fav");
                    } catch (Exception e) {
                        removeResult.postValue(0);
                    }
                });
    }

    public void insert(MoviePersisted movie) {
        PopMoviesDatabase
                .databaseWriteExecutor
                .execute(() -> {
                    try {
                        movieDao.insert(movie);
                        insertResult.postValue(1);
                        Log.d(TAG,"Movie marked as Fav");
                    } catch (Exception e) {
                        insertResult.postValue(0);
                    }
                });
    }

    public MoviePersisted findByID(String id) {
        return movieDao.findPersistedMovieById(id).getValue();
    }


    /*private void onLoaded(List<MoviePersisted> movies) {
        this.movies.postValue(movies);
    }*/

}
