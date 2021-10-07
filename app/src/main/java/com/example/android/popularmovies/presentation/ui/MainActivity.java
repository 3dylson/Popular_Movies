package com.example.android.popularmovies.presentation.ui;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.model.Movie;
import com.example.android.popularmovies.presentation.adapters.MoviesAdapter;
import com.example.android.popularmovies.presentation.viewmodels.MoviesViewModel;
import com.example.android.popularmovies.presentation.viewmodels.MoviesViewModelFactory;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements MoviesAdapter.MoviesAdapterOnItemClickHandler {

    private static final String TAG = MainActivity.class.getSimpleName();
    private MoviesViewModel viewModel;
    private RecyclerView recyclerView;
    private GridLayoutManager layoutManager;
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;
    private MenuItem popMovieItem;
    private MenuItem topRatedItem;
    private MenuItem favItem;
    private final ArrayList<Movie> movieArrayList = new ArrayList<>();
    private int scrollPosition = 0;
    private int PAGE = 2;
    private int rvPosition = RecyclerView.NO_POSITION;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_movies);
        progressBar = findViewById(R.id.pb_loading_indicator);


        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            // landscape
            layoutManager
                    = new GridLayoutManager(getApplicationContext(),6);

        } else {
            // portrait
            layoutManager
                    = new GridLayoutManager(getApplicationContext(),3);
        }
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        moviesAdapter = new MoviesAdapter(this);

        MoviesViewModelFactory factory = new MoviesViewModelFactory(this.getApplication());
        viewModel = new ViewModelProvider(this,factory).get(MoviesViewModel.class);


        viewModel.getMoviesLiveData().observe(this,movies -> {
            movieArrayList.addAll(movies);
            moviesAdapter.submitList(movies);
            //recyclerView.smoothScrollToPosition(scrollPosition);
            /*if (rvPosition == RecyclerView.NO_POSITION) rvPosition = 0;
            recyclerView.smoothScrollToPosition(rvPosition);*/

        });
        recyclerView.setAdapter(moviesAdapter);
        loadContent();
        scrollListener();

        /*if (Objects.equals(viewModel.getListFilterFlag().getValue(), "popular")){
            viewModel.loadPopMovies();
        } else {
            viewModel.loadTopRatedMovies();
            //TODO change checked filter
        }*/

    }

    private void scrollListener() {
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (layoutManager != null && layoutManager.findLastCompletelyVisibleItemPosition() == movieArrayList.size() - 3) {
                    Log.d(TAG,"Start loading");
                   // scrollPosition = layoutManager.findLastVisibleItemPosition();
                    if (Objects.equals(viewModel.getListFilterFlag().getValue(), "popular")) {
                        PAGE++;
                        viewModel.loadPopMovies(String.valueOf(PAGE));
                        //layoutManager.scrollToPosition(scrollPosition);
                    }
                }
            }
        });
    }

    private void loadContent() {
        viewModel.getListFilterFlag().observe(this, string -> {
            if (string.equals("popular")) {
                viewModel.loadPopMovies("1");
            }
            if (string.equals("top_rated")) {
                viewModel.loadTopRatedMovies("1");
                uncheckItemMenu();
                topRatedItem.setChecked(true);
            }
            if (string.equals("my_fav")) {
                viewModel.loadMyFav();
                uncheckItemMenu();
                favItem.setChecked(true);
            }
        });
    }

    private void uncheckItemMenu() {
        popMovieItem.setChecked(false);
        topRatedItem.setChecked(false);
        favItem.setChecked(false);
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
        popMovieItem = menu.findItem(R.id.pop_movies);
        topRatedItem = menu.findItem(R.id.top_rated);
        favItem = menu.findItem(R.id.myFav);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.top_rated) {
            viewModel.setListFilterFlag("top_rated");
            return true;
        }

        if (id == R.id.pop_movies) {
            uncheckItemMenu();
            item.setChecked(true);
            viewModel.setListFilterFlag("popular");
            return true;
        }

        if (id == R.id.myFav) {
            viewModel.setListFilterFlag("my_fav");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}