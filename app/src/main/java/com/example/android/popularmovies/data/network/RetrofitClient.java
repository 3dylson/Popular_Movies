package com.example.android.popularmovies.data.network;

import static com.example.android.popularmovies.data.network.ServerValues.BASE_URL;

import android.util.Log;

import com.example.android.popularmovies.data.network.cb.DataRetrieved;
import com.example.android.popularmovies.data.network.responsemodel.MovieResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {

    private static final String TAG = RetrofitClient.class.getSimpleName();


    public static void getListOfPopularMovies(DataRetrieved listener) {
        apiMovie()
                .getPopularMoviesList()
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d(TAG, "code: " + response.code());
                            return;
                        }
                        listener.onDataFetchedSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Log.e(TAG, "Unable to get popular movies. Error: " + t.getMessage());
                        listener.onDataFetchedFailed();
                    }
                });
    }


    public static void getListOfTopRatedMovies(DataRetrieved listener) {
        apiMovie()
                .getTopRatedMoviesList()
                .enqueue(new Callback<MovieResponse>() {
                    @Override
                    public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d(TAG, "code: " + response.code());
                            return;
                        }
                        listener.onDataFetchedSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<MovieResponse> call, Throwable t) {
                        Log.e(TAG, "Unable to get top rated movies. Error: " + t.getMessage());
                        listener.onDataFetchedFailed();
                    }
                });
    }


    static MovieAPI apiMovie() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        HttpLoggingInterceptor.Level bodyLevel = HttpLoggingInterceptor.Level.BODY;
        interceptor.level(bodyLevel);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(MoshiConverterFactory.create())
                .build();

        return retrofit.create(MovieAPI.class);
    }
}
