package com.example.android.popularmovies.presentation.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.detabase.PopMoviesDatabase;
import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;
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
import com.example.android.popularmovies.presentation.viewmodels.DetailsViewModel;
import com.example.android.popularmovies.presentation.viewmodels.DetailsViewModelFactory;

import java.util.Collections;

public class DetailsActivity extends AppCompatActivity implements View.OnClickListener,
        Toolbar.OnMenuItemClickListener,
        TrailersAdapter.TrailerAdapterOnItemClickHandler, ReviewsAdapter.ReviewAdapterOnItemClickHandler,
        TrailerRetrieved, ReviewRetrieved {

    public static final String EXTRA_MOVIE = "extra_movie";
    private DetailsViewModel viewModel;
    private ActivityDetailsBinding binding;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;
    private PopMoviesDatabase moviesDatabase;

    private Movie movie;
    private boolean isReviewExpanded = false;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDetailsBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        moviesDatabase = PopMoviesDatabase.getInstance(this.getApplicationContext());

        binding.topDetailsAppBar.setNavigationOnClickListener(this);

        LinearLayoutManager trailerLayoutManager
                = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL,false);
        binding.trailersRv.setLayoutManager(trailerLayoutManager);
        binding.trailersRv.setHasFixedSize(true);

        LinearLayoutManager reviewLayoutManager
                = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        binding.rvReviews.setLayoutManager(reviewLayoutManager);
        binding.rvReviews.setHasFixedSize(true);
        binding.rvReviews.setNestedScrollingEnabled(true);

        trailersAdapter = new TrailersAdapter(this);
        reviewsAdapter = new ReviewsAdapter(this);

        DetailsViewModelFactory factory = new DetailsViewModelFactory(this.getApplication());
        viewModel = new ViewModelProvider(this, factory).get(DetailsViewModel.class);

        binding.trailersRv.setAdapter(trailersAdapter);
        binding.rvReviews.setAdapter(reviewsAdapter);

        movie = getIntent().getParcelableExtra(EXTRA_MOVIE);


        initFavCheck();
        bindMovieToUI();

        binding.topDetailsAppBar.setOnMenuItemClickListener(this::onMenuItemClick);

        RetrofitClient.getListOfMovieTrailer( this,String.valueOf(movie.getId()));
        RetrofitClient.getListOfMovieReviews(this,String.valueOf(movie.getId()));
    }

    private void initFavCheck() {

        moviesDatabase.movieDao().findPersistedMovieById(String.valueOf(movie.getId())).observe(this, moviePersisted -> {
            if (moviePersisted != null) {
                isFavorite = true;
                binding.topDetailsAppBar.getMenu().findItem(R.id.favorite_action).setIcon(R.drawable.ic_baseline_favorite_24);
                return;
            }
            binding.topDetailsAppBar.getMenu().findItem(R.id.favorite_action).setIcon(R.drawable.ic_baseline_favorite_border_24);
        });

    }

    private void bindMovieToUI() {
        binding.topDetailsAppBar.setTitle(movie.getOriginalTitle());
        String backDropUrl = null;
        if (movie.getBackdropPath() != null) {
            backDropUrl = movie.getBackdropPath();
        }
        String BASE_IMAGE_PATH = "https://image.tmdb.org/t/p/original";
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
        if (response.getTotalResults() > 0) {
            reviewsAdapter.submitList(response.getReviews());
        }
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

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.favorite_action) {
            if (isFavorite) {
                item.setIcon(R.drawable.ic_baseline_favorite_border_24);
                viewModel.unFavMovie(movie);
            }
            else {
                item.setIcon(R.drawable.ic_baseline_favorite_24);
                viewModel.favMovie(movie);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}