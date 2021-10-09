package com.example.android.popularmovies.presentation.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.presentation.ui.fragments.FavFragment;
import com.example.android.popularmovies.presentation.ui.fragments.PopMovieFragment;
import com.example.android.popularmovies.presentation.ui.fragments.TopRatedFragment;
import com.example.android.popularmovies.presentation.viewmodels.MainViewModel;

public class MainActivity extends AppCompatActivity {

    private PopMovieFragment popMovieFragment;
    private TopRatedFragment topRatedFragment;
    private FavFragment favFragment;

    private MenuItem popMovieItem;
    private MenuItem topRatedItem;
    private MenuItem favItem;

    private MainViewModel viewModel;

    private static final String KEY = "loadFragmentKey";
    private int loadView = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new MainViewModel();
        popMovieFragment = new PopMovieFragment();
        topRatedFragment = new TopRatedFragment();
        favFragment = new FavFragment();

        if (savedInstanceState != null) {
            viewModel.getOptionsMenuCreated().observe(this, flag -> {
                loadView = savedInstanceState.getInt(KEY);
                if (flag) {
                    viewModel.getOptionsMenuCreated().setValue(false);
                    switch (loadView) {
                        case 2:
                            uncheckItemMenu();
                            topRatedItem.setChecked(true);
                            getSupportActionBar().setTitle("Top Rated Movies");
                            viewModel.setTopMovieFragment(true);
                            loadFragment(topRatedFragment);
                            break;
                        case 3:
                            uncheckItemMenu();
                            favItem.setChecked(true);
                            getSupportActionBar().setTitle("My Favorites Movies");
                            viewModel.setFavMovieFragment(true);
                            loadFragment(favFragment);
                            break;
                        default:
                            uncheckItemMenu();
                            popMovieItem.setChecked(true);
                            getSupportActionBar().setTitle("Popular Movies");
                            viewModel.setPopMovieFragment(true);
                            loadFragment(popMovieFragment);
                    }
                }

            });
        }
        else {
            viewModel.getOptionsMenuCreated().removeObservers(this);
            loadFragment(popMovieFragment);
        }
    }



    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        if (viewModel.isPopMovieFragment()) {
            outState.putInt(KEY,1);
        }
        if (viewModel.isTopMovieFragment()) {
            outState.putInt(KEY,2);
        }
        if (viewModel.isFavMovieFragment()) {
            outState.putInt(KEY,3);
        }
    }


    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.navigationContainer, fragment)
                .commit();
    }


    private void uncheckItemMenu() {
        popMovieItem.setChecked(false);
        topRatedItem.setChecked(false);
        favItem.setChecked(false);
        viewModel.setPopMovieFragment(false);
        viewModel.setTopMovieFragment(false);
        viewModel.setFavMovieFragment(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_filter,menu);
        popMovieItem = menu.findItem(R.id.pop_movies);
        topRatedItem = menu.findItem(R.id.top_rated);
        favItem = menu.findItem(R.id.myFav);
        viewModel.getOptionsMenuCreated().setValue(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.top_rated) {
            if (!viewModel.isTopMovieFragment()) {
                uncheckItemMenu();
                item.setChecked(true);
                getSupportActionBar().setTitle("Top Rated Movies");
                viewModel.setTopMovieFragment(true);
                loadFragment(topRatedFragment);
            }
            return true;
        }

        if (id == R.id.pop_movies) {
            if (!viewModel.isPopMovieFragment()) {
                uncheckItemMenu();
                item.setChecked(true);
                getSupportActionBar().setTitle("Popular Movies");
                viewModel.setPopMovieFragment(true);
                loadFragment(popMovieFragment);
            }
            return true;
        }

        if (id == R.id.myFav) {
            if (!viewModel.isFavMovieFragment()) {
                uncheckItemMenu();
                item.setChecked(true);
                getSupportActionBar().setTitle("My Favorites Movies");
                viewModel.setFavMovieFragment(true);
                loadFragment(favFragment);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}