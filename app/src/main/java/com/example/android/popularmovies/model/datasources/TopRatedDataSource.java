package com.example.android.popularmovies.model.datasources;

import static com.example.android.popularmovies.data.network.ServerValues.FAILED;
import static com.example.android.popularmovies.data.network.ServerValues.ONGOING;
import static com.example.android.popularmovies.data.network.ServerValues.SUCCESS;

import android.util.Log;

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

public class TopRatedDataSource extends PageKeyedDataSource<Integer, Movie> {

    private static final String TAG = TopRatedDataSource.class.getSimpleName();

    MovieAPI movieAPI;

    private MutableLiveData<Integer> loadState;

    public TopRatedDataSource(MovieAPI movieAPI) {
        this.movieAPI = movieAPI;
        loadState = new MutableLiveData<>();
    }

    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, Movie> callback) {

        loadState.postValue(ONGOING);

        final int currentPage = 1;

        movieAPI.getTopRatedMoviesList(String.valueOf(currentPage))
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            if (response.body().getMovies() != null) {
                                callback.onResult(response.body().getMovies(), null, currentPage + 1);
                            }
                            loadState.postValue(SUCCESS);

                        } else {
                            callback.onResult(new ArrayList<>(), null, currentPage);
                            loadState.postValue(FAILED);
                            Log.d(TAG, "code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        callback.onResult(new ArrayList<>(), null, currentPage);
                        loadState.postValue(FAILED);
                        Log.e(TAG, "Unable to get popular movies. Error: " + t.getMessage());

                    }
                });
    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {
    }

    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, Movie> callback) {

        final int currentPage = params.key;

        loadState.postValue(ONGOING);

        movieAPI.getTopRatedMoviesList(String.valueOf(currentPage))
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (response.isSuccessful()) {
                            loadState.postValue(SUCCESS);
                            callback.onResult(response.body().getMovies(),currentPage+1);
                        } else {
                            callback.onResult(new ArrayList<>(), currentPage);
                            loadState.postValue(FAILED);
                            Log.d(TAG, "code: " + response.code());
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        callback.onResult(new ArrayList<>(), currentPage);
                        loadState.postValue(FAILED);
                        Log.e(TAG, "Unable to get popular movies. Error: " + t.getMessage());
                    }
                });
    }


    public MutableLiveData<Integer> getTopRatedLoadState() {
        return loadState;
    }
}
