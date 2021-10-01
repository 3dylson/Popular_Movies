package com.example.android.popularmovies.presentation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.network.cb.ReviewRetrieved;
import com.example.android.popularmovies.data.network.responsemodel.ReviewResponse;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.model.Trailer;
import com.example.android.popularmovies.data.network.RetrofitClient;
import com.example.android.popularmovies.data.network.cb.TrailerRetrieved;
import com.example.android.popularmovies.data.network.responsemodel.TrailerResponse;
import com.example.android.popularmovies.databinding.ActivityDetailsBinding;
import com.example.android.popularmovies.presentation.adapters.ReviewsAdapter;
import com.example.android.popularmovies.presentation.adapters.TrailersAdapter;

import java.util.Collections;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener,
        TrailersAdapter.TrailerAdapterOnItemClickHandler, ReviewsAdapter.ReviewAdapterOnItemClickHandler,
        TrailerRetrieved, ReviewRetrieved {

    public static final String EXTRA_MOVIE = "extra_movie";
    private ActivityDetailsBinding binding;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;

    private boolean isReviewExpanded = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.topDetailsAppBar.setNavigationOnClickListener(this);

        LinearLayoutManager trailerLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        binding.trailersRv.setLayoutManager(trailerLayoutManager);
        binding.trailersRv.setHasFixedSize(true);

        LinearLayoutManager reviewLayoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rvReviews.setLayoutManager(reviewLayoutManager);
        binding.rvReviews.setHasFixedSize(true);

        trailersAdapter = new TrailersAdapter(this);
        reviewsAdapter = new ReviewsAdapter(this);

        binding.trailersRv.setAdapter(trailersAdapter);
        binding.rvReviews.setAdapter(reviewsAdapter);

        Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);


        //TODO Fix action bar contrast
        bindMovieToUI(movie);
        RetrofitClient.getListOfMovieTrailer( this,String.valueOf(movie.getId()));
        RetrofitClient.getListOfMovieReviews(this,String.valueOf(movie.getId()));
    }

    private void bindMovieToUI(Movie movie) {
        binding.topDetailsAppBar.setTitle(movie.getOriginalTitle());
        String backDropUrl = null;
        if (movie.getBackdropPath() != null) {
            backDropUrl = movie.getBackdropPath();
        }
        String BASE_IMAGE_PATH = "http://image.tmdb.org/t/p/original";
        Glide.with(getApplicationContext())
                .load(BASE_IMAGE_PATH +backDropUrl)
                .fallback(R.drawable.ic_baseline_broken_image_24)
                .fitCenter()
                .into(binding.ivMovieBackdrop);
        Glide.with(getApplicationContext())
                .load(BASE_IMAGE_PATH +movie.getPosterPath())
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
        Intent playTrailerIntent = new Intent(Intent.ACTION_VIEW);
        playTrailerIntent.setData(Uri.parse("https://www.youtube.com/watch?v="+trailer.getKey()));
        startActivity(playTrailerIntent);

    }

    @Override
    public void onTrailerFetchedSuccess(TrailerResponse response) {
        trailersAdapter.submitList(response.getTrailers());

    }

    @Override
    public void onTrailerFetchedFailed() {
        trailersAdapter.submitList(Collections.emptyList());
    }

    @Override
    public void onReviewFetchedSuccess(ReviewResponse response) {
        reviewsAdapter.submitList(response.getReviews());
    }

    @Override
    public void onReviewFetchedFailed() {
        reviewsAdapter.submitList(Collections.emptyList());
    }

    @Override
    public void onReviewClick(int adapterPosition) {
        RecyclerView.ViewHolder currentReview = binding.rvReviews.findViewHolderForAdapterPosition(adapterPosition);
        TextView showReview = currentReview.itemView.findViewById(R.id.tv_review);
        if(isReviewExpanded){
            //This will shrink textview to 3 lines if it is expanded.
            showReview.setMaxLines(3);
            //showReview.setEllipsize(TextUtils.TruncateAt.END);
            isReviewExpanded = false;
        } else {
            //This will expand the textview if it is of 3 lines
            showReview.setMaxLines(Integer.MAX_VALUE);
            isReviewExpanded = true;
        }
    }
}