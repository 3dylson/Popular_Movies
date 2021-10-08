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

public class MainActivity extends AppCompatActivity {

    private PopMovieFragment popMovieFragment;
    private TopRatedFragment topRatedFragment;
    private FavFragment favFragment;

    private MenuItem popMovieItem;
    private MenuItem topRatedItem;
    private MenuItem favItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        popMovieFragment = new PopMovieFragment();
        topRatedFragment = new TopRatedFragment();
        favFragment = new FavFragment();

        loadFragment(popMovieFragment);

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
            uncheckItemMenu();
            item.setChecked(true);
            getSupportActionBar().setTitle("Top Rated Movies");
            loadFragment(topRatedFragment);
            return true;
        }

        if (id == R.id.pop_movies) {
            uncheckItemMenu();
            item.setChecked(true);
            getSupportActionBar().setTitle("Popular Movies");
            loadFragment(popMovieFragment);
            return true;
        }

        if (id == R.id.myFav) {
            uncheckItemMenu();
            item.setChecked(true);
            getSupportActionBar().setTitle("My Favorites Movies");
            loadFragment(favFragment);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}