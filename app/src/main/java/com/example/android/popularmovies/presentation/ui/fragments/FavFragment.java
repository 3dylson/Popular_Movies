package com.example.android.popularmovies.presentation.ui.fragments;


import android.content.Intent;

import com.example.android.popularmovies.data.detabase.entity.MoviePersisted;
import com.example.android.popularmovies.presentation.adapters.FavAdapter;
import com.example.android.popularmovies.presentation.ui.DetailsActivity;

public class FavFragment extends PopMovieFragment implements FavAdapter.FavAdapterOnItemClickHandler {

    private FavAdapter favAdapter;

    @Override
    public void setupAdapter() {
        favAdapter = new FavAdapter(this);
        getRecyclerView().setAdapter(favAdapter);
    }

    @Override
    public void loadData() {
        getViewModel().getFavMovies().observe(this.getViewLifecycleOwner(), favMovies -> {
            favAdapter.submitList(favMovies);
        });
    }


    @Override
    public void onFavMovieCLick(MoviePersisted moviePersisted) {
        Intent movieDetailIntent = new Intent(requireActivity(), DetailsActivity.class);
        movieDetailIntent.putExtra(DetailsActivity.EXTRA_MOVIE, moviePersisted);
        startActivity(movieDetailIntent);
    }

    @Override
    public void onResume() {
        super.onResume();
        getViewModel().loadMyFav();
    }
}