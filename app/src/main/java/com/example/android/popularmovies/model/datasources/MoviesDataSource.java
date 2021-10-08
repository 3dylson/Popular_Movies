package com.example.android.popularmovies.model.datasources;

import static com.example.android.popularmovies.data.network.ServerValues.FAILED;
import static com.example.android.popularmovies.data.network.ServerValues.ONGOING;
import static com.example.android.popularmovies.data.network.ServerValues.SUCCESS;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.PageKeyedDataSource;

import com.example.android.popularmovies.data.network.MovieAPI;
import com.example.android.popularmovies.data.network.responsemodel.MovieResponse;
import com.example.android.popularmovies.model.Movie;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesDataSource extends PageKeyedDataSource<Integer, Movie>  {

    MovieAPI movieAPI;

    //A livedata which has a integer that holds the state of loading
    MutableLiveData<Integer> loadState;

    public MoviesDataSource(MovieAPI movieAPI) {
        this.movieAPI = movieAPI;
        loadState = new MutableLiveData<>();
    }

    /**
     * This below method is called only once and the first method to be called
     * */
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {

        loadState.postValue(ONGOING);

        final int currentPage = 1;

        movieAPI.getPopularMoviesList(String.valueOf(currentPage))
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {

                            if (response.body().getMovies() != null) {
                                //IMPORTANT: once the first page is load we increment the page count and pass it to callback.onResult()
                                callback.onResult(response.body().getMovies(), null, currentPage + 1);
                            }

                            //setting load state so that the UI can know data fetching is successful
                            loadState.postValue(SUCCESS);

                        } else {
                            //since no data was fetched we pass empty list and dont increment the page number
                            //so that it can retry the fetching of 1st page
                            callback.onResult(new ArrayList<>(), null, currentPage);

                            //setting load state so that the UI can know data fetching failed
                            loadState.postValue(FAILED);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {

                        //since no data was fetched we pass empty list and dont increment the page number
                        //so that it can retry the fetching of 1st page
                        callback.onResult(new ArrayList<>(), null, currentPage);

                        //setting load state so that the UI can know data fetching failed
                        loadState.postValue(FAILED);

                    }
                });


    }

    // This need not be implemented as previous pages have already been loaded.
    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

    }

    /**
     * This method will be called every time after the loadInitial() is been called
    * */
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

        // we get the current page from params
        final int currentPage = params.key;

        loadState.postValue(ONGOING);

        movieAPI.getPopularMoviesList(String.valueOf(currentPage))
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            loadState.postValue(SUCCESS);
                            callback.onResult(response.body().getMovies(),currentPage+1);
                        } else {
                            callback.onResult(new ArrayList<>(), currentPage);
                            loadState.postValue(FAILED);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        callback.onResult(new ArrayList<>(), currentPage);
                        loadState.postValue(FAILED);
                    }
                });


    }


    //UI will call this method to get the livedata of loading, subscribe to it, and update the UI state accordingly
    public MutableLiveData<Integer> getLoadState() {
        return loadState;
    }
}
