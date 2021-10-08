package com.example.android.popularmovies.data.network;

import static com.example.android.popularmovies.data.network.ServerValues.BASE_URL;

import android.util.Log;

import com.example.android.popularmovies.data.network.cb.DataRetrieved;
import com.example.android.popularmovies.data.network.cb.ReviewRetrieved;
import com.example.android.popularmovies.data.network.cb.TrailerRetrieved;
import com.example.android.popularmovies.data.network.responsemodel.MovieResponse;
import com.example.android.popularmovies.data.network.responsemodel.ReviewResponse;
import com.example.android.popularmovies.data.network.responsemodel.TrailerResponse;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

public class RetrofitClient {

    private static final String TAG = RetrofitClient.class.getSimpleName();


    public static void getListOfPopularMovies(DataRetrieved listener, String page) {
        apiMovie()
                .getPopularMoviesList(page)
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


    public static void getListOfTopRatedMovies(DataRetrieved listener, String page) {
        apiMovie()
                .getTopRatedMoviesList(page)
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


    public static void getListOfMovieTrailer(TrailerRetrieved listener, String movieID) {
        apiMovie()
                .getMovieTrailers(movieID)
                .enqueue(new Callback<TrailerResponse>() {
                    @Override
                    public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d(TAG, "code: " + response.code());
                            return;
                        }
                        listener.onTrailerFetchedSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<TrailerResponse> call, Throwable t) {
                        Log.e(TAG, "Unable to get trailers. Error: " + t.getMessage());
                        listener.onTrailerFetchedFailed();
                    }
                });
    }

    public static void getListOfMovieReviews(ReviewRetrieved listener, String movieID) {
        apiMovie()
                .getMovieReviews(movieID)
                .enqueue(new Callback<ReviewResponse>() {
                    @Override
                    public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                        if (!response.isSuccessful()) {
                            Log.d(TAG, "code: " + response.code());
                            return;
                        }
                        listener.onReviewFetchedSuccess(response.body());
                    }

                    @Override
                    public void onFailure(Call<ReviewResponse> call, Throwable t) {
                        Log.e(TAG, "Unable to get reviews. Error: " + t.getMessage());
                        listener.onReviewFetchedFailed();
                    }
                });
    }


    public static MovieAPI apiMovie() {
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
