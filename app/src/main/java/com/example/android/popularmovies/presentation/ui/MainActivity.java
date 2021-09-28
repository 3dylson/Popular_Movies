package com.example.android.popularmovies.presentation.ui;

import android.os.Bundle;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.detabase.entity.Movie;
import com.example.android.popularmovies.presentation.adapters.MoviesAdapter;
import com.example.android.popularmovies.presentation.viewmodels.MoviesViewModel;
import com.example.android.popularmovies.presentation.viewmodels.MoviesViewModelFactory;

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

        viewModel.loadMovies();

    }

    @Override
    public void onItemClick(Movie movie) {

    }
}