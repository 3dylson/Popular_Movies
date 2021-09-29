package com.example.android.popularmovies.presentation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.detabase.entity.Movie;
import com.example.android.popularmovies.data.detabase.entity.Trailer;
import com.example.android.popularmovies.data.network.RetrofitClient;
import com.example.android.popularmovies.data.network.cb.TrailerRetrieved;
import com.example.android.popularmovies.data.network.responsemodel.TrailerResponse;
import com.example.android.popularmovies.databinding.ActivityDetailsBinding;
import com.example.android.popularmovies.presentation.adapters.TrailersAdapter;

import java.util.Objects;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener,
        TrailersAdapter.TrailerAdapterOnItemClickHandler, TrailerRetrieved {

    public static final String EXTRA_MOVIE = "extra_movie";
    private final String BASE_IMAGE_PATH = "http://image.tmdb.org/t/p/original";
    private ActivityDetailsBinding binding;
    private TrailersAdapter trailersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.topDetailsAppBar.setNavigationOnClickListener(this);

        LinearLayoutManager trailerLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        binding.trailersRv.setLayoutManager(trailerLayoutManager);
        binding.trailersRv.setHasFixedSize(true);

        trailersAdapter = new TrailersAdapter(this);
        binding.trailersRv.setAdapter(trailersAdapter);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        //TODO Fix action bar contrast
        bindMovieToUI(movie);
        RetrofitClient.getListOfMovieTrailer( this,String.valueOf(movie.getId()));
    }

    private void bindMovieToUI(Movie movie) {
        binding.topDetailsAppBar.setTitle(movie.getOriginalTitle());
        String backDropUrl = null;
        if (movie.getBackdropPath() != null) {
            backDropUrl = movie.getBackdropPath();
        }
        Glide.with(getApplicationContext())
                .load(BASE_IMAGE_PATH+backDropUrl)
                .fallback(R.drawable.ic_baseline_broken_image_24)
                .fitCenter()
                .into(binding.ivMovieBackdrop);
        Glide.with(getApplicationContext())
                .load(BASE_IMAGE_PATH+movie.getPosterPath())
                .fallback(R.drawable.ic_baseline_broken_image_24)
                .fitCenter()
                .into(binding.movieThumbnail);
        binding.relesdeDateLabel.setText(movie.getReleaseDate());
        binding.tvVoteAverage.setText(movie.getVoteAverage() +"/10");
        binding.movieOverview.setText(movie.getOverview());

    }

    @Override
    public void onClick(View v) {
        onBackPressed();
    }

    @Override
    public void onItemClick(Trailer trailer) {

    }

    @Override
    public void onTrailerFetchedSuccess(TrailerResponse response) {
        trailersAdapter.submitList(response.getTrailers());

    }

    @Override
    public void onTrailerFetchedFailed() {

    }
}