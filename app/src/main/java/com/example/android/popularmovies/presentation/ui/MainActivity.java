package com.example.android.popularmovies.presentation.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.detabase.entity.Movie;
import com.example.android.popularmovies.presentation.adapters.MoviesAdapter;
import com.example.android.popularmovies.presentation.viewmodels.MoviesViewModel;
import com.example.android.popularmovies.presentation.viewmodels.MoviesViewModelFactory;
import com.squareup.moshi.Moshi;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnItemClickHandler {

    private MoviesViewModel viewModel;
    private RecyclerView recyclerView;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_movies);
        progressBar = findViewById(R.id.pb_loading_indicator);

        GridLayoutManager layoutManager
                = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        moviesAdapter = new MoviesAdapter(this);

        MoviesViewModelFactory factory = new MoviesViewModelFactory(this.getApplication());
        viewModel = new ViewModelProvider(this,factory).get(MoviesViewModel.class);


        viewModel.getMoviesLiveData().observe(this,movies -> {
            moviesAdapter.submitList(movies);

        });
        recyclerView.setAdapter(moviesAdapter);

        // TODO save instance to load right filter on screen rotation
        viewModel.loadPopMovies();

    }


    @Override
    public void onItemClick(Movie movie) {
        Intent movieDetailIntent = new Intent(MainActivity.this,DetailsActivity.class);
        movieDetailIntent.putExtra(DetailsActivity.EXTRA_MOVIE,movie);
        startActivity(movieDetailIntent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_filter,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.top_rated) {
            if (viewModel.getFilterPopMovieFlag()) {
                viewModel.loadTopRatedMovies();
                viewModel.setFilterPopMovieFlag(false);
            }
            return true;
        }

        if (id == R.id.pop_movies) {
            if (!viewModel.getFilterPopMovieFlag()) {
                viewModel.loadPopMovies();
                viewModel.setFilterPopMovieFlag(true);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}